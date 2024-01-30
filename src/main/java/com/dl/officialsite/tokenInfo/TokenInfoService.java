package com.dl.officialsite.tokenInfo;

import com.dl.officialsite.common.enums.TokenStatusEnums;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.dl.officialsite.common.enums.CodeEnums;
import com.dl.officialsite.common.enums.DistributeStatusEnums;
import com.dl.officialsite.common.exception.BizException;
import com.dl.officialsite.common.utils.UserSecurityUtils;
import com.dl.officialsite.config.ChainConfig;
import com.dl.officialsite.distributor.vo.GetDistributeByPageReqVo;
import com.dl.officialsite.distributor.vo.GetTokenByPageReqVo;
import com.dl.officialsite.member.Member;
import com.dl.officialsite.member.MemberVo;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

@Slf4j
@Service
public class TokenInfoService {

    @Autowired
    private ChainConfig chainConfig;
    @Autowired
    private TokenInfoRepository tokenInfoRepository;
    @Autowired
    private TokenInfoManager tokenInfoManager;

    // save token
    public TokenInfo save(TokenInfo param) {
        // check ID
        if (!Objects.isNull(param.getId()))
            throw new BizException(CodeEnums.ID_NEED_EMPTY);
        // check chain
        if (!Arrays.asList(chainConfig.getIds()).contains(String.valueOf(param.getChainId())))
            throw new BizException(CodeEnums.INVALID_CHAIN_ID);

        param.setStatus(TokenStatusEnums.NORMAL.getData());
        return tokenInfoRepository.save(param);
    }

    // update token
    public void deleteToken(Long id) {
        log.info("[deleteToken] id :{} ", id);

        // TODO check member

        // check id
        tokenInfoManager.requireIdIsValid(id);
        // delete
        tokenInfoRepository.deleteById(id);
    }

    // query by page
    public Page<TokenInfo> queryToekenByPage(GetTokenByPageReqVo param) {
        log.info("[queryToekenByPage]");

        Pageable pageable = PageRequest.of(param.getPageNumber() - 1, param.getPageSize(),
                Sort.by(Sort.Direction.DESC, "createTime"));

        Specification<TokenInfo> queryParam = new Specification<TokenInfo>() {
            @Override
            public Predicate toPredicate(Root<TokenInfo> root, CriteriaQuery<?> criteriaQuery,
                    CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                if (param.getId() != null) {
                    log.info(String.valueOf(param.getId()));
                    predicates.add(criteriaBuilder.like(root.get("id"), "%" + param.getId() + "%"));
                }
                if (param.getChainId() != null) {
                    predicates.add(criteriaBuilder.equal(root.get("chainId"), param.getChainId()));
                }
                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };

        return tokenInfoRepository.findAll(queryParam, pageable);
    }

}
