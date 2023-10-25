package com.dl.officialsite.login.aspect;

import com.dl.officialsite.common.base.BaseResponse;
import com.dl.officialsite.common.utils.UserSecurityUtils;
import com.dl.officialsite.login.enums.UserRoleEnum;
import com.dl.officialsite.login.model.UserPrincipleData;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Aspect
@Component
@Slf4j
public class AuthorizationAspect {
    @Pointcut("@annotation(com.dl.officialsite.login.aspect.RequiresAtLeast)")
    public void controller() {
    }

    @Around("controller()") //在切入点的方法之前
    public Object interceptAuthorization(ProceedingJoinPoint joinPoint) throws Throwable {
        UserPrincipleData userPrincipleData = UserSecurityUtils.getUserLogin();
        if(userPrincipleData == null){
            log.info("intercept unauthenticated login");
            return BaseResponse.failWithReason("2001", "please login in");
        }

        UserRoleEnum requiredRole = getRequiredUserRole(joinPoint);
        if(userPrincipleData.getUserRole().getPower() < requiredRole.getPower()){
            log.info("intercept unauthorized operation");
            return BaseResponse.failWithReason("2003", "no authorization");
        }

        return joinPoint.proceed();
    }

    private UserRoleEnum getRequiredUserRole(JoinPoint joinPoint){
        MethodSignature signature=(MethodSignature)joinPoint.getSignature();
        Method method=signature.getMethod();
        RequiresAtLeast aspect=method.getAnnotation(RequiresAtLeast.class);
        return aspect.value();
    }

}
