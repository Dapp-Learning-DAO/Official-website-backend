package com.dl.officialsite.redpacket.config;

import com.dl.officialsite.config.bean.Refreshable;
import com.dl.officialsite.config.constant.ConfigEnum;
import com.dl.officialsite.config.service.ServerConfigCacheService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

/**
 * @ClassName RedPacketConfig
 * @Author jackchen
 * @Date 2024/7/2 20:12
 * @Description RedPacketConfig
 **/
@Service
@Slf4j
public class RedPacketConfig extends BaseGraph implements Refreshable {

    @Autowired
    private ServerConfigCacheService serverConfigCacheService;

    @Override
    @EventListener(ApplicationReadyEvent.class)
    public void startUpOrRefresh() {
        this.graphConfig = serverConfigCacheService.get(ConfigEnum.RED_PACKET_API_KEY, GraphConfig.class);
        log.info("Set up {} RED_PACKET_API_KEY config {} ....",
            ConfigEnum.RED_PACKET_API_KEY.getConfigName(), this.getGraphConfig());
    }
}
