package com.dl.officialsite.wish.params;

import lombok.Data;

/**
 * @ClassName EditWishParam
 * @Author jackchen
 * @Date 2025/1/2 22:48
 * @Description 编辑许愿清单
 **/
@Data
public class EditWishParam {

    private Long id;

    private String title;

    private String description;

    private String tag;

    private String amount;

}
