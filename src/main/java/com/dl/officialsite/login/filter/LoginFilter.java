package com.dl.officialsite.login.filter;


import com.dl.officialsite.common.base.BaseResponse;
import com.dl.officialsite.common.utils.HttpSessionUtils;
import com.dl.officialsite.common.utils.UserSecurityUtils;
import com.dl.officialsite.login.enums.UserRoleEnum;
import com.dl.officialsite.login.model.SessionUserInfo;
import com.dl.officialsite.login.model.UserPrincipleData;
import com.dl.officialsite.team.TeamRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
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
@Order(Integer.MIN_VALUE)
public class LoginFilter extends OncePerRequestFilter {

    @Autowired
    private TeamRepository teamRepository;


    private Set<String> noLoginApis = new HashSet(){{
        add("/login/nonce");
        add("/login/check");
        add("/login/check-session");
    }} ;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        try{
            if (noLoginApis.contains(request.getRequestURI())) {
                UserPrincipleData userPrincipleData = new UserPrincipleData();
                userPrincipleData.setUserRole(UserRoleEnum.ANONYMOUS);
                filterChain.doFilter(request, response);
                return;
            }


            //Must login
            if(!HttpSessionUtils.isUserLogin(request.getSession())){
                dumpError(response);
                return;
            }

            //Since logon, put a temporary user data
            SessionUserInfo sessionUserInfo = HttpSessionUtils.getMember(request.getSession());
            UserPrincipleData userPrinciple = new UserPrincipleData();
            userPrinciple.setAddress(sessionUserInfo.getAddress());
            userPrinciple.setUserRole(UserRoleEnum.NORMAL);//TODO: load from team
            UserSecurityUtils.setPrincipleLogin(userPrinciple);

            //Execute next(auth filter)
            filterChain.doFilter(request, response);
        }
        finally {
            UserSecurityUtils.clearPipelineCache();
        }
    }

    private void dumpError(HttpServletResponse response) throws IOException{
        response.setContentType("application/json;charset=utf-8");
        PrintWriter out = response.getWriter();
        ObjectMapper objectMapper =  new  ObjectMapper();
        out.write( objectMapper.writeValueAsString(   BaseResponse.failWithReason("2001", "please login in")));
        out.flush();
        out.close();
    }

    private UserRoleEnum deduceRole(){
        this.teamRepository.findBy();
    }
}
