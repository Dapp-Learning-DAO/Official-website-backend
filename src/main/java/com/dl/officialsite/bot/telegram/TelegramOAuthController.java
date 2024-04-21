package com.dl.officialsite.bot.telegram;


import com.dl.officialsite.bot.util.TelegramVerifyValidator;
import com.dl.officialsite.common.base.BaseResponse;
import com.dl.officialsite.common.utils.HttpSessionUtils;
import com.dl.officialsite.login.model.SessionUserInfo;
import com.dl.officialsite.member.Member;
import com.dl.officialsite.member.MemberRepository;
import com.dl.officialsite.oauth2.config.OAuthConfig;
import com.dl.officialsite.oauth2.config.RegistrationConfig;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.twitter.connect.TwitterConnectionFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpSession;
import java.util.Map;
import java.util.Optional;

@RestController
@Slf4j
public class TelegramOAuthController {

    @Autowired
    private OAuthConfig oAuthConfig;
    @Autowired
    private MemberRepository memberRepository;

    private TwitterConnectionFactory connectionFactory = null;
    RegistrationConfig telegramConfig = null;


    @PostConstruct
    public void setUpTelegram() {
        telegramConfig = oAuthConfig.getRegistrations().get("telegram");
        if (telegramConfig == null) {
            throw new RuntimeException("Invalid registrationId");
        }
        log.info("Successfully set up Telegram login validator");
    }

    @GetMapping("/oauth2/callback/telegram")
    public BaseResponse verifyTelegram(@RequestParam Map<String, String> params, @RequestParam(required = false) String addressForTesting,
                                       HttpSession session) {
        SessionUserInfo sessionUserInfo = HttpSessionUtils.getMember(session);
        final String address = sessionUserInfo != null ? sessionUserInfo.getAddress() : addressForTesting;
        params.remove("addressForTesting");

        boolean verifyResult = TelegramVerifyValidator.verifyTelegramParameter(params, telegramConfig.getClientSecret());
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
