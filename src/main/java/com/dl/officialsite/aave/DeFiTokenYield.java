package com.dl.officialsite.aave;

import lombok.Data;

/**
 * @ClassName AaveDefi
 * @Author jackchen
 * @Date 2024/3/6 19:51
 * @Description AaveDefi
 **/
@Data
public class DeFiTokenYield {

    private String tokeName;

    private String protocol;

    private String tokenAddress;

    private String tokenApy;

    private String tokenCurrent;

    private String linkName;

    private Long createTime;

}
