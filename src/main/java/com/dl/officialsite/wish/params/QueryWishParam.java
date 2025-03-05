package com.dl.officialsite.wish.params;

import lombok.Data;

/**
 * @ClassName QueryWishParam
 * @Author jackchen
 * @Date 2025/1/2 22:59
 * @Description QueryWishParam
 **/
@Data
public class QueryWishParam {

    private String title;

    private String description;

    private String tag;

    private String amount;

    private String chainId;

}
