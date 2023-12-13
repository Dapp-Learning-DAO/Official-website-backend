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
public class MemberWithTeam extends Member
{

    private ArrayList<TeamVO> teams;

    private boolean isAdmin;


}
