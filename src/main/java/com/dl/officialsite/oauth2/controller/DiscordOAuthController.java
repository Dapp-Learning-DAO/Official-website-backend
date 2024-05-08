package com.dl.officialsite.oauth2.controller;


import com.dl.officialsite.common.base.BaseResponse;
import com.dl.officialsite.common.utils.GsonUtil;
import com.dl.officialsite.common.utils.HttpSessionUtils;
import com.dl.officialsite.common.utils.HttpUtil;
import com.dl.officialsite.login.model.SessionUserInfo;
import com.dl.officialsite.member.Member;
import com.dl.officialsite.member.MemberRepository;
import com.dl.officialsite.oauth2.config.DiscordOAuthConfig;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@Slf4j
public class DiscordOAuthController {
    private static final String O_AUTH_URL = "https://discord.com/api/oauth2/authorize?client_id={clientId}&redirect_uri={callbackUrl}" +
        "&response_type=code&scope=identify";
    private static final String DISCORD_TOKEN_URL = "https://discord.com/api/v9/oauth2/token";
    private static final String DISCORD_USER_INFO_URL = "https://discord.com/api/v9/users/@me";

    @Autowired
    private DiscordOAuthConfig discordOAuthConfig;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("oauth2/authorization/discord")
    public void twitterOauthLogin(@RequestParam(name = "test", defaultValue = "false") boolean test, HttpServletResponse response)
        throws IOException {
        Map<String, String> uriVariables = new HashMap<>();
        uriVariables.put("clientId", this.discordOAuthConfig.getOAuthConfig().getClientId());
        uriVariables.put("callbackUrl", this.discordOAuthConfig.getOAuthConfig().getCallbackUrl());
        response.sendRedirect(UriComponentsBuilder.fromUriString(O_AUTH_URL).buildAndExpand(uriVariables)
            .toUriString());
    }

    @PostMapping("oauth2/callback/discord")
    public BaseResponse discordCallback(@RequestParam("code") String code, @RequestParam(required = false) String addressForTesting,
                                   HttpSession session) {
        // 检查用户是否注册
        SessionUserInfo sessionUserInfo = HttpSessionUtils.getMember(session);
        final String address = sessionUserInfo != null ? sessionUserInfo.getAddress() : addressForTesting;
        Optional<Member> member = this.memberRepository.findByAddress(address);
        if (!member.isPresent()) {
            return BaseResponse.failWithReason("1001", "no user found"); // 用户需要注册
        }

        // 获取  access token
        String accessToken = fetchAccessToken(code);
        if (StringUtils.isBlank(accessToken)) {
            return BaseResponse.failWithReason("1101", "Fetch Discord access token failed.");
        }

        String discordUserId = fetchUserId(accessToken);
        if (StringUtils.isBlank(discordUserId)) {
            return BaseResponse.failWithReason("1102", "Fetch Discord user id failed.");
        }

        member.get().setDiscordId(discordUserId);
        memberRepository.save(member.get());
        return BaseResponse.success();
    }

    private String fetchAccessToken(String code) {
        List<BasicNameValuePair> paramList = getParams(code);
        HttpPost httpPost = new HttpPost(DISCORD_TOKEN_URL);
        try {
            httpPost.setEntity(new UrlEncodedFormEntity(paramList));
            HttpResponse response = HttpUtil.client().execute(httpPost);
            // Handle response
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                String responseBody = EntityUtils.toString(entity);
                log.info("Discord fetch user access token response:{}", responseBody);
                return GsonUtil.fromJson(responseBody, DiscordTokenResponse.class).getAccess_token();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private List<BasicNameValuePair> getParams(String code) {
        List<BasicNameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("client_id", this.discordOAuthConfig.getOAuthConfig().getClientId()));
        params.add(new BasicNameValuePair("client_secret", this.discordOAuthConfig.getOAuthConfig().getClientSecret()));
        params.add(new BasicNameValuePair("grant_type", "authorization_code"));
        params.add(new BasicNameValuePair("code", code));
        params.add(new BasicNameValuePair("redirect_uri", this.discordOAuthConfig.getOAuthConfig().getCallbackUrl()));
        params.add(new BasicNameValuePair("scope", "guilds.members.read"));
        return params;
    }

    private String fetchUserId(String accessToken) {
        HttpGet httpGet = new HttpGet(DISCORD_USER_INFO_URL);
        httpGet.setHeader(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken);

        try {
            HttpResponse response = HttpUtil.client().execute(httpGet);

            // Handle response
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                String responseBody = EntityUtils.toString(entity);
                log.info("Discord fetch user id response:{}", responseBody);
                return GsonUtil.fromJson(responseBody, DiscordUserInfo.class).getId();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Data
    public static class DiscordUserInfo {
        private String id;
        private String username;
        private String avatar;
        private String discriminator;
        private int publicFlags;
        private int flags;
        private String banner;
        private String accentColor;
        private String globalName;
        private String avatarDecorationData;
        private String bannerColor;
        private String clan;
        private boolean mfaEnabled;
        private String locale;
        private int premiumType;
    }

    @Data
    public static class DiscordTokenResponse {
        private String token_type;
        private String access_token;
        private int expires_in;
        private String refresh_token;
        private String scope;
    }
}
