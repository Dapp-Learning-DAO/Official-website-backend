package com.dl.officialsite.oauth2.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesBinding;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.Map;

@Data
@Component
@ConfigurationProperties("oauth")
@ConfigurationPropertiesBinding()
public class OAuthConfig {

    @NestedConfigurationProperty
    private Map<String, AuthClientConfig> registrations;
}
