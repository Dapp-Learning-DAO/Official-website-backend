package com.dl.officialsite.member;

import com.dl.officialsite.common.base.BaseResponse;
import com.dl.officialsite.team.Team;
import com.dl.officialsite.team.TeamRepository;
import com.dl.officialsite.team.teammember.TeamMember;
import com.dl.officialsite.team.teammember.TeamMemberRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MemberService {


    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private TeamMemberRepository teamMemberRepository;
    @Autowired
    private TeamRepository teamRepository;

    public static final Logger logger = LoggerFactory.getLogger(MemberController.class);

        public Member getMemberByAddress(String address) {
            Optional<Member> member = memberRepository.findByAddress(address);
            if(member.isPresent()) {
                return member.get();
            }
            return null;
        }


    public MemberWithTeam getMemberWithTeamInfoByAddress(String address) {
        Optional<Member> member = memberRepository.findByAddress(address);

        if(member.isPresent()) {
            MemberWithTeam memberWithTeam = new MemberWithTeam();
            BeanUtils.copyProperties(member.get(), memberWithTeam);
             ArrayList teams = new ArrayList();
            List<TeamMember> teamMembers = teamMemberRepository.findByMemberIdAndStatus(member.get().getId(), 0);

            teamMembers.stream().forEach(teamMember -> {
                Team team = teamRepository.findById(teamMember.getTeamId()).get();
                if(team.getTeamName().equals("Dapp-Learning DAO co-founders")){
                    memberWithTeam.setAdmin(true);
                }
                teams.add(team);
            });
            memberWithTeam.setTeams(teams);
            return memberWithTeam;
        }
        return null;
    }

    public MemberVo save(Member member) {
            memberRepository.save(member);
            MemberVo memberVo = new MemberVo();
        BeanUtils.copyProperties(member, memberVo);
         return memberVo;

    }

    public MemberVo getMemberPrivacyInfo(String address) {
        Optional<Member> member = memberRepository.findByAddress(address);
        if (!member.isPresent()) {
            return null;
        }
        MemberVo memberVo = new MemberVo();
        BeanUtils.copyProperties(member.get(), memberVo);

      return memberVo;

    }
//            } catch (DataIntegrityViolationException e) {
//
//                String mostSpecificCauseMessage = e.getMostSpecificCause().getMessage();
//                if (e.getCause() instanceof ConstraintViolationException) {
//                    String name = ((ConstraintViolationException) e.getCause()).getConstraintName();
//                    logger.info("Encountered ConstraintViolationException, details: " + mostSpecificCauseMessage + "constraintName: "+ name);
//                }
//                return BaseResponse.failWithReason("1000", mostSpecificCauseMessage);
//              }

}
