package com.dl.officialsite.oauth2.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeResourceDetails;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OAuthRegistration {

    private String authUrl;

    @NestedConfigurationProperty
    private AuthorizationCodeResourceDetails client;

    @NestedConfigurationProperty
    private OAuthResourceConfig resource;
}
