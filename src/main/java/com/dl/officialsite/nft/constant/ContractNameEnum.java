package com.dl.officialsite.nft.constant;

import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

@Getter
public enum ContractNameEnum {
    WAR_CRAFT("WarCraft");

    private String contractName;

    ContractNameEnum(String contractName) {
        this.contractName = contractName;
    }

    public static ContractNameEnum fromValue(String contractName) {
        for (ContractNameEnum value : ContractNameEnum.values()) {
            if (StringUtils.equalsIgnoreCase(value.getContractName(), contractName)) {
                return value;
            }
        }
        return WAR_CRAFT;
    }
}