package com.dl.officialsite.oauth2.config;

import com.dl.officialsite.config.bean.Refreshable;
import com.dl.officialsite.config.constant.ConfigEnum;
import com.dl.officialsite.config.service.ServerConfigCacheService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class DiscordOAuthConfig extends BaseOAuth implements Refreshable {

    @Autowired
    private ServerConfigCacheService serverConfigCacheService;

    @EventListener(ApplicationReadyEvent.class)
    public void startUpOrRefresh() {
        this.oAuthConfig = serverConfigCacheService.get(ConfigEnum.DISCORD_OAUTH_CONFIG, OAuthConfig.class);
        log.info("Set up {} OAuth config {} ....", ConfigEnum.DISCORD_OAUTH_CONFIG.getConfigName(), this.getOAuthConfig());
    }
}