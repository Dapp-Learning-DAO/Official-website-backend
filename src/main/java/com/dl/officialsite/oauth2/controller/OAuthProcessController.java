package com.dl.officialsite.oauth2.controller;

import com.dl.officialsite.common.model.ServerResponse;
import com.dl.officialsite.oauth2.config.AuthClientConfig;
import com.dl.officialsite.oauth2.config.OAuthConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.keygen.Base64StringKeyGenerator;
import org.springframework.security.crypto.keygen.StringKeyGenerator;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.web.util.UrlUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

/**
 * Process oauth requests.
 */
@RequestMapping("oauth2")
@RestController
public class OAuthProcessController {

    @Autowired
    private OAuthConfig oAuthConfig;

    private StringKeyGenerator DEFAULT_STATE_GENERATOR = new Base64StringKeyGenerator(
            Base64.getUrlEncoder());

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
                                              HttpServletResponse response
                                    ) throws Exception{
        /**
         * 1. Fetch registration
         */
        AuthClientConfig oAuthRegistration = oAuthConfig.getRegistrations().get(registrationId);
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
        String responseType = AuthorizationGrantType.AUTHORIZATION_CODE.getValue();
        String state = DEFAULT_STATE_GENERATOR.generateKey();
        String redirectUri = expandRedirectUri(
                registrationId,
                request,
                oAuthRegistration,
                "bind");



        /**
         * 3. Generate the full auth uri and let the browser redirect to github authorize page
         */
        UriComponents uriComponents = UriComponentsBuilder.fromUriString(oAuthRegistration.getUserAuthorizationUri())
                .queryParam("client_id", clientId)
                .queryParam("response_type", responseType)
                .queryParam("state", state)
                .queryParam("redirect_uri", redirectUri)
                .build();
        response.sendRedirect(uriComponents.toUriString());

    }

    /**
     * This api retrieves the authorization code then :
     * 1. exchange access token via authorization code from:
     * https://github.com/login/oauth/access_token
     *
     * 2. exchange user info via access token from:
     * https://api.github.com/user
     */
    @GetMapping("{action}/code/{registrationId}")
    public String receiveAuthorizationCode(@RequestParam("code") String code,
                                         @RequestParam("state") String state){
        return "已获得授权码！"+code;
    }

    private static String expandRedirectUri(String registration, HttpServletRequest request, AuthClientConfig clientRegistration,
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
