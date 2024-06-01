package com.dl.officialsite.activity;

import com.dl.officialsite.activity.service.MemberTaskService;
import com.dl.officialsite.common.base.BaseResponse;
import com.dl.officialsite.common.exception.BizException;
import com.dl.officialsite.common.utils.HttpSessionUtils;
import com.dl.officialsite.config.ChainConfig;
import com.dl.officialsite.login.model.SessionUserInfo;
import com.dl.officialsite.member.Member;
import com.dl.officialsite.member.MemberRepository;
import com.dl.officialsite.member.MemberService;
import com.dl.officialsite.nft.constant.ContractNameEnum;
import com.dl.officialsite.nft.service.MemberNFTMintService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.Optional;

import static com.dl.officialsite.common.enums.CodeEnums.PARAM_ERROR;

@RestController
@RequestMapping("/dev/activity/")
@Profile({"dev", "local"})
@Slf4j
public class ActivityTestController {
    @Autowired
    private MemberTaskService memberTaskService;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private MemberService memberService;
    @Autowired
    private MemberNFTMintService memberNFTMintService;
    @Autowired
    private ChainConfig chainConfig;

    /**
     * remove the user's activity data for re-testing
     */
    @GetMapping("/clean")
    public BaseResponse clean(@NotNull @RequestParam("chainId") String chainIdParam,
                              @RequestParam(required = false) String addressForTesting,
                              @RequestParam(required = false, defaultValue = "WarCraft") String contractName,
                              HttpSession session) {
        String chainId = Arrays.stream(chainConfig.getIds()).filter(id -> StringUtils.equalsIgnoreCase(chainIdParam, id))
            .findFirst()
            .orElseThrow(() -> new BizException(PARAM_ERROR.getCode(), String.format("Chain id %s not exists", chainIdParam)));


        SessionUserInfo sessionUserInfo = HttpSessionUtils.getMember(session);
        final String address = sessionUserInfo != null ? sessionUserInfo.getAddress() : addressForTesting;

        Optional<Member> memberOptional = memberRepository.findByAddress(address);
        if (!memberOptional.isPresent()) {
            BaseResponse.failWithReason("1001", "no user found");
        }

        this.memberTaskService.clean(address);

        this.memberNFTMintService.clean(address, ContractNameEnum.fromValue(contractName), chainId);

        this.memberService.cleanGitHubTgAndDiscordId(address);

        return BaseResponse.successWithData("GitHub, Telegram, Discord data are removed.");
    }
}