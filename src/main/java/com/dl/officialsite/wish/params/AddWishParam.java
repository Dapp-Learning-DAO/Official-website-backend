package com.dl.officialsite.wish.params;

import com.dl.officialsite.wish.Wish;
import javax.persistence.Column;
import lombok.Data;

/**
 * @ClassName AddWishParams
 * @Author jackchen
 * @Date 2024/12/25 16:50
 * @Description AddWishParam
 **/
@Data
public class AddWishParam {

    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    private String tag;

    private String amount;

    private String createUser;

    private String createAddress;

    public Wish buildWish() {
        Wish wish = new Wish();
        wish.setTitle(title);
        wish.setDescription(description);
        wish.setTag(tag);
        wish.setAmount(amount);
        wish.setCreateUser(createUser);
        wish.setCreateAddress(createAddress);
        return wish;
    }

}
