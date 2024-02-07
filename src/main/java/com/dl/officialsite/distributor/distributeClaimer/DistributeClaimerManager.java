package com.dl.officialsite.distributor.distributeClaimer;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dl.officialsite.common.enums.CodeEnums;
import com.dl.officialsite.common.exception.BizException;
import com.dl.officialsite.distributor.vo.GetDistributeClaimerRspVo;
import com.dl.officialsite.member.Member;
import com.dl.officialsite.member.MemberRepository;

import cn.hutool.core.lang.Pair;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class DistributeClaimerManager {

    @Autowired
    private DistributeClaimerRepository distributeClaimerRepository;
    @Autowired
    private MemberRepository memberRepository;

    public DistributeClaimer requireIdIsValid(Long id) {
        // check name
        Optional<DistributeClaimer> optionalRsp = this.distributeClaimerRepository.findById(id);
        if (!optionalRsp.isPresent())
            throw new BizException(CodeEnums.INVALID_ID);
        return optionalRsp.get();
    }

    // query detail
    public GetDistributeClaimerRspVo queryDistributeClaimerDetail(Long id) {
        log.info("[queryDistributeClaimerDetail] id :{} ", id);

        // check id
        DistributeClaimer distributeClaimer = requireIdIsValid(id);

        return convertToGetDistributeClaimerRspVo(distributeClaimer);
    }

    public GetDistributeClaimerRspVo convertToGetDistributeClaimerRspVo(DistributeClaimer distributeClaimer) {
        // set address
        GetDistributeClaimerRspVo rspVo = new GetDistributeClaimerRspVo();
        BeanUtils.copyProperties(distributeClaimer, rspVo);
//
//        // check member
//        Optional<Member> memberOp = memberRepository.findById(distributeClaimer.getClaimerId());
//        if (memberOp.isPresent())
//            rspVo.setClaimerAddress(memberOp.get().getAddress());
        return rspVo;
    }

}
