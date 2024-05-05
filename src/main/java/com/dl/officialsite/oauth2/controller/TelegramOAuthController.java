package com.dl.officialsite.oauth2.controller;


import com.dl.officialsite.bot.util.TelegramVerifyValidator;
import com.dl.officialsite.common.base.BaseResponse;
import com.dl.officialsite.common.utils.HttpSessionUtils;
import com.dl.officialsite.login.model.SessionUserInfo;
import com.dl.officialsite.member.Member;
import com.dl.officialsite.member.MemberRepository;
import com.dl.officialsite.oauth2.config.TelegramOAuthConfig;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.twitter.connect.TwitterConnectionFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.Map;
import java.util.Optional;

@RestController
@Slf4j
public class TelegramOAuthController {
    @Autowired
    private TelegramOAuthConfig telegramOAuthConfig;
    @Autowired
    private MemberRepository memberRepository;

    private TwitterConnectionFactory connectionFactory = null;

    @PostMapping("oauth2/callback/telegram")
    public BaseResponse verifyTelegram(@RequestParam Map<String, String> params, @RequestParam(required = false) String addressForTesting,
                                       HttpSession session) {
        SessionUserInfo sessionUserInfo = HttpSessionUtils.getMember(session);
        final String address = sessionUserInfo != null ? sessionUserInfo.getAddress() : addressForTesting;
        params.remove("addressForTesting");

        boolean verifyResult = TelegramVerifyValidator.verifyTelegramParameter(params, this.telegramOAuthConfig.getOAuthConfig().getClientSecret());
        if (verifyResult) {
            Optional<Member> member = this.memberRepository.findByAddress(address);
            if (!member.isPresent()) {
                return BaseResponse.failWithReason("1001", "no user found"); // 用户需要注册
            }
            if (StringUtils.equals(params.get("id"),member.get().getTelegramUserId())) {
                return BaseResponse.failWithReason("1104", "Telegram user id is same to the Telegram user id in Database."); // 用户需要注册
            }

            String telegramUserId = params.get("id");
            String telegramUserName = params.get("username");
            member.get().setTelegramUserId(telegramUserId);
            member.get().setTelegramId(telegramUserName);
            memberRepository.save(member.get());
            return BaseResponse.success();
        }
        return BaseResponse.failWithReason("1103", "Telegram request verify failed.");
    }
}
