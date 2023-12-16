package com.dl.officialsite.member;


import com.dl.officialsite.team.Team;
import com.dl.officialsite.team.vo.TeamsWithMembers;
import lombok.*;

import java.util.ArrayList;

@Data
public class MemberWithTeam extends Member
{

    private ArrayList<TeamsWithMembers> teams;

    private boolean isAdmin;


}
