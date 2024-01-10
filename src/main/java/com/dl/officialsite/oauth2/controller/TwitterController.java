package com.dl.officialsite.oauth2.controller;


import com.dl.officialsite.oauth2.config.OAuthConfig;
import com.dl.officialsite.oauth2.config.RegistrationConfig;
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
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@Slf4j
public class TwitterController {


    @Autowired
    private OAuthConfig oAuthConfig;

    @GetMapping("/oauth2/callback/twitter")
    public void getTwitter() {

        RegistrationConfig oAuthRegistration = oAuthConfig.getRegistrations().get("twitter");
        if (oAuthRegistration == null){
            //TODO
            throw new RuntimeException("Invalid registrationId");
        }

        log.info("oAuthRegistration.getClientId() = " + oAuthRegistration.getClientId());
        log.info("oAuthRegistration.getClientSecret() = " + oAuthRegistration.getClientSecret());
        TwitterConnectionFactory connectionFactory =
                new TwitterConnectionFactory(oAuthRegistration.getClientId(),oAuthRegistration.getClientSecret());
        OAuth1Operations oauthOperations = connectionFactory.getOAuthOperations();
        OAuthToken requestToken = oauthOperations.fetchRequestToken("https://api.twitter.com/oauth/request_token", null);

        OAuthToken accessToken = oauthOperations.exchangeForAccessToken(
                new AuthorizedRequestToken(requestToken, ""), null);
        System.out.println("Token Value:- accesstoken");
//        accessToken.getSecret();
//        accessToken.getValue();
        Twitter twitter = new TwitterTemplate(oAuthRegistration.getClientId(),
                oAuthRegistration.getClientSecret(),
                accessToken.getValue(),
                accessToken.getSecret());
        TwitterProfile profile = twitter.userOperations().getUserProfile();
        System.out.println(profile.toString());

    }


    @GetMapping("/oauth2/authorize/normal/twitter")
    public void twitterOauthLogin(HttpServletRequest request, HttpServletResponse response) throws IOException {

        RegistrationConfig oAuthRegistration = oAuthConfig.getRegistrations().get("twitter");
        if (oAuthRegistration == null){
            throw new RuntimeException("Invalid registrationId");
        }
        TwitterConnectionFactory connectionFactory =
                new TwitterConnectionFactory(oAuthRegistration.getClientId(),oAuthRegistration.getClientSecret());
        OAuth1Operations oauthOperations = connectionFactory.getOAuthOperations();
        oauthOperations.toString();
        OAuthToken requestToken = oauthOperations.fetchRequestToken("https://api.twitter.com/oauth/request_token", null);
        String authorizeUrl = oauthOperations.buildAuthorizeUrl(String.valueOf(requestToken), OAuth1Parameters.NONE);
        response.sendRedirect(authorizeUrl);
    }
}
