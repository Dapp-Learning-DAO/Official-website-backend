package com.dl.officialsite.nft.config;

import com.dl.officialsite.config.bean.Configurable;
import com.dl.officialsite.config.bean.Refreshable;
import com.dl.officialsite.config.constant.ConfigEnum;
import com.dl.officialsite.config.service.ServerConfigCacheService;
import com.dl.officialsite.nft.constant.ContractNameEnum;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class ContractConfigService implements Refreshable {
    private ContractAddressConfig contractAddressConfig;

    @Autowired
    private ServerConfigCacheService serverConfigCacheService;

    @EventListener(ApplicationReadyEvent.class)
    public void startUpOrRefresh() {
        contractAddressConfig = this.serverConfigCacheService.get(ConfigEnum.CONTRACT_ADDRESS, ContractAddressConfig.class);

        log.info("Load contract config [{}]", this.contractAddressConfig);
    }

    public String getContractAddressByName(ContractNameEnum contractName, String chainId) {
        return Optional.ofNullable(contractAddressConfig)
            .map(ContractAddressConfig::getContractAddressItemList)
            .flatMap(list -> list.stream()
                .filter(item -> StringUtils.equalsIgnoreCase(item.getContractName(), contractName.getContractName()))
                .filter(item -> StringUtils.equalsIgnoreCase(item.getChainId(), chainId))
                .map(ContractAddressItem::getAddress)
                .findFirst()
            ).orElse("");
    }
}

@Data
class ContractAddressConfig implements Configurable {
    private List<ContractAddressItem> contractAddressItemList;
}

@Data
class ContractAddressItem {
    private String contractName;
    private String chainId;
    private String chainName;
    private String address;
}
