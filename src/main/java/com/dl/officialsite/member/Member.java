package com.dl.officialsite.member;


import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Getter
@Setter
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
//@DynamicUpdate
@Table(name = "member", schema = "dl")
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String  address;
    private String nickName;
    private int role;
    private String githubId;
    private String tweetID;
    private String wechatId;
    private int techStack;
    private  int programing;
    private int shareCount;
    private int rewardCount;





}
