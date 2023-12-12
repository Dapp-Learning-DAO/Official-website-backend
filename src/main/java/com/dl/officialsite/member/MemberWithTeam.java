package com.dl.officialsite.member;


import com.dl.officialsite.team.vo.TeamVO;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;

@Data
public class MemberWithTeam
{

    private Long id;
    private String  address;
    private String email;
    private String nickName;
    private int role; // 开发者0 ， 投资 1  产品2   运营3  市场4  UI/UX 5
    private String githubId;
    private String tweetId;
    private String telegramId;
    private String wechatId;
    private String avatar;

    private String techStack;
    private String programing;
    // optional
    private String interests;
    private String city;
    private int shareCount;
    private int rewardCount;
    private Long workStatus;

    private ArrayList<TeamVO> teams;

    private boolean isAdmin;


}
