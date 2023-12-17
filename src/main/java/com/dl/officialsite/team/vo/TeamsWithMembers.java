package com.dl.officialsite.team.vo;

import com.dl.officialsite.member.Member;
import java.util.List;

import com.dl.officialsite.team.Team;
import lombok.Data;

/**
 * @ClassName TeamsMembersVo
 * @Author jackchen
 * @Date 2023/10/21 17:34
 * @Description TeamsMembersVo
 **/
@Data
public class TeamsWithMembers extends Team {

    //only for specific member with team info
    private int status;

    private List<Member> members;

}
