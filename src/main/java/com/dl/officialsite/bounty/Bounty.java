package com.dl.officialsite.bounty;


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
    private String creator;

    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    private String Salary;
   // 线性释放， 指数释放
    private int paymentType;

    private Long projectLength;

    /**
     * 0:jd 招聘中
     * 1: 已匹配
     * 1:jd 删除
     * 2:jd 过期
     * 设置默认值,默认值为招聘中
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
