package com.dl.officialsite.login;

import com.dl.officialsite.common.exception.UnauthorizedException;
import com.dl.officialsite.common.utils.UserSecurityUtils;
import com.dl.officialsite.login.model.UserPrincipleData;
import com.dl.officialsite.member.MemberController;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@Aspect
public class AuthAspect {
//    @Autowired
//    private UserSecurityUtils authService;

    @Pointcut("@annotation(com.dl.officialsite.login.Auth)")
    public void authPointcut() {}

    public static final Logger logger = LoggerFactory.getLogger(MemberController.class);

    @Before("authPointcut() && @annotation(auth)")
    public void authBefore(JoinPoint joinPoint, Auth auth) throws UnauthorizedException {
        String permission = auth.value();
        UserPrincipleData userPrincipleData =  UserSecurityUtils.getUserLogin();
        logger.info("userPrincipleData address "+ userPrincipleData.getAddress());
        logger.info("userPrincipleData team "+ userPrincipleData.getTeams());
       if(permission.equals("admin")) {

           List team =  userPrincipleData.getTeams().stream().filter(x-> x.getTeamId()==1).collect(Collectors.toList());
            if(team == null || team.size()==0){
                throw new UnauthorizedException("Unauthorized access");
            }


       }
    }
}
