package com.dl.officialsite.common.utils;

import com.dl.officialsite.login.enums.UserRoleEnum;
import com.dl.officialsite.login.model.UserPrincipleData;
import com.dl.officialsite.team.TeamMember;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;

@Slf4j
public class AuthorizationUtils {

    public static void requireTeamAuth(long teamId, UserRoleEnum requiredRole){
        UserPrincipleData userPrincipleData = UserSecurityUtils.getUserLogin();
        if(userPrincipleData == null){
            log.error("not login at all");
            throw new RuntimeException("not authorized");//统一改错误玛
        }

        List<TeamMember> teams = userPrincipleData.getTeams();
        if (CollectionUtils.isEmpty(teams)){
            log.error("no teams info");
            throw new RuntimeException("not authorized");//统一改错误玛
        }

        for (TeamMember teamMember: teams){
            if (teamMember.getTeamId().longValue() == teamId  && teamMember.getRole().getPower() >= requiredRole.getPower()){
                return;
            }
        }
        log.error("no team matched");
        throw new RuntimeException("not authorized");//统一改错误玛

    }
}
