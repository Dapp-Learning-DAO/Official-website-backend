package com.dl.officialsite.oauth2.config;

import com.dl.officialsite.config.bean.Refreshable;
import com.dl.officialsite.config.constant.ConfigEnum;
import com.dl.officialsite.config.service.ServerConfigCacheService;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.social.twitter.connect.TwitterConnectionFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
@Getter
public class TwitterOAuthConfig extends BaseOAuth implements Refreshable {
    private TwitterConnectionFactory connectionFactory = null;

    @Autowired private ServerConfigCacheService serverConfigCacheService;

    @EventListener(ApplicationReadyEvent.class)
    public void startUpOrRefresh() {
        this.oAuthConfig = serverConfigCacheService.get(ConfigEnum.TWITTER_OAUTH_CONFIG, OAuthConfig.class);
        Optional.ofNullable(this.oAuthConfig).ifPresent(config -> {
            connectionFactory = new TwitterConnectionFactory(config.getClientId(), config.getClientSecret());
        });
        log.info("Set up {} OAuth config {} ....", ConfigEnum.TWITTER_OAUTH_CONFIG.getConfigName(), this.getOAuthConfig());
    }
}