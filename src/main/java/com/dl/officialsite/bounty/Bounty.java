package com.dl.officialsite.bounty;


import com.dl.officialsite.member.Member;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@Entity
@Table(name = "bounty")
@EntityListeners(AuditingEntityListener.class)
public class Bounty {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    /**
     * 创建岗位人地址
     */
    @Embedded
    private Member member;

    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    private String salary;
   // 线性释放， 指数释放
    private int paymentType;

    private String projectLength;

    private String techTag;

    /**
     * 0:jd 招聘中
     * 1: 已匹配
     * 2:jd 已完成结算
     * 3:jd 已过期
     * 4：jd 已退款
     * 5: 已删除
     */
    private int status;

    private String company;

    private String tags;

    private String benefits;


    @CreationTimestamp
    @Column(updatable = false, nullable = false)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    @LastModifiedDate
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;




}
