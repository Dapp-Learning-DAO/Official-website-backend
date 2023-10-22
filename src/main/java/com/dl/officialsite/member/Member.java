package com.dl.officialsite.member;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.mapping.PrimaryKey;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@EntityListeners(AuditingEntityListener.class)
@Entity
@DynamicUpdate
@Table(name = "member", schema = "dl", uniqueConstraints = {
        @UniqueConstraint(name = "address", columnNames = {"address"}),
        @UniqueConstraint(name = "nickName", columnNames = {"nickName"})})
public class Member  implements Serializable
{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 42)
    private String  address;
    @Column(unique=true,length = 40)
    private String email;
    @Column(unique=true,length = 20)
    private String nickName;
    private int role; // 开发者0 ， 投资 1  产品2   运营3  市场4  UI/UX 5
    @Column(unique=true,length = 20)
    private String githubId;
    @Column(length = 20)
    private String tweetId;
    @Column(length = 20)
    private String teletgramId;
    @Column(length = 20)
    private String wechatId;
    private String avatar;

    private String techStack;  //前端 ， 后端 ， 全栈， 运维， 测试, 密码学， 区块链底层，金融，数学
    private String programing; //
    // optional
    private String interests; //兴趣
    private String city;
    private int shareCount;
    private int rewardCount;
    @CreatedDate
    @Column(updatable = false)
    private Long createTime;
    @LastModifiedDate
    @Column( updatable = false ,nullable = false)
    private Long updateTime;

    //todo
    private Long workStatus;
    @JsonIgnore
    private String resume;


}
