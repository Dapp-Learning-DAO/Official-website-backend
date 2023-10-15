package com.dl.officialsite.oauth2;


import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping("oauth")
public class OAuthController {
//    @GetMapping("/github/user")
//    public Map<String, Object> user(@AuthenticationPrincipal OAuth2User principal) {
//        System.out.println(principal);
//        return Collections.singletonMap("name", principal.getAttribute("login"));
//    }

}
