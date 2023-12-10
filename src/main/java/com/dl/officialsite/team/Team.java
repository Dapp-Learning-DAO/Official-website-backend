package com.dl.officialsite.team;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;

@Data
@Entity
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
}
