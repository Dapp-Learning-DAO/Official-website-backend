package com.dl.officialsite.login.model;

import com.dl.officialsite.team.teammember.TeamMember;
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

    private String twitterOauthTokenSecret;
}
