package com.dl.officialsite.tokenInfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dl.officialsite.common.enums.CodeEnums;
import com.dl.officialsite.common.exception.BizException;

import java.util.Optional;

@Service
public class TokenInfoManager {

    @Autowired
    private TokenInfoRepository tokenInfoRepository;

    public TokenInfo requireTokenIdIsValid(Long tokenId) {

        // check name
        Optional<TokenInfo> optionalRsp = this.tokenInfoRepository.findById(tokenId);
        if (!optionalRsp.isPresent())
            throw new BizException(CodeEnums.INVALID_ID);
        return optionalRsp.get();
    }

}
