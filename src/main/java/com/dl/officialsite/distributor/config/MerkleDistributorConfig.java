package com.dl.officialsite.distributor.config;

import com.dl.officialsite.config.bean.Refreshable;
import com.dl.officialsite.config.constant.ConfigEnum;
import com.dl.officialsite.config.service.ServerConfigCacheService;
import com.dl.officialsite.redpacket.config.BaseGraph;
import com.dl.officialsite.redpacket.config.GraphConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

/**
 * @ClassName MerkleDistributorConfig
 * @Author jackchen
 * @Date 2024/7/2 22:15
 * @Description MerkleDistributorConfig
 **/
@Service
@Slf4j
public class MerkleDistributorConfig extends BaseGraph implements Refreshable {

    @Autowired
    private ServerConfigCacheService serverConfigCacheService;

    @Override
    @EventListener(ApplicationReadyEvent.class)
    public void startUpOrRefresh() {
        this.graphConfig = serverConfigCacheService.get(ConfigEnum.MERKLE_DISTRIBUTION_API_KEY, GraphConfig.class);
        log.info("Set up {} MERKLE_DISTRIBUTION_API_KEY config {} ....",
            ConfigEnum.MERKLE_DISTRIBUTION_API_KEY.getConfigName(), this.getGraphConfig());
    }
}
