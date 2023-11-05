package com.dl.officialsite.aave;

import lombok.Builder;
import lombok.Data;

/**
 * @ClassName TokenInfo
 * @Author jackchen
 * @Date 2023/11/5 12:32
 * @Description TokenInfo
 **/
@Data
@Builder
public class TokenInfo {

    private String deposit;

    private String borrow;

    private String price;
}
