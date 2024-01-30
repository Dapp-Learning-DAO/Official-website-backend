package com.dl.officialsite.team.teammember;

import javax.persistence.*;

import com.dl.officialsite.login.enums.UserRoleEnum;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.sql.Timestamp;

/**
 * @ClassName TeamMember
 * @Author jackchen
 * @Date 2023/10/21 17:17
 * @Description team member 中间表
 **/
@Data
@Entity
@EntityListeners(AuditingEntityListener.class)
@DynamicUpdate
@Table(name = "team_member")
public class TeamMember {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long teamId;

    private Long memberId;

    private UserRoleEnum role;
    /**
     * 0: 已加入
     * 1: 申请中
     * 2： 已退出 , 未加入
     * 3:  已拒绝
     */
    private int status;

    @CreatedDate
    @Column(updatable = false)
    private Long createTime;
    @LastModifiedDate
    @Column( updatable = false ,nullable = false)
    private Long updateTime;
}
