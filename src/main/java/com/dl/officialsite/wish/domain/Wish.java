package com.dl.officialsite.wish.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.LastModifiedDate;

/**
 * @ClassName Wish
 * @Author jackchen
 * @Date 2024/12/25 16:38
 * @Description wish
 **/
@Data
@Entity
@Table(name = "wish")
public class Wish {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String vaultId;

    private String acceptTokens;

    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    private String tag;

    private String tokenSymbol;

    private String targetAmount;

    private String amount;

    private Integer likeNumber;

    private Integer status = 0;

    private Integer createStatus = 0;

    private Integer apply = 0;

    private String createUser;

    private String createAddress;

    private String settleUser;

    private String settleAddress;

    private LocalDateTime beginTime;

    private LocalDateTime endTime;

    private String chainId;

    @CreationTimestamp
    @Column(updatable = false, nullable = false)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    @LastModifiedDate
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;

    private Long shareId;


}
