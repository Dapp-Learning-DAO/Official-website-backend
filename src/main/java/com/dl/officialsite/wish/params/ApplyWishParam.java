package com.dl.officialsite.wish.params;

import lombok.Data;

/**
 * @ClassName ApplyWishParam
 * @Author jackchen
 * @Date 2025/1/12 13:10
 * @Description ApplyWishParam
 **/
@Data
public class ApplyWishParam {

    private Long wishId;

    private String amount;

    private String tokenSymbol;


}
