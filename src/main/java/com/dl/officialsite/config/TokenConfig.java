package com.dl.officialsite.config;

import lombok.Builder;
import lombok.Data;

/**
 * @ClassName TokenConfig
 * @Author jackchen
 * @Date 2024/3/10 11:43
 * @Description TokenConfig
 **/
@Data
@Builder
public class TokenConfig {

    private String name;

    private String address;
}
