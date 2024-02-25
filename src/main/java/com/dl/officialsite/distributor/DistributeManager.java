package com.dl.officialsite.distributor;

import com.dl.officialsite.common.enums.CodeEnums;
import com.dl.officialsite.common.enums.DistributeStatusEnums;
import com.dl.officialsite.common.exception.BizException;
import com.dl.officialsite.hiring.application.Application;
import com.dl.officialsite.redpacket.RedPacket;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;

@Service
@Slf4j
@Configuration
public class DistributeManager {

    @Autowired
    private DistributeRepository distributeRepository;

    public DistributeInfo requireIdIsValid(Long id) {
        Optional<DistributeInfo> optionalRsp = this.distributeRepository.findById(id);
        if (!optionalRsp.isPresent())
            throw new BizException(CodeEnums.INVALID_ID);
        return optionalRsp.get();
    }

    List<DistributeInfo> findUnfinishedDistributeByChainId(String chainId) {
        //TODO DistributeStatusEnums
        return this.distributeRepository.findUnfinishedDistributeByChainId(chainId);
    }

}
