package com.dl.officialsite.member;


import lombok.*;

import java.util.ArrayList;

@Data
public class MemberWithTeam extends Member
{

    private ArrayList<TeamVO> teams;

    private boolean isAdmin;


}
