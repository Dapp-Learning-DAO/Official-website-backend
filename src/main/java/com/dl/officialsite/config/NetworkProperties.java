package com.dl.officialsite.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

import static com.dl.officialsite.config.NetworkProperties.NETWORK_PREFIX;

@Data
@ConfigurationProperties(prefix = NETWORK_PREFIX)
public class NetworkProperties {

    public static final String NETWORK_PREFIX = "network";

    private NetworkDetailProperties[] configs;

    @Data
    public static class NetworkDetailProperties {
        private String id;
        private String name;
        private String rpc;
        private Boolean adminRpc;
        private Long httpTimeoutSeconds;

    }
}
