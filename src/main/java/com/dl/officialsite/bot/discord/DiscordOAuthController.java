package com.dl.officialsite.bot.discord;


import com.dl.officialsite.common.base.BaseResponse;
import com.dl.officialsite.common.utils.HttpSessionUtils;
import com.dl.officialsite.login.model.SessionUserInfo;
import com.dl.officialsite.member.Member;
import com.dl.officialsite.member.MemberRepository;
import com.dl.officialsite.oauth2.config.OAuthConfig;
import com.dl.officialsite.oauth2.config.RegistrationConfig;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@Slf4j
public class DiscordOAuthController {
    private static final String O_AUTH_URL = "https://discord.com/api/oauth2/authorize?client_id={clientId}&redirect_uri={callbackUrl}" +
        "&response_type=code&scope=identify%20guilds";
    private static final String DISCORD_TOKEN_URL = "https://discord.com/api/v9/oauth2/token";
    private static final String DISCORD_USER_INFO_URL = "https://discord.com/api/v9/users/@me";

    @Autowired
    private OAuthConfig oAuthConfig;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private RestTemplate restTemplate;

    RegistrationConfig discordConfig = null;


    @PostConstruct
    public void setUpTwitter() {
        discordConfig = oAuthConfig.getRegistrations().get("discord");
        if (discordConfig == null) {
            //TODO
            throw new RuntimeException("Invalid registrationId");
        }
        log.info("Successfully set up Discord....");
    }

    @GetMapping("/oauth2/authorize/normal/discord")
    public void twitterOauthLogin(@RequestParam(name = "test", defaultValue = "false") boolean test, HttpServletResponse response)
        throws IOException {
        Map<String, String> uriVariables = new HashMap<>();
        uriVariables.put("clientId", discordConfig.getClientId());
        uriVariables.put("callbackUrl", discordConfig.getCallbackUrl());
        response.sendRedirect(UriComponentsBuilder.fromUriString(O_AUTH_URL).buildAndExpand(uriVariables)
            .toUriString());
    }

    @GetMapping("/oauth2/callback/discord")
    public BaseResponse getTwitter(@RequestParam("code") String code, HttpSession session) {
        String accessToken = fetchAccessToken(code);
        if (StringUtils.isBlank(accessToken)) {
            return BaseResponse.failWithReason("1101", "Login Discord failed.");
        }

        String discordUserId = fetchUserId(accessToken);
        if (StringUtils.isBlank(discordUserId)) {
            return BaseResponse.failWithReason("1101", "Fetch Discord user id failed.");
        }

        SessionUserInfo sessionUserInfo = HttpSessionUtils.getMember(session);
        String address = sessionUserInfo.getAddress();
        Optional<Member> member = this.memberRepository.findByAddress(address);
        if (!member.isPresent()) {
            return BaseResponse.failWithReason("1001", "no user found"); // 用户需要注册
        }
        member.get().setDiscordId(discordUserId);
        memberRepository.save(member.get());
        return BaseResponse.success();
    }

    private String fetchAccessToken(String code) {
        // Set request headers
        HttpHeaders headers = new HttpHeaders();

        // Build request body with form data
        MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
        requestBody.add("client_id", discordConfig.getClientId());
        requestBody.add("client_secret", discordConfig.getClientSecret());
        requestBody.add("grant_type", "authorization_code");
        requestBody.add("code", code);
        requestBody.add("redirect_uri", discordConfig.getCallbackUrl());
        requestBody.add("scope", "identify guilds");

        // Build the HTTP request entity
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(requestBody, headers);

        // Send POST request to Discord API
        ResponseEntity<DiscordTokenResponse> responseEntity = restTemplate.exchange(
            DISCORD_TOKEN_URL,
            HttpMethod.POST,
            requestEntity,
            DiscordTokenResponse.class
        );

        // Handle the response
        HttpStatus statusCode = responseEntity.getStatusCode();
        DiscordTokenResponse responseBody = responseEntity.getBody();

        if (statusCode == HttpStatus.OK && responseBody != null) {
            // Successfully retrieved access_token and expires_in
            return responseBody.getAccessToken();
        }
        return null;
    }

    private String fetchUserId(String accessToken){
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);

        HttpEntity<String> requestEntity = new HttpEntity<>(headers);
        ResponseEntity<DiscordUserInfo> responseEntity = restTemplate.exchange(
            DISCORD_USER_INFO_URL,
            HttpMethod.GET,
            requestEntity,
            DiscordUserInfo.class
        );

        // 处理响应
        HttpStatus statusCode = responseEntity.getStatusCode();
        DiscordUserInfo responseBody = responseEntity.getBody();

        if (statusCode == HttpStatus.OK && responseBody != null) {
            return responseBody.getId();
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
        @JsonProperty("token_type")
        private String tokenType;

        @JsonProperty("access_token")
        private String accessToken;

        @JsonProperty("expires_in")
        private int expiresIn;

        @JsonProperty("refresh_token")
        private String refreshToken;

        @JsonProperty("scope")
        private String scope;
    }
}
