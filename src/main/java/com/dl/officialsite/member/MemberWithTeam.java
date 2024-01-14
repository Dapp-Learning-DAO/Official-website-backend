package com.dl.officialsite.member;


import com.dl.officialsite.team.Team;
import java.util.ArrayList;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class MemberWithTeam extends Member
{

    private ArrayList<Team> teams;

    private boolean isAdmin;


}
