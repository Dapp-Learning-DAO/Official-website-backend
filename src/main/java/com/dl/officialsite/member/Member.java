package com.dl.officialsite.member;


import com.dl.officialsite.common.privacy.PrivacyEncrypt;
import com.dl.officialsite.common.privacy.PrivacyTypeEnum;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

import static com.dl.officialsite.common.privacy.PrivacyTypeEnum.CUSTOMER;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
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
    @NotNull
    private String  address;
    @Column(unique=true,length = 40)
    @NotNull
    @PrivacyEncrypt(type = PrivacyTypeEnum.EMAIL)
    private String email;
    @Column(unique=true,length = 20)
    @NotNull
    private String nickName;
    private int role; // 0开发者，  1产品   2运营  3 测试  4 运维  5 设计师  6项目经理  7 研究员 8 投资人 9 市场 10 数据分析师  11 其他
    @Column(unique=true,length = 38)
    private String githubId;
    @Column(length = 20)
    private String tweetId;
    @Column(length = 20)
    private String telegramId;
    @Column(length = 20)
    private String wechatId;
    private String avatar;

    private String techStack;  //前端 ，后端 ， 全栈， 运维，DBA,  UI/UX, AI， 密码学，智能合约，数据分析,  共识算法，金融，数学， 网络安全， 英语,  其他
    private String programing;
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

    //@PrivacyEncrypt(type= CUSTOMER)
    @JsonIgnore
    private String resume;

   //todo 



}
