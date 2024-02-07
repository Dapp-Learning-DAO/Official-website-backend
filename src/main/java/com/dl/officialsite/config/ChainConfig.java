package com.dl.officialsite.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix="chain")
@Data
public class ChainConfig {

   private  String[] ids;
}
