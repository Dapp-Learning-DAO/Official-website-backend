package com.dl.officialsite.aave;

import lombok.Data;

/**
 * @ClassName TokenAPYInfo
 * @Author jackchen
 * @Date 2024/3/6 20:37
 * @Description TokenAPYInfo
 **/
@Data
public class TokenAPYInfo {

    String tokenName;

    String tokenAddress;

    String chainName;

    String tokenApy;
}
