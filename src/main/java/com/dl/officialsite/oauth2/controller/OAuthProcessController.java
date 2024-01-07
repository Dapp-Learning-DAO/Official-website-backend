package com.dl.officialsite.oauth2.controller;


import com.dl.officialsite.common.base.BaseResponse;
import com.dl.officialsite.common.utils.HttpSessionUtils;
import com.dl.officialsite.common.utils.UserSecurityUtils;
import com.dl.officialsite.oauth2.config.RegistrationConfig;
import com.dl.officialsite.oauth2.config.OAuthConfig;
import com.dl.officialsite.oauth2.handler.bind.IOAuthBindHandler;
import com.dl.officialsite.oauth2.handler.bind.OAuthBindHandlers;
import com.dl.officialsite.oauth2.handler.userinfo.IUserInfoRetrieveHandler;
import com.dl.officialsite.oauth2.handler.userinfo.UserInfoRetrieveHandlers;
import com.dl.officialsite.oauth2.manager.OAuthUsernameManager;
import com.dl.officialsite.oauth2.model.bo.AccessTokenResponse;
import com.dl.officialsite.oauth2.model.bo.user.IUserInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.manager.util.SessionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.keygen.Base64StringKeyGenerator;
import org.springframework.security.crypto.keygen.StringKeyGenerator;
import org.springframework.security.web.util.UrlUtils;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

/**
 * Process oauth requests.
 */
@RequestMapping("oauth2")
@RestController
@Slf4j
public class OAuthProcessController {

    @Autowired
    private OAuthConfig oAuthConfig;

    private StringKeyGenerator DEFAULT_STATE_GENERATOR = new Base64StringKeyGenerator(
            Base64.getUrlEncoder());

    private final static String AUTHORIZATION_CODE = "authorization_code";

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private UserInfoRetrieveHandlers userInfoRetrieveHandlers;

    @Autowired
    private OAuthBindHandlers bindHandlers;

    @Autowired
    private OAuthUsernameManager usernameManager;

    private static final char PATH_DELIMITER = '/';

    private static final String DEFAULT_REDIRECT_URL = "{baseUrl}/oauth2/{action}/code/{registrationId}";
    /**
     * Accept oauth authorization request like:
     *  http://localhost:8080/oauth2/authorization/github
     * It returns the github authorization url like:
     *  https://github.com/login/oauth/authorize
     * back to the browser through redirect thus
     * the browser then jump to github page to allow user to authenticate himself.
     */
    @RequestMapping("authorization/{registrationId}")
    public void handleAuthorization(@PathVariable("registrationId") String registrationId,
                                              HttpServletRequest request,
                                              HttpServletResponse response,
                                            HttpSession httpSession
                                    ) throws Exception{

//        HttpSessionUtils.putMember(httpSession, "test");

        /**
         * 1. Preconditions
         */
        RegistrationConfig oAuthRegistration = oAuthConfig.getRegistrations().get(registrationId);
        if (oAuthRegistration == null){
            //TODO
            throw new RuntimeException("Invalid registrationId");
        }

        /**
         * 2. Create auth url parameters, forming:
         * https://authorization-server.com/oauth/authorize
         * ?client_id=a17c21ed
         * &response_type=code
         * &state=5ca75bd30
         * &redirect_uri=https%3A%2F%2Fexample-app.com%2Fauth
         * &scope=photos
         */
        String clientId = oAuthRegistration.getClientId();
        String responseType = AUTHORIZATION_CODE;
        String state = DEFAULT_STATE_GENERATOR.generateKey();
//        String redirectUri = expandRedirectUri(
//                registrationId,
//                request,
//                oAuthRegistration,
//                "bind");


        /**
         * 3. Generate the full auth uri and let the browser redirect to github authorize page
         */
        UriComponents uriComponents = UriComponentsBuilder.fromUriString(oAuthRegistration.getUserAuthorizationUri())
                .queryParam("client_id", clientId)
                .queryParam("response_type", responseType)
                .queryParam("state", state)
//                .queryParam("redirect_uri", redirectUri)
                .build();
        response.sendRedirect(uriComponents.toUriString());

    }

