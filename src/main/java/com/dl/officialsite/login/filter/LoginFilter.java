package com.dl.officialsite.login.filter;


import com.dl.officialsite.common.base.BaseResponse;
import com.dl.officialsite.common.utils.HttpSessionUtils;
import com.dl.officialsite.common.utils.UserSecurityUtils;
import com.dl.officialsite.login.enums.UserRoleEnum;
import com.dl.officialsite.login.model.SessionUserInfo;
import com.dl.officialsite.login.model.UserPrincipleData;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Component
@Order(Integer.MIN_VALUE)
public class LoginFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        if ("/login/nonce".equals(request.getRequestURI()) || "/login/check".equals(request.getRequestURI())
         ||"login/check-session".equals(request.getRequestURI())) {

            filterChain.doFilter(request, response);
            return;
        }

//        String addressInHeader =   request.getParameter("address");
//        String address = (String) request.getSession().getAttribute("member"+ addressInHeader);

//        logger.info("***filter***"+addressInHeader);
        //Judge whether login
            SessionUserInfo sessionUserInfo = HttpSessionUtils.getMember(request.getSession());
            if (sessionUserInfo == null || !StringUtils.hasText(sessionUserInfo.getAddress())){
                response.setContentType("application/json;charset=utf-8");
                PrintWriter out = response.getWriter();
                ObjectMapper objectMapper =  new  ObjectMapper();
                out.write( objectMapper.writeValueAsString(   BaseResponse.failWithReason("2001", "please login in")));
                out.flush();
                out.close();
                return;
            }


            //Since logon, put a temporary user data
        UserPrincipleData userPrinciple = new UserPrincipleData();
        userPrinciple.setAddress(sessionUserInfo.getAddress());
        userPrinciple.setUserRole(UserRoleEnum.NORMAL);
        UserSecurityUtils.setPrincipleLogin(userPrinciple);
        try{
            filterChain.doFilter(request, response);
        }
        finally {
            try{
                UserSecurityUtils.removeLoginStatus();
            }catch (Exception ex){

            }
        }
    }
}
