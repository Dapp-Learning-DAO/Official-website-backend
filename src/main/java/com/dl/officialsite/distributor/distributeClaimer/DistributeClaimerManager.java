package com.dl.officialsite.distributor.distributeClaimer;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dl.officialsite.common.enums.CodeEnums;
import com.dl.officialsite.common.exception.BizException;

@Service
public class DistributeClaimerManager {

    @Autowired
    private DistributeClaimerRepository distributeClaimerRepository;

    public DistributeClaimer requireIdIsValid(Long id) {
        // check name
        Optional<DistributeClaimer> optionalRsp = this.distributeClaimerRepository.findById(id);
        if (!optionalRsp.isPresent())
            throw new BizException(CodeEnums.INVALID_ID);
        return optionalRsp.get();
    }
}
