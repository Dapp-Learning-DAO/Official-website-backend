package com.dl.officialsite.member;

import com.dl.officialsite.team.Team;
import com.dl.officialsite.team.TeamRepository;
import com.dl.officialsite.team.teammember.TeamMember;
import com.dl.officialsite.team.teammember.TeamMemberRepository;
import com.dl.officialsite.team.vo.TeamVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public Member getMemberByAddress(String address) {
        Optional<Member> member = memberRepository.findByAddress(address);
        if(member.isPresent()) {
            return member.get();
        }
        return null;
    }


    public Member getMemberWithTeamInfoByAddress(String address) {
        Optional<Member> member = memberRepository.findByAddress(address);
        if(member.isPresent()) {
            MemberWithTeam memberWithTeam = new MemberWithTeam();
            BeanUtils.copyProperties(member, memberWithTeam);

             List teams = memberWithTeam.getTeams();
            List<TeamMember> teamMembers = teamMemberRepository.findByMemberId(member.get().getId());
            teamMembers.stream().forEach(teamMember -> {
                Team team = teamRepository.findById(teamMember.getTeamId()).get();
                if(team.getTeamName().equals("Dapp-Learning DAO co-founders")){
                    memberWithTeam.setAdmin(true);
                }
                teams.add(team);
            });
        }
        return null;
    }
}
