package com.dl.officialsite.distributor;

import com.dl.officialsite.common.enums.CodeEnums;
import com.dl.officialsite.common.exception.BizException;
import java.util.Optional;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@Configuration
public class DistributeManager {

    @Autowired
    private DistributeRepository distributeRepository;

    public DistributeInfo requireIdIsValid(Long id) {

        // check name
        Optional<DistributeInfo> optionalRsp = this.distributeRepository.findById(id);
        if (!optionalRsp.isPresent())
            throw new BizException(CodeEnums.TOKEN_INVALID_ID);
        return optionalRsp.get();
    }

}
