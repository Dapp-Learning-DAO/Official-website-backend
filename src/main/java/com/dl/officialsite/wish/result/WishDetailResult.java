package com.dl.officialsite.wish.result;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
import java.util.Date;
import lombok.Data;

/**
 * @ClassName WishDeatilResult
 * @Author jackchen
 * @Date 2024/12/25 16:53
 * @Description WishDetailResult
 **/
@Data
public class WishDetailResult {

    private Long id;

    private String title;

    private String description;

    private String tag;

    private String amount;

    private String applyUser;

    private String applyAddress;

    private String shareUrl;

    private String shareUser;

    private String shareAddress;

    private String createUser;

    private String createAddress;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;

}
