package com.dl.officialsite.wish.result;

import com.dl.officialsite.wish.enums.DonateStatusEnum;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

/**
 * @ClassName WishApplyResult
 * @Author jackchen
 * @Date 2025/3/5 21:31
 * @Description WishApplyResult
 **/
@Data
public class WishApplyResult {

    private Long id;

    private Long wishId;

    private Long memberId;

    private String chainId;

    private String memberName;

    private String memberAddress;

    private String githubId;

    private String tweetId;

    private String amount;

    private String tokenSymbol;

    private String token;

    private String status = DonateStatusEnum.PENDING.getDesc();

    @CreationTimestamp
    @Column(updatable = false, nullable = false)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    private LocalDateTime createTime;

}
