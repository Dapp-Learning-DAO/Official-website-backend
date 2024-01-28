package com.dl.officialsite.distributor;

import com.dl.officialsite.common.base.BaseResponse;
import com.dl.officialsite.common.enums.CodeEnums;
import com.dl.officialsite.common.enums.DistributeStatusEnums;
import com.dl.officialsite.common.exception.BizException;
import com.dl.officialsite.common.utils.UserSecurityUtils;
import com.dl.officialsite.config.ChainConfig;
import com.dl.officialsite.distributor.distributeClaimer.DistributeClaimerService;
import com.dl.officialsite.distributor.vo.DistributeInfoVo;
import com.dl.officialsite.distributor.vo.GetDistributeByPageReqVo;
import com.dl.officialsite.member.Member;
import com.dl.officialsite.member.MemberManager;
import com.dl.officialsite.member.MemberRepository;
import com.dl.officialsite.redpacket.RedPacket;
import com.dl.officialsite.tokenInfo.TokenInfo;
import com.dl.officialsite.tokenInfo.TokenInfoManager;
import com.dl.officialsite.tokenInfo.TokenInfoRepository;

import cn.hutool.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import lombok.extern.slf4j.Slf4j;
import java.util.Optional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.web3j.crypto.Hash;
import org.springframework.data.domain.Page;

@Service
@Slf4j
@Configuration
public class DistributeService {

    @Autowired
    private DistributeRepository distributeRepository;
    @Autowired
    private MemberManager memberManager;

    @Autowired
    private ChainConfig chainConfig;

    @Autowired
    private TokenInfoManager tokenInfoManager;

    @Autowired
    private DistributeManager distributeManager;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private TokenInfoRepository tokenInfoRepository;

    // create new distribute
    public DistributeInfo createDistribute(DistributeInfo param) {
        log.info("[createDistribute] param : ", String.valueOf(param));

        // current user
        String creatorAddress = UserSecurityUtils.getUserLogin().getAddress();
        Member member = this.memberManager.requireMemberAddressExist(creatorAddress);
        param.setCreatorId(member.getId());
        // check ID
        if (!Objects.isNull(param.getId()))
            throw new BizException(CodeEnums.ID_NEED_EMPTY);
        // check chain
        if (!Arrays.asList(chainConfig.getIds()).contains(String.valueOf(param.getChainId())))
            throw new BizException(CodeEnums.INVALID_CHAIN_ID);
        // check token
        this.tokenInfoManager.requireTokenIdIsValid(param.getTokenId());
        // distribute key
        if (StringUtils.isEmpty(param.getContractKey())) {
            String distributeKey = buildDistributeContractId(creatorAddress, param.getMessage());
            param.setContractKey(distributeKey);
        }

        // save
        return distributeRepository.save(param);
    }

    // update distribute
    public void deleteDistribute(Long id) {
        log.info("[deleteDistribute] id :{} ", id);

        // check id
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
        distributeRepository.deleteById(id);
    }

    // query detail
    public DistributeInfoVo queryDistributeDetail(Long id) {
        log.info("[queryDistributeDetail] id :{} ", id);

        // check id
        DistributeInfo distributeInfo = distributeManager.requireIdIsValid(id);

        // copy attribute
        DistributeInfoVo distributeInfoVo = new DistributeInfoVo();
        BeanUtils.copyProperties(distributeInfo, distributeInfoVo);

        // query member
        Optional<Member> memberOptional = memberRepository.findById(id);
        memberOptional.ifPresent(memberRow -> distributeInfoVo.setCreatorAddress(memberRow.getAddress()));

        // query token
        Optional<TokenInfo> tokenOptional = tokenInfoRepository.findById(id);
        tokenOptional.ifPresent(row -> distributeInfoVo.setTokenAddress(row.getTokenAddress()));

        return distributeInfoVo;
    }

    // query by page
    public Page<DistributeInfo> queryDistributeByPage(GetDistributeByPageReqVo param) {
        log.info("[queryDistributeByPage]");

        Pageable pageable = PageRequest.of(param.getPageNumber() - 1, param.getPageSize(),
                Sort.by(Sort.Direction.DESC, "createTime"));

        Specification<DistributeInfo> queryParam = new Specification<DistributeInfo>() {
            @Override
            public Predicate toPredicate(Root<DistributeInfo> root, CriteriaQuery<?> criteriaQuery,
                    CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                if (param.getId() != null) {
                    log.info(String.valueOf(param.getId()));
                    predicates.add(criteriaBuilder.like(root.get("id"), "%" + param.getId() + "%"));
                }
                if (param.getName() != null) {
                    predicates.add(criteriaBuilder.like(root.get("name"), "%" + param.getName() + "%"));
                }
                if (param.getChainId() != null) {
                    predicates.add(criteriaBuilder.equal(root.get("chainId"), param.getChainId()));
                }
                if (param.getCreator() != null) {
                    Member member = memberManager.getMemberByAddress(param.getCreator());
                    String creatorAddress = Objects.isNull(member) ? null : member.getAddress();
                    predicates.add(criteriaBuilder.equal(root.get("creatorId"), creatorAddress));
                }
                if (param.getExpireTime() != null) {
                    predicates.add(criteriaBuilder.lessThan(root.get("expireTime"), param.getExpireTime()));
                }
                if (param.getCreateTime() != null) {
                    predicates.add(criteriaBuilder.greaterThan(root.get("createTime"), param.getCreateTime()));
                }
                if (param.getStatus() != null) {
                    predicates.add(criteriaBuilder.equal(root.get("status"), param.getStatus()));
                }
                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };

        return distributeRepository.findAll(queryParam, pageable);
    }

    public static String buildDistributeContractId(String address, String message) {
        // Address addr = new Address(address);
        // Utf8String utf8Str = new Utf8String(message);
        return Hash.sha3(address.concat(message));
    }

}
