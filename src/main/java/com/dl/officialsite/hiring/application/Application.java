package com.dl.officialsite.hiring.application;

import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Data
@EntityListeners(AuditingEntityListener.class)
@Entity
@DynamicUpdate
@Table(name = "application", schema = "dl")
public class Application {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long candidateId;

    private Long hiringId ;

    private Long hrId;

    // 0- 投递中 1 已录取 2 已拒绝
    private Integer status;


    @CreatedDate
    @Column(updatable = false)
    private Long createTime;
    @LastModifiedDate
    @Column( updatable = false ,nullable = false)
    private Long updateTime;

}
