package com.dl.officialsite.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Data
public class ChainConfig {

   @Value("#{'${CHAIN_IDS:11155111,10}'.split(',')}")
   private  String[] ids;
}
