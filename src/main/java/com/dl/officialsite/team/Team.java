package com.dl.officialsite.team;

import com.dl.officialsite.member.Member;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.List;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import lombok.Data;

@Data
@Entity
@Table(name = "team")
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String teamName;
    private String teamProfile;
    private String administrator;

    // dao admin , core contributor,  builder
    private int authority;
}
