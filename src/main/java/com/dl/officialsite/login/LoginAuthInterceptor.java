package com.dl.officialsite.login;

import com.dl.officialsite.common.base.BaseResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

public class LoginAuthInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        String addressInHeader =   request.getParameter("address");

        String userId = (String) session.getAttribute("member"+ addressInHeader);
        if (userId == null) {
            authFailOutput(response, "please login");
            return false;
        }
        return true;
    }
    /**
     * json输出
     */
    private void authFailOutput(HttpServletResponse response, String msg) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
        ObjectMapper objectMapper = new ObjectMapper();
        out.write(objectMapper.writeValueAsString(BaseResponse.failWithReason("90000", msg)));
        out.flush();
        out.close();
    }
}
