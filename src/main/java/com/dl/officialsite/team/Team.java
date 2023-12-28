package com.dl.officialsite.team;

import javax.persistence.*;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Data
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "team")
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    //team名称
    private String teamName;
    //团队简介
    private String teamProfile;
    //管理员
    private String administrator;

    // dao admin , core contributor,  builder
    private int authority;

    @CreatedDate
    @Column(updatable = false)
    private Long createTime;
    @LastModifiedDate
    @Column( updatable = false ,nullable = false)
    private Long updateTime;
}
