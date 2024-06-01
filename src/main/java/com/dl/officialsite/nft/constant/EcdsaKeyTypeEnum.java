package com.dl.officialsite.nft.constant;

import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

@Getter
public enum EcdsaKeyTypeEnum {
    NFT("NFT");

    private String keyType;

    EcdsaKeyTypeEnum(String keyType) {
        this.keyType = keyType;
    }

    public static EcdsaKeyTypeEnum fromValue(String keyType) {
        for (EcdsaKeyTypeEnum value : EcdsaKeyTypeEnum.values()) {
            if (StringUtils.equalsIgnoreCase(value.getKeyType(), keyType)) {
                return value;
            }
        }
        return NFT;
    }
}