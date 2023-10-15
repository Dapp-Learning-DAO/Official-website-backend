package com.dl.officialsite.oauth2.controller;

import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

/**
 * Process oauth requests.
 */
@RequestMapping("oauth2")
@RestController
public class OAuthProcessController {

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
                                    HttpServletResponse response
                                    ){

        


    }

    /**
     * This api retrieves the authorization code then :
     * 1. exchange access token via authorization code from:
     * https://github.com/login/oauth/access_token
     *
     * 2. exchange user info via access token from:
     * https://api.github.com/user
     */
    //http://localhost:8080/login/oauth2/code/github?code=xx&state=xxx
    @GetMapping("code/{github}")
    public void receiveAuthorizationCode(@RequestParam("code") String code,
                                         @RequestParam("state") String state){

    }
}
