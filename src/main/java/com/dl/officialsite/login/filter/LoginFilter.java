package com.dl.officialsite.login.filter;


import com.dl.officialsite.common.base.BaseResponse;
import com.dl.officialsite.common.utils.HttpSessionUtils;
import com.dl.officialsite.common.utils.UserSecurityUtils;
import com.dl.officialsite.login.enums.UserRoleEnum;
import com.dl.officialsite.login.model.SessionUserInfo;
import com.dl.officialsite.login.model.UserPrincipleData;
import com.dl.officialsite.member.Member;
import com.dl.officialsite.member.MemberRepository;
import com.dl.officialsite.team.TeamMember;
import com.dl.officialsite.team.TeamMemberRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Component
@Order(Integer.MIN_VALUE)
@Slf4j
public class LoginFilter extends OncePerRequestFilter {

    @Autowired
    private TeamMemberRepository teamMemberRepository;

    @Autowired
    private MemberRepository memberRepository;


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
            UserRoleEnum roleEnum = deduceRole(sessionUserInfo.getAddress());
            if(roleEnum == null){
                dumpError(response);
                return;
            }
            userPrinciple.setUserRole(roleEnum);
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

    private UserRoleEnum deduceRole(String address){
        Optional<Member> optionalMember = this.memberRepository.findByAddress(address);
        if (!optionalMember.isPresent()){
            log.error("not existed user:{}", address);
            return null;
        }

        List<TeamMember> roles =this.teamMemberRepository.findByMemberId(optionalMember.get().getId());
        if (CollectionUtils.isEmpty(roles)){
            return UserRoleEnum.NORMAL;
        }

        UserRoleEnum result = UserRoleEnum.ANONYMOUS;
        for(TeamMember teamMember: roles){
            UserRoleEnum userRoleEnum = teamMember.getRole();
            if (userRoleEnum.getPower() > result.getPower()){
                result = userRoleEnum;
            }
        }

        return result;
    }


}
