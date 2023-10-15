package com.dl.officialsite.member;


import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.mapping.PrimaryKey;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@DynamicUpdate
@Table(name = "member", schema = "dl", uniqueConstraints = {
        @UniqueConstraint(name = "address", columnNames = {"address"}),
        @UniqueConstraint(name = "role", columnNames = {"role"})})
public class Member  implements Serializable
{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String  address;
    private String nickName;
    private int role; // 开发者0 ， 投资 1  产品2   运营3  市场4  UI/UX 5
    private String githubId;
    private String tweetID;
    private String wechatId;
    private int techStack;  //前端 ， 后端 ， 全栈， 运维， 测试

    private String programing; //
    // optional
    private String interests; //兴趣
    private String city;
    private int shareCount;
    private int rewardCount;





}
