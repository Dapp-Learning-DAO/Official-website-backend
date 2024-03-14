package com.dl.officialsite.aave.vo;

import java.math.BigInteger;
import lombok.Builder;
import lombok.Data;

/**
 * @ClassName HealthInfo
 * @Author jackchen
 * @Date 2023/11/2 20:29
 * @Description HealthInfo
 **/
@Data
@Builder
public class HealthInfoVo {

    //健康系数
    private BigInteger healthFactor;

    //总借款
    private String totalBorrows;

    //总存款
    private String totalCollateralETH;

    //LTV数据获取
    private String totalLiquidity;
}
