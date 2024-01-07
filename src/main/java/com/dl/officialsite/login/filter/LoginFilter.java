package com.dl.officialsite.login.filter;


import com.dl.officialsite.common.base.BaseResponse;
import com.dl.officialsite.common.utils.HttpSessionUtils;
import com.dl.officialsite.common.utils.UserSecurityUtils;
import com.dl.officialsite.login.model.SessionUserInfo;
import com.dl.officialsite.login.model.UserPrincipleData;
import com.dl.officialsite.member.Member;
import com.dl.officialsite.member.MemberRepository;
import com.dl.officialsite.team.teammember.TeamMember;
import com.dl.officialsite.team.teammember.TeamMemberRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.session.web.http.SessionRepositoryFilter;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

@Configuration
@ConditionalOnProperty(value="login.filter",
        havingValue = "true")
@Order(SessionRepositoryFilter.DEFAULT_ORDER + 1)
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
        add("/login/logout");
        add("/share/all");
        add("/share/queryByShareId");
        add("/share/byUser");
        add("/share/create");
        add("/share/update");
        add("/share/delete");
    }} ;

    private Set<String> noAddrCheckApis = new HashSet(){{
       add("bind/code/github");
    }};
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        log.info("Login filter session id {}, request {}", request.getSession().getId(), request.getRequestURI());
        try{
            String uri = request.getRequestURI();
            if (noLoginApis.contains(uri) || uri.contains("swagger")) {
                filterChain.doFilter(request, response);
                return;
            }

            //Must login
            if(!HttpSessionUtils.isUserLogin(request.getSession())){
                dumpError(response);
                return;
            }

            //Get user info from session
            SessionUserInfo sessionUserInfo = HttpSessionUtils.getMember(request.getSession());


            //Must check addr
            boolean noCheckAddr = this.noAddrCheckApis.contains(uri);

            if(!noCheckAddr){
                String addressInHeader = request.getParameter("address");
                if(!sessionUserInfo.getAddress().equals(addressInHeader)) {
                    dumpForbidden(response);
                    return;
                }
            }


            //Since logon, put a temporary user data in the pipeline
            UserPrincipleData userPrinciple = new UserPrincipleData();
            userPrinciple.setAddress(sessionUserInfo.getAddress());
            List<TeamMember> teams = loadTeams(sessionUserInfo.getAddress());
            userPrinciple.setTeams(teams);
            UserSecurityUtils.setPrincipleLogin(userPrinciple);

            filterChain.doFilter(request, response);
        }
        finally {
            UserSecurityUtils.clearPipelineCache();
        }
    }

    private List<TeamMember> loadTeams(String address) {
        //用户还未注册
        Optional<Member> optionalMember = this.memberRepository.findByAddress(address);
        if (!optionalMember.isPresent()){
            return Collections.EMPTY_LIST;
        }

        List<TeamMember> teamMembers =this.teamMemberRepository.findByMemberId(optionalMember.get().getId());
        return teamMembers;
    }

    private void dumpError(HttpServletResponse response) throws IOException{
        response.setContentType("application/json;charset=utf-8");
        PrintWriter out = response.getWriter();
        ObjectMapper objectMapper =  new  ObjectMapper();
        out.write( objectMapper.writeValueAsString(   BaseResponse.failWithReason("2001", "please login in")));
        out.flush();
        out.close();
    }


    private void dumpForbidden(HttpServletResponse response) throws IOException{
        response.setContentType("application/json;charset=utf-8");
        PrintWriter out = response.getWriter();
        ObjectMapper objectMapper =  new  ObjectMapper();
        out.write( objectMapper.writeValueAsString(   BaseResponse.failWithReason("2002", "not forbidden to query others")));
        out.flush();
        out.close();
    }

}
