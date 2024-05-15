package com.dl.officialsite.nft.config;

import com.dl.officialsite.common.utils.SignatureGeneration;
import com.dl.officialsite.config.bean.Configurable;
import com.dl.officialsite.config.bean.Refreshable;
import com.dl.officialsite.config.constant.ConfigEnum;
import com.dl.officialsite.config.service.ServerConfigCacheService;
import com.dl.officialsite.nft.constant.ContractNameEnum;
import com.dl.officialsite.nft.constant.EcdsaKeyTypeEnum;
import com.dl.officialsite.nft.dto.SignatureDto;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.web3j.crypto.Credentials;
import org.web3j.utils.Numeric;

import java.math.BigInteger;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class EcdsaKeyConfigService implements Refreshable {
    private EcdsaPrivateKeyConfig ecdsaPrivateKeyConfig;

    @Autowired
    private ServerConfigCacheService serverConfigCacheService;
    @Autowired
    private ContractConfigService contractConfigService;

    @EventListener(ApplicationReadyEvent.class)
    public void startUpOrRefresh() {
        ecdsaPrivateKeyConfig = this.serverConfigCacheService.get(ConfigEnum.ECDSA_PRIVATE_KEY, EcdsaPrivateKeyConfig.class);

        log.info("Load ECDSA private key config:[{}]", this.ecdsaPrivateKeyConfig);
    }

    public SignatureDto sign(EcdsaKeyTypeEnum keyType, ContractNameEnum contractNameEnum,
                             String receiverAddress, String chainId) {
        String contractAddress = this.contractConfigService.getContractAddressByName(contractNameEnum, chainId);
        if (StringUtils.isBlank(contractAddress)) {
            log.error("No contract [{}] was configured.", contractNameEnum.getContractName());
            return null;
        }

        BigInteger seed = SignatureGeneration.randomSeed();
        long signedAt = Instant.now().getEpochSecond();

        return Optional.ofNullable(ecdsaPrivateKeyConfig)
            .map(EcdsaPrivateKeyConfig::getEcdsaKeyList)
            .flatMap(list -> list.stream()
                .filter(item -> StringUtils.equalsIgnoreCase(item.getKeyType(), keyType.getKeyType()))
                .map(EcdsaPrivateKey::getPrivateKey)
                .map(privateKey ->
                    new SignatureDto(receiverAddress,
                        signedAt,
                        SignatureGeneration.sign(Credentials.create(privateKey), receiverAddress, seed, BigInteger.valueOf(signedAt),
                            BigInteger.valueOf(Long.parseLong(chainId)), contractAddress),
                        Numeric.toHexStringWithPrefix(seed),
                        chainId,
                        contractAddress)
                ).findFirst()
            ).orElse(null);
    }
}

@Data
class EcdsaPrivateKeyConfig implements Configurable {
    private List<EcdsaPrivateKey> ecdsaKeyList;
}

@Data
class EcdsaPrivateKey {
    private String keyType;
    private String privateKey;
    private String address;
}
