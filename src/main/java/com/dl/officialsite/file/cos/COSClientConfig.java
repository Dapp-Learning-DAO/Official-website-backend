package com.dl.officialsite.file.cos;


import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.region.Region;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(COSProperties.class)
public class COSClientConfig {


    @Autowired
    private COSProperties cosProperties;

    @Bean
    public COSClient cosClient(){
        COSCredentials cred = new BasicCOSCredentials(cosProperties.getSecretId(),cosProperties.getSecretKey());
        Region region = new Region(cosProperties.getRegionName());
        ClientConfig clientConfig = new ClientConfig(region);
        // 3 生成 cos 客户端。
        COSClient cosClient = new COSClient(cred, clientConfig);
        return cosClient;
    }

}
