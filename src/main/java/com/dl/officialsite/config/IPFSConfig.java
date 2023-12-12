package com.dl.officialsite.config;


import com.dl.officialsite.member.MemberController;
import io.ipfs.api.IPFS;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

//@Component
@Data
@ConditionalOnProperty(value = "ipfs.url")
@ConfigurationProperties(prefix = "ipfs")
public class IPFSConfig {

    public String url;

    public static final Logger logger = LoggerFactory.getLogger(MemberController.class);


    @Bean
    public IPFS getIPFS() {

         IPFS ipfs = new IPFS(url);
        logger.info("******ipfs url******"+ipfs.host );
         return ipfs;
    }

}
