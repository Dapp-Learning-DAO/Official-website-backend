package com.dl.officialsite.file.cos;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "qcloud")
public class COSProperties {
    private String secretId;
    private String secretKey;
    private String bucketName;
    private String regionName;


}
