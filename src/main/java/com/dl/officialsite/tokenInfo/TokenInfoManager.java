package com.dl.officialsite.tokenInfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dl.officialsite.common.enums.CodeEnums;
import com.dl.officialsite.common.exception.BizException;
import com.dl.officialsite.common.utils.UserSecurityUtils;
import com.dl.officialsite.distributor.DistributeInfo;

import antlr.Token;

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

    public TokenInfo requireIdIsValid(Long id) {
        // check supper admin TODO
        //  String address = UserSecurityUtils.getUserLogin().getAddress();
        //  if (!teamService.checkMemberIsSuperAdmin(address)) {
        //  throw new BizException(CodeEnums.NOT_THE_ADMIN.getCode(),
        //  CodeEnums.NOT_THE_ADMIN.getMsg());
        //  }

        Optional<TokenInfo> optionalRsp = this.tokenInfoRepository.findById(id);
        if (!optionalRsp.isPresent())
            throw new BizException(CodeEnums.INVALID_ID);
        return optionalRsp.get();
    }

}
