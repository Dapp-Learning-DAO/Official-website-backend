package com.dl.officialsite.defi.vo.params;

import java.math.BigDecimal;
import lombok.Data;

/**
 * @ClassName QueryWhaleParams
 * @Author jackchen
 * @Date 2024/4/5 18:18
 * @Description QueryWhaleParams
 **/
@Data
public class QueryWhaleParams {

    private String whaleAddress;

    private BigDecimal whaleUSD;

    /**
     * 1: desc, 2: asc
     */
    private Integer order;
}
