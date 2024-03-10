/**
 *
 */


package com.dl.officialsite.oauth2.config;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public enum OAuthSessionKey {
    GITHUB_USER_NAME("GITHUB_USER_NAME"),
    TWITTER_USER_NAME("TWITTER_USER_NAME")
    ;

    private String sessionKey;


}
