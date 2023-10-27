package com.dl.officialsite.team;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.dl.officialsite.login.enums.UserRoleEnum;
import lombok.Data;

/**
 * @ClassName TeamMember
 * @Author jackchen
 * @Date 2023/10/21 17:17
 * @Description team member 中间表
 **/
@Data
@Entity
@Table(name = "team_member")
public class TeamMember {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long teamId;

    private Long memberId;

    private UserRoleEnum role;
    /**
     * 0: 同意加入
     * 1: 申请中
     */
    private int status;
}
