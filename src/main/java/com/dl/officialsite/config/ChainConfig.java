package com.dl.officialsite.config;

import lombok.Data;

import java.util.Arrays;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "chain")
@Data
public class ChainConfig {

   private String[] ids;

   public boolean isContainId(String targetId) {
      return Arrays.stream(ids).anyMatch(s -> s.equals(targetId));
   }
}
