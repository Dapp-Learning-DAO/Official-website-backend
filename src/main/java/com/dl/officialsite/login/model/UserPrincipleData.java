package com.dl.officialsite.login.model;

import com.dl.officialsite.login.enums.UserRoleEnum;
import com.dl.officialsite.team.TeamMember;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserPrincipleData {

    private String address;

    private List<TeamMember> teams;
}
