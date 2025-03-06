package com.dl.officialsite.wish.params;

import lombok.Data;

/**
 * @ClassName ApplyWishParam
 * @Author jackchen
 * @Date 2025/2/26 22:52
 * @Description ApplyWishParam
 **/
@Data
public class ApplyWishParam {

    private Long wishId;

    private String applyUserName;

    private String applyAddress;
}
