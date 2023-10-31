package com.dl.officialsite.member;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;

@Getter
@Setter
@NoArgsConstructor
public class MemberVo {

    private String  address;

    private String email;

    private String nickName;
    private int role; // 开发者0 ， 投资 1  产品2   运营3  市场4  UI/UX 5
    private String githubId;
    private String tweetId;
    private String telegramId;
    private String wechatId;
    private String avatar;
    private String techStack;  //前端 ， 后端 ， 全栈， 运维， 测试, 密码学， 区块链底层，金融，数学
    private String programing; //
    private Long createTime;
    // optional
    private String interests; //兴趣
    private String city;
    private int shareCount;
    private String resume;
}