    /**
     * This api retrieves the authorization code then :
     * 1. exchange access token via authorization code from:
     * https://github.com/login/oauth/access_token?code=xxx&client_id=xxx&client_secret
     *
     * 2. exchange user info via access token from:
     * https://api.github.com/user
     */
    @GetMapping("{action}/code/{registrationId}")
    public BaseResponse<String> receiveAuthorizationCode(
            @PathVariable String registrationId,
            @RequestParam("code") String code,
            @RequestParam("state") String state,
            HttpServletResponse response,
            HttpSession httpSession
            ) throws Exception{

        /**
         * 1. Fetch registration
         */
        RegistrationConfig registration = this.oAuthConfig.getRegistrations().get(registrationId);
        if (registration == null){
            throw new RuntimeException("Invalid registration id :"+registrationId);
        }
        /**
         * 2. Combine access_token request and get access token
         */
        UriComponents uriComponents = UriComponentsBuilder.fromUriString(registration.getAccessTokenUri())
                .queryParam("client_id", registration.getClientId())
                .queryParam("client_secret", registration.getClientSecret())
                .queryParam("state", state)
                .queryParam("code", code)
                .build();
        String accessTokenUrl = uriComponents.toString();
        log.info(accessTokenUrl);
        AccessTokenResponse accessTokenResponse = this.restTemplate.getForObject(accessTokenUrl, AccessTokenResponse.class);
        if (accessTokenResponse == null){
            throw new RuntimeException("Failed to get access token id ");
        }
        String accessToken = accessTokenResponse.getAccessToken();
        log.info("access token received");
        /**
         * 3. Get user info via access_token
         */
        IUserInfoRetrieveHandler retrieveHandler = this.userInfoRetrieveHandlers.get(registrationId);
        Assert.notNull(retrieveHandler, "retrieveHandler not found:"+registrationId);
        IUserInfo userInfo = retrieveHandler.retrieve(registration.getUserInfoUri(), accessToken);
        log.info("user info {}", userInfo.getUsername());
        Assert.notNull(userInfo, "failed to find userInfo");
        /**
         * 4. Bind userInfo
         */
        IOAuthBindHandler bindHandler = this.bindHandlers.get(registrationId);
        Assert.notNull(bindHandler, "bindHandler not found:"+registrationId);

        bindHandler.bind(UserSecurityUtils.getUserLogin().getAddress(), userInfo);

        response.addCookie(new Cookie("oauth_"+registrationId, userInfo.getUsername()));
        return BaseResponse.successWithData(userInfo.getUsername());
    }

    @GetMapping("username/{registrationId}")
    public BaseResponse<String> getOAuthUserName(
            @PathVariable String registrationId,
            HttpServletResponse response,
            HttpSession httpSession
    ) throws Exception{

        /**
         * 1. Prechecks
         */
        RegistrationConfig registration = this.oAuthConfig.getRegistrations().get(registrationId);
        if (registration == null){
            throw new RuntimeException("Invalid registration id :"+registrationId);
        }
        String address = UserSecurityUtils.getUserLogin().getAddress();
        /**
         * 2. Fetch oauth username
         */
        IUserInfo userInfo = usernameManager.get(registrationId, address);
        if(userInfo == null){
            throw new RuntimeException("user not found");
        }
        return BaseResponse.successWithData(userInfo.getUsername());
    }

    private static String expandRedirectUri(String registration, HttpServletRequest request, RegistrationConfig clientRegistration,
                                            String action) {
        Map<String, String> uriVariables = new HashMap<>();
        uriVariables.put("registrationId", registration);
        // @formatter:off
        UriComponents uriComponents = UriComponentsBuilder.fromHttpUrl(UrlUtils.buildFullRequestUrl(request))
                .replacePath(request.getContextPath())
                .replaceQuery(null)
                .fragment(null)
                .build();
        // @formatter:on
        String scheme = uriComponents.getScheme();
        uriVariables.put("baseScheme", (scheme != null) ? scheme : "");
        String host = uriComponents.getHost();
        uriVariables.put("baseHost", (host != null) ? host : "");
        // following logic is based on HierarchicalUriComponents#toUriString()
        int port = uriComponents.getPort();
        uriVariables.put("basePort", (port == -1) ? "" : ":" + port);
        String path = uriComponents.getPath();
        if (StringUtils.hasLength(path)) {
            if (path.charAt(0) != PATH_DELIMITER) {
                path = PATH_DELIMITER + path;
            }
        }
        uriVariables.put("basePath", (path != null) ? path : "");
        uriVariables.put("baseUrl", uriComponents.toUriString());
        uriVariables.put("action", (action != null) ? action : "");
        return UriComponentsBuilder.fromUriString(DEFAULT_REDIRECT_URL).buildAndExpand(uriVariables)
                .toUriString();
    }
}
