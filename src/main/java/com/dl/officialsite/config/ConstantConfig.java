package com.dl.officialsite.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.web3j.abi.datatypes.Bool;

@Component
@ConfigurationProperties
@Data
public class ConstantConfig {
   @Value("${login.filter}")
   private Boolean loginFilter;
}
