package com.dl.officialsite.wish.params;

import com.dl.officialsite.wish.config.TokenDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
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

    @JsonDeserialize(using = TokenDeserializer.class)
    private String token;


}
