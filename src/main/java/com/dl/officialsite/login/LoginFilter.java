package com.dl.officialsite.login;


import com.dl.officialsite.common.base.BaseResponse;
import com.dl.officialsite.common.enums.CodeEnums;
import com.dl.officialsite.member.Member;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import static com.dl.officialsite.common.enums.CodeEnums.LOGIN_IN;

@Component
public class LoginFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        if ("/login/nonce".equals(request.getRequestURI()) || "/login/check".equals(request.getRequestURI())
         || "oauth2/authorization/github".equals(request.getRequestURI())) {
            filterChain.doFilter(request, response);
            return;
        }


        String address = (String) request.getSession().getAttribute("member");
        if (address != null) {
            filterChain.doFilter(request, response);
            return;
        }

        response.setContentType("application/json;charset=utf-8");
        PrintWriter out = response.getWriter();

        BaseResponse baseResponse = BaseResponse.failWithReason("2001", "please login in");

        ObjectMapper objectMapper =  new  ObjectMapper();

        String s =  objectMapper.writeValueAsString( baseResponse );
        out.write(s);
        out.flush();
        out.close();
    }
}
