package com.dl.officialsite.oauth2.controller;


import com.dl.officialsite.common.base.BaseResponse;
import com.dl.officialsite.common.utils.HttpSessionUtils;
import com.dl.officialsite.oauth2.AccessTokenCacheService;
import com.dl.officialsite.oauth2.config.GitHubOAuthConfig;
import com.dl.officialsite.oauth2.config.OAuthSessionKey;
import com.dl.officialsite.oauth2.model.bo.AccessTokenResponse;
import com.dl.officialsite.oauth2.model.bo.GithubUserInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.keygen.Base64StringKeyGenerator;
import org.springframework.security.crypto.keygen.StringKeyGenerator;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Base64;

/**
 * Process oauth requests.
 */
@RequestMapping("oauth2")
@RestController
@Slf4j
public class GitHubController {
    private StringKeyGenerator DEFAULT_STATE_GENERATOR = new Base64StringKeyGenerator(Base64.getUrlEncoder());
    private final static String AUTHORIZATION_CODE = "authorization_code";

    private static final char PATH_DELIMITER = '/';
    private static final String DEFAULT_REDIRECT_URL = "{baseUrl}/oauth2/{action}/code/{registrationId}";

    @Autowired
    private GitHubOAuthConfig gitHubOAuthConfig;

    @Autowired
    private RestTemplate restTemplate;

    /**
     * Accept oauth authorization request like:
     * http://localhost:8080/oauth2/authorization/github
     * It returns the github authorization url like:
     * https://github.com/login/oauth/authorize
     * back to the browser through redirect thus
     * the browser then jump to github page to allow user to authenticate himself.
     */
    @RequestMapping("authorization/github")
    public void handleAuthorization(HttpServletResponse response ) throws Exception {

        /**
         * 1. Create auth url parameters, forming:
         * https://authorization-server.com/oauth/authorize
         * ?client_id=a17c21ed
         * &response_type=code
         * &state=5ca75bd30
         * &redirect_uri=https%3A%2F%2Fexample-app.com%2Fauth
         * &scope=photos
         */
        String clientId = gitHubOAuthConfig.getOAuthConfig().getClientId();
        String responseType = AUTHORIZATION_CODE;
        String state = DEFAULT_STATE_GENERATOR.generateKey();

        /**
         * 2. Generate the full auth uri and let the browser redirect to github authorize page
         */
        UriComponents uriComponents = UriComponentsBuilder.fromUriString(gitHubOAuthConfig.getOAuthConfig().getUserAuthorizationUri())
            .queryParam("client_id", clientId)
            .queryParam("response_type", responseType)
            .queryParam("state", state)
            .build();
        response.sendRedirect(uriComponents.toUriString());
    }

    /**
     * This api retrieves the authorization code then :
     * 1. exchange access token via authorization code from:
     * https://github.com/login/oauth/access_token?code=xxx&client_id=xxx&client_secret
     * <p>
     * 2. exchange user info via access token from:
     * https://api.github.com/user
     */
    @GetMapping("bind/code/github")
    public BaseResponse<String> fetchAccessTokenAndUsername(
        @RequestParam("code") String code,
        @RequestParam("state") String state,
        HttpServletRequest request,
        HttpServletResponse response
    ) throws Exception {
        /**
         * 1. Combine access_token request and get access token
         */
        UriComponents uriComponents = UriComponentsBuilder.fromUriString(this.gitHubOAuthConfig.getOAuthConfig().getAccessTokenUri())
            .queryParam("client_id", this.gitHubOAuthConfig.getOAuthConfig().getClientId())
            .queryParam("client_secret", this.gitHubOAuthConfig.getOAuthConfig().getClientSecret())
            .queryParam("state", state)
            .queryParam("code", code)
            .build();
        String accessTokenUrl = uriComponents.toString();
        log.info(accessTokenUrl);
        AccessTokenResponse accessTokenResponse = this.restTemplate.getForObject(accessTokenUrl, AccessTokenResponse.class);
        if (accessTokenResponse == null) {
            throw new RuntimeException("Failed to get access token id ");
        }
        String accessToken = accessTokenResponse.getAccessToken();
        log.info("access token received: {}", accessToken);
        /**
         * 2. Get user info via access_token
         */
        GithubUserInfo userInfo = this.retrieve(this.gitHubOAuthConfig.getOAuthConfig().getUserInfoUri(), accessToken);
        String username = userInfo.getLogin();
        log.info("user info {}", username);
        Assert.notNull(userInfo, "failed to find userInfo");
        // add access token to cache
        AccessTokenCacheService.addGitHubAccessToken(username, accessToken);
        HttpSessionUtils.setOAuthUserName(request.getSession(), OAuthSessionKey.GITHUB_USER_NAME, username);
        /**
         * 3. Bind userInfo
         */
        response.addCookie(new Cookie("oauth_github", username));
        return BaseResponse.successWithData(username);
    }

    /**
     * Refer:
     * https://docs.github.com/en/free-pro-team@latest/rest/users/users?apiVersion=2022-11-28#get-the-authenticated-user
     * @param userInfoUri
     * @param accessToken
     * @return
     */
    private GithubUserInfo retrieve(String userInfoUri, String accessToken) {
        RequestEntity requestEntity = RequestEntity.get(userInfoUri).
            header("Authorization","Bearer "+accessToken)
            .build();

        ResponseEntity<GithubUserInfo> responseEntity = restTemplate.exchange(requestEntity,GithubUserInfo.class);

        System.out.println(responseEntity.getStatusCode());
        System.out.println(responseEntity.getBody());
        return responseEntity.getBody();
    }
}
