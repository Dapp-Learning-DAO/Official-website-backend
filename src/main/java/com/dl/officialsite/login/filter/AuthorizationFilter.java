package com.dl.officialsite.login.filter;


import com.dl.officialsite.common.base.BaseResponse;
import com.dl.officialsite.common.utils.UserSecurityUtils;
import com.dl.officialsite.login.enums.UserRoleEnum;
import com.dl.officialsite.login.model.SessionUserInfo;
import com.dl.officialsite.login.model.UserPrincipleData;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Preconditions;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.Set;

@Component
@Order(Integer.MIN_VALUE + 1)
@Slf4j
public class AuthorizationFilter extends OncePerRequestFilter {

    private Set<String> anonymousApis = new HashSet(){{
        add("/login/nonce");
        add("/login/check");
        add("/login/check-session");
    }} ;
    private Set<String> normalApis = new HashSet(){{
        add("/file/upload");
    }} ;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        UserPrincipleData userPrinciple = UserSecurityUtils.getUserLogin();
        Preconditions.checkArgument(userPrinciple != null, "user not login in auth filter");

        UserRoleEnum role = userPrinciple.getUserRole();

        //Check authorities to apis
        boolean shouldPass = deduceShouldPass(role, request);

        //Pass or not
        if (!shouldPass){
            response.setContentType("application/json;charset=utf-8");
            PrintWriter out = response.getWriter();
            ObjectMapper objectMapper =  new  ObjectMapper();
            out.write( objectMapper.writeValueAsString(BaseResponse.failWithReason("2002", "forbidden")));
            out.flush();
            out.close();
            return;
        }

        filterChain.doFilter(request, response);
    }

    private boolean deduceShouldPass(UserRoleEnum role, HttpServletRequest request){
        log.info("***req url:{}, role:{}", request.getRequestURI(), role);
        if (role == UserRoleEnum.ANONYMOUS){
            return anonymousApis.contains(request.getRequestURI());
        }

        if (role == UserRoleEnum.ADMIN){
            return true;
        }

        if (role == UserRoleEnum.BLOCKED){
            return false;
        }

        if (role == UserRoleEnum.NORMAL){
            return normalApis.contains(request.getRequestURI());
        }

        return false;

    }

}
