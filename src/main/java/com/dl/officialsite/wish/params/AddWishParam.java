package com.dl.officialsite.wish.params;

import com.dl.officialsite.wish.domain.Wish;
import com.dl.officialsite.wish.config.TokenDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.time.LocalDateTime;
import lombok.Data;
import org.springframework.beans.BeanUtils;

/**
 * @ClassName AddWishParams
 * @Author jackchen
 * @Date 2024/12/25 16:50
 * @Description AddWishParam
 **/
@Data
public class AddWishParam {

    private String vaultId;

    @JsonDeserialize(using = TokenDeserializer.class)
    private String acceptTokens;

    private String title;

    private String description;

    private String tag;

    private String tokenSymbol;

    private String targetAmount;

    private String amount;

    private String createUser;

    private String createAddress;

    private LocalDateTime beginTime;

    private LocalDateTime endTime;

    private String chainId;

    private DonationWishParam donationWishParam;

    public Wish toWish() {
        Wish wish = new Wish();
        BeanUtils.copyProperties(this, wish);
        return wish;
    }

}
