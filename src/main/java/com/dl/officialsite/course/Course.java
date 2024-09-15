package com.dl.officialsite.course;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "course")
public class Course {
    /**
     * create table course
     * (
     *     id                bigint auto_increment
     *         primary key,
     *     course_name     varchar(255)  null comment '课程名称',
     *     remark             varchar(1024)  null comment '课程简介',
     *     cooperate_community        varchar(100)  null comment '合作社区',
     *     creator    varchar(64)   null comment '创建者',
     *     updater   varchar(64)   null comment '更新者',
     *     create_time       bigint        null comment '创建时间',
     *     update_time       bigint        null comment '更新时间',
     *     status            int           null comment '状态,0:进行中,1:已结束'
     * )
     *     comment '课程表';
     */

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    //课程名称
    private String courseName;
    //课程简介
    private String remark;
    //合作社区
    private String cooperateCommunity;
    //创建者
    private String creator;
    //更新者
    private String updater;
    //创建时间
    @CreatedDate
    @Column(updatable = false)
    private Long createTime;
    //更新时间
    @LastModifiedDate
    @Column( updatable = false ,nullable = false)
    private Long updateTime;
    //状态,0:进行中,1:已结束
    private Integer status;
}
