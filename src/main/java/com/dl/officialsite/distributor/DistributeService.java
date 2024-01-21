package com.dl.officialsite.distributor;

import com.dl.officialsite.common.enums.CodeEnums;
import com.dl.officialsite.common.exception.BizException;
import com.dl.officialsite.common.utils.UserSecurityUtils;
import com.dl.officialsite.config.ChainConfig;
import com.dl.officialsite.distributor.RedPacket;
import com.dl.officialsite.member.Member;
import com.dl.officialsite.member.MemberManager;
import com.dl.officialsite.sponsor.Sponsor;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

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

    public DistributeInfo createDistribute(DistributeInfo param) {
        log.info("[createDistribute] param : ", String.valueOf(param));

        // current user
        String creatorAddress = UserSecurityUtils.getUserLogin().getAddress();
        Member member = this.memberManager.requireMemberAddressExist(creatorAddress);
        param.setCreatorId(member.getId());

        if (param.getId() > 0)
            throw new BizException(CodeEnums.ID_NEED_EMPTY);

        if (!chainConfig.isContainId(String.valueOf(param.getChainId())))
            throw new BizException(CodeEnums.INVALID_CHAIN_ID);

        // check name
        Optional<DistributeInfo> optionalRsp = this.distributeRepository
                .findByDistributeNonce(param.getDistributeNonce());
        if (optionalRsp.isPresent())
            throw new BizException(CodeEnums.DUPLICATE_NONCE);

        // check nonce
        optionalRsp = this.distributeRepository.findByDistributeName(param.getDistributeName());
        if (optionalRsp.isPresent())
            throw new BizException(CodeEnums.DUPLICATE_ANME);

        return distributeRepository.save(param);
    }

}
