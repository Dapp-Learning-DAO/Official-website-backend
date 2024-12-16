package com.dl.officialsite.sharing.config;

import com.dl.officialsite.config.bean.Configurable;
import com.dl.officialsite.config.bean.Refreshable;
import com.dl.officialsite.config.constant.ConfigEnum;
import com.dl.officialsite.config.service.ServerConfigCacheService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@Slf4j
public class ShareTagConfigService implements Refreshable {
    private ShareTagConfig shareTagConfig;

    @Autowired
    private ServerConfigCacheService serverConfigCacheService;
    @EventListener(ApplicationReadyEvent.class)
    public void startUpOrRefresh() {
        shareTagConfig = this.serverConfigCacheService.get(ConfigEnum.ECDSA_PRIVATE_KEY, ShareTagConfig.class);

        log.info("Load share tag config:[{}]", this.shareTagConfig);
    }

}

@Data
class ShareTagConfig implements Configurable {
    private List<ShareTagKey> tagList;
}

@Data
class ShareTagKey {
    private String tag;
}
