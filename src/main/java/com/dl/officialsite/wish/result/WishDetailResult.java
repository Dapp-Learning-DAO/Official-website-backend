package com.dl.officialsite.wish.result;

import com.dl.officialsite.member.Member;
import com.dl.officialsite.wish.domain.WishApply;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
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

    private String vaultId;

    private String chainId;

    private String acceptTokens;

    private String title;

    private String description;

    private String tag;

    private String tokenSymbol;

    private String targetAmount;

    private String amount;

    private Integer likeNumber;

    private String shareTitle;

    private Long shareId;

    private String shareUser;

    private String shareAddress;

    private String shareGithubId;

    private String shareTweetId;

    private String createUser;

    private String createAddress;

    private String settleUser;

    private String settleAddress;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime beginTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime endTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;

    private Integer status;

    private List<WishApplyResult> wishApplyList;

    private Member creator;

    private Integer liked = 0;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    private LocalDateTime settleTime;

}
