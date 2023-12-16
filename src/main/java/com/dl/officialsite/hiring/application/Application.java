package com.dl.officialsite.hiring.application;

import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;


@EntityListeners(AuditingEntityListener.class)
@Entity
@DynamicUpdate
@Table(name = "application", schema = "dl")

public class Application {


    private Long memberId;

    private Long hiringId ;


    // 0- 投递中 1 已录取 2 已拒绝
    private Integer status;


    @CreatedDate
    @Column(updatable = false)
    private Long createTime;
    @LastModifiedDate
    @Column( updatable = false ,nullable = false)
    private Long updateTime;

}
