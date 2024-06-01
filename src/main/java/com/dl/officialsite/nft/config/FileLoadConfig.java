package com.dl.officialsite.nft.config;

import com.dl.officialsite.common.utils.GsonUtil;
import com.dl.officialsite.config.bean.Configurable;
import com.dl.officialsite.config.bean.Refreshable;
import com.dl.officialsite.config.constant.ConfigEnum;
import com.dl.officialsite.config.service.ServerConfigCacheService;
import com.dl.officialsite.nft.bean.NFTMetadata;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class FileLoadConfig implements Refreshable {
    private FilePathConfig filePathConfig;

    private Map<String, List<NFTMetadata>> nftMetadataListMap;

    @Autowired
    private ServerConfigCacheService serverConfigCacheService;

    @EventListener(ApplicationReadyEvent.class)
    public void startUpOrRefresh() {
        filePathConfig = this.serverConfigCacheService.get(ConfigEnum.FILE_PATH_CONFIG, FilePathConfig.class);

        // Load JSON files
        nftMetadataListMap = Optional.ofNullable(filePathConfig)
            .map(FilePathConfig::getJsonFiles)
            .map(jsonFileList -> jsonFileList.stream()
                .collect(Collectors.toMap(
                    JsonFile::getType,
                    jsonFile -> GsonUtil.readListFromJsonFile(jsonFile.getFilePath(), NFTMetadata.class),
                    (existingValue, newValue) -> newValue)
                )
            ).orElse(Collections.emptyMap());

        log.info("Load NFT meta data [{}]", nftMetadataListMap.size());
    }

    public NFTMetadata get(String nftType, int rank) {
        List<NFTMetadata> nftMetadataList = Optional.ofNullable(nftMetadataListMap.get(nftType)).orElse(Collections.emptyList());
        if (rank >= 0 && rank < nftMetadataList.size()) {
            return nftMetadataList.get(rank);
        }
        return null;
    }
}

@Data
class FilePathConfig implements Configurable {
    private List<JsonFile> jsonFiles;
}

@Data
class JsonFile {
    private String filePath;
    private String type;
}
