package com.dl.officialsite.distributor.distributeClaimer;

import com.dl.officialsite.common.enums.CodeEnums;
import com.dl.officialsite.common.enums.DistributeClaimerStatusEnums;
import com.dl.officialsite.common.enums.DistributeStatusEnums;
import com.dl.officialsite.common.exception.BizException;
import com.dl.officialsite.common.utils.UserSecurityUtils;
import com.dl.officialsite.config.ConstantConfig;
import com.dl.officialsite.distributor.DistributeInfo;
import com.dl.officialsite.distributor.DistributeManager;
import com.dl.officialsite.distributor.vo.AddDistributeClaimerReqVo;
import com.dl.officialsite.distributor.vo.AddDistributeClaimerReqVo.ClaimerInfo;
import com.dl.officialsite.distributor.vo.DistributeInfoVo;
import com.dl.officialsite.distributor.vo.GetDistributeClaimerByPageReqVo;
import com.dl.officialsite.distributor.vo.GetDistributeClaimerRspVo;
import com.dl.officialsite.member.Member;
import com.dl.officialsite.member.MemberManager;

import cn.hutool.core.util.ArrayUtil;

import java.util.ArrayList;
import java.util.List;

import com.dl.officialsite.tokenInfo.TokenInfo;
import lombok.extern.slf4j.Slf4j;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

@Service
@Slf4j
@Configuration
public class DistributeClaimerService {

    @Autowired
    private MemberManager memberManager;

    @Autowired
    private DistributeManager distributeManager;

    @Autowired
    private DistributeClaimerRepository distributeClaimerRepository;

    @Autowired
    private DistributeClaimerManager distributeClaimerManager;

    @Autowired
    private ConstantConfig constantConfig;

    // save
    @Transactional(rollbackOn = Exception.class)
    public void saveClaimer(AddDistributeClaimerReqVo param) {
        log.info("[saveClaimer] param");

        // check distribute
        DistributeInfo distributeInfo = distributeManager.requireIdIsValid(param.getDistributeId());

        // check creator
        // current user TODO test.dev
        String creatorAddress = "0x1F7b953113f4dFcBF56a1688529CC812865840e2";
        if (constantConfig.getLoginFilter())
            creatorAddress = UserSecurityUtils.getUserLogin().getAddress();
        Member member = this.memberManager.requireMemberAddressExist(creatorAddress);
        if (distributeInfo.getCreatorId() != member.getId())
            throw new BizException(CodeEnums.ONLY_CREATOE);

        // check status
        if (distributeInfo.getStatus() != DistributeStatusEnums.UN_COMPLETED.getData())
            throw new BizException(CodeEnums.NOT_SUPPORT_MODIFY);

        // check claimerId
        List<ClaimerInfo> claimerList = param.getClaimerList();
        if (ArrayUtil.isEmpty(claimerList))
            throw new BizException(CodeEnums.EMPTY_CLAIMER);
        for (int i = 0; i < claimerList.size(); i++) {
            ClaimerInfo claimerInfo = claimerList.get(i);
            // check amount
            if (claimerInfo.getAmount() <= 0)
                throw new BizException(CodeEnums.INVALID_AMOUNT);
            // check member
            memberManager.requireMembeIdExist(claimerInfo.getClaimerId());

            // check record
            Optional<DistributeClaimer> opRsp = distributeClaimerRepository
                    .findByDistributeAndClaimer(param.getDistributeId(), claimerInfo.getClaimerId());
            DistributeClaimer newRow = null;
            if (opRsp.isPresent()) {
                newRow = opRsp.get();
                newRow.setDistributeAmount(claimerInfo.getAmount());
            } else {
                newRow = new DistributeClaimer();
                newRow.setDistributeId(param.getDistributeId());
                newRow.setChainId(distributeInfo.getChainId());
                newRow.setClaimerId(claimerInfo.getClaimerId());
                newRow.setDistributeAmount(claimerInfo.getAmount());
                newRow.setStatus(DistributeClaimerStatusEnums.CREATING.getData());
            }
            distributeClaimerRepository.save(newRow);

        }
    }

    // update distribute claimer
    public void deleteDistributeClaimer(Long id) {
        log.info("[deleteDistributeClaimer] id :{} ", id);

        // check id
        DistributeClaimer distributeClaimerInfo = distributeClaimerManager.requireIdIsValid(id);
        // check distribute
        DistributeInfo distributeInfo = distributeManager.requireIdIsValid(id);

        // check creator
        String creatorAddress = UserSecurityUtils.getUserLogin().getAddress();
        Member member = this.memberManager.requireMemberAddressExist(creatorAddress);
        if (distributeInfo.getCreatorId() != member.getId())
            throw new BizException(CodeEnums.ONLY_CREATOE);

        // check status
        if (distributeInfo.getStatus() != DistributeStatusEnums.UN_COMPLETED.getData())
            throw new BizException(CodeEnums.NOT_SUPPORT_MODIFY);

        // delete
        distributeClaimerRepository.deleteById(id);
    }

    // query by page
    public Page<GetDistributeClaimerRspVo> queryDistributeClaimerByPage(GetDistributeClaimerByPageReqVo param) {
        log.info("[queryDistributeClaimerByPage]");

        Pageable pageable = PageRequest.of(param.getPageNumber() - 1, param.getPageSize(),
                Sort.by(Sort.Direction.DESC, "createTime"));

        Specification<DistributeClaimer> queryParam = new Specification<DistributeClaimer>() {
            @Override
            public Predicate toPredicate(Root<DistributeClaimer> root, CriteriaQuery<?> criteriaQuery,
                    CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                if (param.getId() != null) {
                    log.info(String.valueOf(param.getId()));
                    predicates.add(criteriaBuilder.like(root.get("id"), "%" + param.getId() + "%"));
                }
                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };

        // GetDistributeClaimerRspVo
        Page<DistributeClaimer> dbRsp = distributeClaimerRepository.findAll(queryParam, pageable);
        List<GetDistributeClaimerRspVo> voList = dbRsp.getContent().stream()
                .map(this::convertToGetDistributeClaimerRspVo)
                .collect(Collectors.toList());

        return new PageImpl<>(voList, pageable, dbRsp.getTotalElements());
    }


    // query detail
    public GetDistributeClaimerRspVo queryDistributeClaimerDetail(Long id) {
        log.info("[queryDistributeClaimerDetail] id :{} ", id);

        // check id
        DistributeClaimer distributeClaimer = distributeClaimerManager.requireIdIsValid(id);

        return convertToGetDistributeClaimerRspVo(distributeClaimer);
    }


    private GetDistributeClaimerRspVo convertToGetDistributeClaimerRspVo(DistributeClaimer distributeClaimer) {
        // check member
        Member member = memberManager.requireMembeIdExist(distributeClaimer.getClaimerId());
        // set address
        GetDistributeClaimerRspVo rspVo = new GetDistributeClaimerRspVo();
        BeanUtils.copyProperties(distributeClaimer, rspVo);
        rspVo.setClaimerAddress(member.getAddress());
        return rspVo;
    }

}
