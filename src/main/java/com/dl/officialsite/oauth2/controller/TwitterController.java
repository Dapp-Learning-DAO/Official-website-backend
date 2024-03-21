package com.dl.officialsite.oauth2.controller;


import com.dl.officialsite.common.base.BaseResponse;
import com.dl.officialsite.common.utils.HttpSessionUtils;
import com.dl.officialsite.common.utils.UserSecurityUtils;
import com.dl.officialsite.oauth2.config.OAuthConfig;
import com.dl.officialsite.oauth2.config.OAuthSessionKey;
import com.dl.officialsite.oauth2.config.RegistrationConfig;
import com.dl.officialsite.oauth2.model.bo.TwitterVerifyResponse;
import com.nimbusds.jose.util.Pair;
import java.io.IOException;
import java.util.HashMap;
import java.util.Optional;
import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.oauth1.AuthorizedRequestToken;
import org.springframework.social.oauth1.OAuth1Operations;
import org.springframework.social.oauth1.OAuth1Parameters;
import org.springframework.social.oauth1.OAuthToken;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.social.twitter.api.TwitterProfile;
import org.springframework.social.twitter.api.impl.TwitterTemplate;
import org.springframework.social.twitter.connect.TwitterConnectionFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class TwitterController {

    @Autowired
    private OAuthConfig oAuthConfig;
    private TwitterConnectionFactory connectionFactory = null;
    RegistrationConfig twitterConfig = null;


    @PostConstruct
    public void setUpTwitter() {
        twitterConfig = oAuthConfig.getRegistrations().get("twitter");
        if (twitterConfig == null) {
            //TODO
            throw new RuntimeException("Invalid registrationId");
        }
        connectionFactory = new TwitterConnectionFactory(twitterConfig.getClientId(), twitterConfig.getClientSecret());
        log.info("Successfully set up Twitter....");
    }

    @GetMapping("/oauth2/authorize/normal/twitter")
    public void twitterOauthLogin(@RequestParam(name = "test", defaultValue = "false") boolean test, HttpServletResponse response)
        throws IOException {
        String authorizeUrl = this.generateAuthorizeUrl(test);
        response.sendRedirect(authorizeUrl);
    }

    private String generateAuthorizeUrl(boolean test) {
        OAuth1Operations oauthOperations = connectionFactory.getOAuthOperations();
        OAuthToken requestToken = oauthOperations.fetchRequestToken(twitterConfig.getCallbackUrl(), null);

        OAuth1Parameters oAuth1Parameters = new OAuth1Parameters(new HashMap<>());
        if (test) {
            oAuth1Parameters.add("secret", requestToken.getSecret());
        } else {
            // Save user's oauth_token_secret for exchanging the profile
            UserSecurityUtils.getUserLogin().setTwitterOauthTokenSecret(requestToken.getSecret());
        }
        log.info("oAuthToken: {}, secret: {}", requestToken.getValue(), requestToken.getSecret());
        return oauthOperations.buildAuthorizeUrl(requestToken.getValue(), oAuth1Parameters);
    }

    @GetMapping("/oauth2/callback/twitter")
    public BaseResponse getTwitter(@RequestParam("oauth_token") String oauthToken, @RequestParam("oauth_verifier") String oauthVerifier,
                                   @RequestParam(value = "secret", required = false) String secret, HttpServletRequest request) {
        Pair<String, String> twitterUserNameAndScreenName = fetchProfile(oauthToken, oauthVerifier, secret);
        HttpSessionUtils.setOAuthUserName(request.getSession(), OAuthSessionKey.TWITTER_USER_NAME, twitterUserNameAndScreenName.getLeft());
        HttpSessionUtils.setOAuthUserName(request.getSession(), OAuthSessionKey.TWITTER_SCREEN_NAME,
            twitterUserNameAndScreenName.getRight());
        TwitterVerifyResponse response = new TwitterVerifyResponse();
        response.setNickName(twitterUserNameAndScreenName.getLeft());
        response.setId(twitterUserNameAndScreenName.getRight());
        return BaseResponse.successWithData(response);
    }

    private Pair<String, String> fetchProfile(String oAuthToken, String verifier, String secret) {
        OAuth1Operations oauthOperations = connectionFactory.getOAuthOperations();


        String twitterOauthTokenSecret =
            Optional.ofNullable(secret).orElseGet(() -> UserSecurityUtils.getUserLogin().getTwitterOauthTokenSecret());

        OAuthToken accessToken = oauthOperations.exchangeForAccessToken(
            new AuthorizedRequestToken(new OAuthToken(oAuthToken, twitterOauthTokenSecret), verifier), OAuth1Parameters.NONE);

        Twitter twitter = new TwitterTemplate(twitterConfig.getClientId(), twitterConfig.getClientSecret(), accessToken.getValue(),
            accessToken.getSecret());
        TwitterProfile profile = twitter.userOperations().getUserProfile();
        log.info("User's name:[{} : {}]", profile.getName(), profile.getScreenName());
        return Pair.of(profile.getName(), profile.getScreenName());
    }

//    public static void main(String[] args) {
//        OAuthConfig oAuthConfig1 = new OAuthConfig();
//        Map<String, RegistrationConfig> registrationConfigMap = new HashMap<>();
//        oAuthConfig1.setRegistrations(registrationConfigMap);
//        registrationConfigMap.put(
//            "twitter",
//            // 1st parameter: apiKey
//            // 2st parameter: apiSecret
//            // 6st parameter: callbackUrl
//            new RegistrationConfig("", "",
//                null, null, null,
//                ""));
//
//
//        TwitterController twitterController = new TwitterController();
//        twitterController.oAuthConfig = oAuthConfig1;
//        twitterController.setUpTwitter();
//
//
////        String test = twitterController.generateAuthorizeUrl(true);
////        System.out.println(test);
//
//        // 1st parameter: oauth_token
//        // 2st parameter: oauth_verifier
//        // 3st parameter: secret
//        String name = twitterController.fetchProfile("", "", "");
//        System.out.println(name);
//    }
}
