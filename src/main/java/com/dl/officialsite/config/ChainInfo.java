package com.dl.officialsite.config;

import java.util.List;
import lombok.Data;

/**
 * @ClassName ChainInfo
 * @Author jackchen
 * @Date 2024/3/10 11:35
 * @Description ChainInfo
 **/
@Data
public class ChainInfo {

    private String name;

    private String id;

    private String lendingPoolAddressProviderV3Address;

    private List<TokenConfig> tokens;
}
