package com.dl.officialsite.oauth2.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegistrationConfig {

    private String clientId;

    private String clientSecret;

    private String accessTokenUri;

    private String userAuthorizationUri;

    private String userInfoUri;

}
