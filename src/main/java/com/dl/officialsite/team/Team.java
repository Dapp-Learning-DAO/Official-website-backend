package com.dl.officialsite.team;

import com.dl.officialsite.member.Member;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.List;

public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String teamName;
    private String teamProfile;
    private List<Member> memberList;
    private String administrator;
    private String nickName;
}
