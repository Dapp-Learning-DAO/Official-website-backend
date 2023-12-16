package com.dl.officialsite.team;


import com.dl.officialsite.common.constants.Constants;
import com.dl.officialsite.common.enums.CodeEnums;
import com.dl.officialsite.common.exception.BizException;
import com.dl.officialsite.mail.EmailService;
import com.dl.officialsite.member.Member;
import com.dl.officialsite.member.MemberRepository;
import com.dl.officialsite.member.MemberService;
import com.dl.officialsite.team.teammember.TeamMember;
import com.dl.officialsite.team.teammember.TeamMemberRepository;
import com.dl.officialsite.team.vo.TeamMemberApproveVO;
import com.dl.officialsite.team.vo.TeamMemberBatchJoinVO;
import com.dl.officialsite.team.vo.TeamMemberJoinVO;
import com.dl.officialsite.team.vo.TeamQueryVo;
import com.dl.officialsite.team.vo.TeamsWithMembers;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

/**
 * @ClassName TeamService
 * @Author jackchen
 * @Date 2023/10/21 17:23
 * @Description TeamService
 **/
@Slf4j
@Service
public class TeamService {

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private TeamMemberRepository teamMemberRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private MemberService memberService;



    @Transactional
    public Team add(Team team) {
        return teamRepository.save(team);
    }

    public List<TeamsWithMembers> getTeamWithMembersByTeamNameAndStatus(TeamQueryVo teamQueryVo) {

        //find team
        Specification<Team> queryParam = new Specification<Team>() {
            @Override
            public javax.persistence.criteria.Predicate toPredicate(
                Root<Team> root,
                CriteriaQuery<?> criteriaQuery,
                CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                if (teamQueryVo.getTeamName() != null) {
                    predicates.add(criteriaBuilder.like(root.get("teamName"),
                        "%" + teamQueryVo.getTeamName() + "%"));
                }
                return criteriaBuilder.and(predicates.toArray(
                    new javax.persistence.criteria.Predicate[predicates.size()]));
            }
        };

        List<TeamsWithMembers> teamsWithMembers = new ArrayList<>();
        List<Team> teams = teamRepository.findAll(queryParam);

        // find members
        teams.stream().forEach(team -> {
            TeamsWithMembers teamsMembersVo = new TeamsWithMembers();
            BeanUtils.copyProperties(team, teamsMembersVo);

            List<Long> memberIds = teamMemberRepository.findByTeamIdAndStatus(team.getId(),
                teamQueryVo.getStatus());
//            memberIds.stream().forEach(memberId -> {
//                Member member = memberRepository.findById(memberId).get();
//                members.add(member);
//            });
            List<Member> members  = memberRepository.findByIdIn(memberIds);
            teamsMembersVo.setMembers(members);
            teamsWithMembers.add(teamsMembersVo);
        });
        return teamsWithMembers;
    }

    @Transactional(rollbackOn = Exception.class)
    public void join(TeamMemberJoinVO teamMember) {

        Member member = memberRepository.findById(teamMember.getMemberId()).get();

        //todo
/*        if (ObjectUtils.isEmpty(member.getTelegramId()) || ObjectUtils.isEmpty(
            member.getWechatId())) {
            throw new BizException(CodeEnums.TELEGRAM_WECHAT_NOT_BIND.getCode(),
                CodeEnums.TELEGRAM_WECHAT_NOT_BIND.getMsg());
        }*/
        Optional<TeamMember> optional = teamMemberRepository.findByTeamAndMember(
            teamMember.getTeamId(), teamMember.getMemberId());

        // refactor
        if (optional.isPresent()) {
            TeamMember teamMember2 = optional.get();
            //already apply
            if (teamMember2.getStatus() == Constants.REQUEST_TEAM) {
                throw new BizException(CodeEnums.MEMBER_ALREADY_REQUEST_TEAM.getCode(),
                    CodeEnums.MEMBER_ALREADY_REQUEST_TEAM.getMsg());
            }
            if (teamMember2.getStatus() == Constants.IN_TEAM) {
                throw new BizException(CodeEnums.MEMBER_ALREADY_IN_TEAM.getCode(),
                        CodeEnums.MEMBER_ALREADY_IN_TEAM.getMsg());
            }

            //todo 存在为什么要发再set状态以及发邮件
//            teamMember2.setStatus(Constants.REQUEST_TEAM);
//            teamMemberRepository.save(teamMember2);
//            //发送邮件
//            Team team = teamRepository.findById(teamMember.getTeamId()).get();
//            String administratorAddress = team.getAdministrator();
//            if (!ObjectUtils.isEmpty(administratorAddress) || !"".equals(administratorAddress)) {
//                Optional<Member> admin = memberRepository.findByAddress(administratorAddress);
//                if (admin.isPresent()) {
//                    Member member1 = admin.get();
//                    String email = member1.getEmail();
//                    String subject = team.getTeamName() + "团队新成员"+ member1.getNickName()+"加入申请";
//                    String content = "点击此链接去处理" + "https://dapplearning.org/team/admin";
//                    List<String> mailAddress = new ArrayList<>();
//                    mailAddress.add(email);
//                    log.info("发送邮件给管理员:{},接收地址{}", email, mailAddress);
//                    emailService.memberJoinTeam(mailAddress, subject, content);
//                } else {
//                    throw new BizException(CodeEnums.TEAM_ADMIN_NOT_EXIST.getCode(),
//                        CodeEnums.TEAM_ADMIN_NOT_EXIST.getMsg());
//                }

//            } else {
//                throw new BizException(CodeEnums.TEAM_ADMIN_NOT_EXIST.getCode(),
//                    CodeEnums.TEAM_ADMIN_NOT_EXIST.getMsg());
//            }
        } else {
            TeamMember teamMember1 = new TeamMember();
            BeanUtils.copyProperties(teamMember, teamMember1);
            teamMember1.setStatus(Constants.REQUEST_TEAM);
            teamMemberRepository.save(teamMember1);
            //发送邮件
            Team team = teamRepository.findById(teamMember.getTeamId()).get();
            String administratorAddress = team.getAdministrator();
            if (!ObjectUtils.isEmpty(administratorAddress) || !"".equals(administratorAddress)) {
                Optional<Member> admin = memberRepository.findByAddress(administratorAddress);
                if (admin.isPresent()) {
                    Member member1 = admin.get();
                    String email = member1.getEmail();
                    String subject = team.getTeamName() + "团队新成员"+ member1.getNickName()+"加入申请";
                    String content = "点击此链接去处理" + "https://dapplearning.org/team/admin";
                    List<String> mailAddress = new ArrayList<>();
                    mailAddress.add(email);
                    log.info("发送邮件给管理员:{},接收地址{}", email, mailAddress);
                    emailService.memberJoinTeam(mailAddress, subject, content);
                } else {
                    throw new BizException(CodeEnums.TEAM_ADMIN_NOT_EXIST.getCode(),
                        CodeEnums.TEAM_ADMIN_NOT_EXIST.getMsg());
                }

            } else {
                throw new BizException(CodeEnums.TEAM_ADMIN_NOT_EXIST.getCode(),
                    CodeEnums.TEAM_ADMIN_NOT_EXIST.getMsg());
            }
        }

    }

    @Transactional(rollbackOn = Exception.class)
    public void approve(TeamMemberApproveVO teamMemberApproveVO) {

        List<Long> memberIds = teamMemberApproveVO.getMemberIds();
        List<TeamMember> teamMembers = new ArrayList<>();
        memberIds.stream().forEach(memberId -> {
            Optional<TeamMember> optional = teamMemberRepository.findByTeamAndMember(
                teamMemberApproveVO.getTeamId(), memberId);
            if (optional.isPresent()) {
                TeamMember teamMember = optional.get();
                teamMember.setStatus(teamMemberApproveVO.getStatus());
                teamMembers.add(teamMember);
            } else {
                throw new BizException(CodeEnums.TEAM_ADMIN_NOT_EXIST.getCode(),
                        CodeEnums.TEAM_ADMIN_NOT_EXIST.getMsg());
            }
        });
        teamMemberRepository.saveAll(teamMembers);
    }


    public void exit(TeamMemberJoinVO teamMember, String address) {

       Optional<Member> member =  memberRepository.findByAddress(address);
       if(member.isPresent() ) {

           if (checkMemberIsAdmin(address) || member.get().getId().equals(teamMember.getMemberId())) {
               teamMemberRepository.findByTeamAndMember(teamMember.getTeamId(),
                       teamMember.getMemberId()).ifPresent(teamMember1 -> {
                   teamMember1.setStatus(Constants.EXIT_TEAM);
                   teamMemberRepository.save(teamMember1);
               });
           }

           Team team = teamRepository.findById(teamMember.getTeamId()).get();
           Member member1 = memberRepository.findById(teamMember.getMemberId()).get();
           String subject = team.getTeamName() + "团队成员退出";
           String text = member1.getNickName() + "成员退出";
           emailService.sendMail(member.get().getEmail(), subject, text);

       }
    }

    public List<Member> getNeedApproveMembers(Long teamId) {
        List<Long> memberIds = teamMemberRepository.findByTeamIdAndStatus(teamId,
            Constants.REQUEST_TEAM);
        List<Member> members =   memberRepository.findByIdIn(memberIds);
        return members;
    }

    public List<TeamsWithMembers> getMemberTeamsInfo(Long memberId) {
        List<TeamMember> teamMembers = teamMemberRepository.findByMemberId(memberId);
        if (teamMembers.size() == 0) {
            throw new BizException(CodeEnums.MEMBER_NOT_IN_TEAM.getCode(),
                CodeEnums.MEMBER_NOT_IN_TEAM.getMsg());
        } else {
            //ids
            List<TeamsWithMembers> teamsWithMembers = new ArrayList<>();

            teamMembers.stream().forEach(teamMember -> {
                Team team = teamRepository.findById(teamMember.getTeamId()).get();
                TeamsWithMembers teamVO = new TeamsWithMembers();
                BeanUtils.copyProperties(team, teamVO);
                teamVO.setStatus(teamMember.getStatus());
                teamsWithMembers.add(teamVO);
            });
            return teamsWithMembers;
        }
    }


    public boolean checkMemberIsAdmin(String address) {

        Member member =  memberService.getMemberByAddress(address);
        if(member == null) {
            return false;
        }
        // id 0
        List<Long> adminMembers = teamMemberRepository.findByTeamId(1L);
           if(adminMembers.contains(member)) {
               return true;
        }
           return false;
    }


    public TeamsWithMembers getTeamById(Long teamId) {
        Optional<Team> optional = teamRepository.findById(teamId);
        if (optional.isPresent()) {
            TeamsWithMembers teamsMembersVo = new TeamsWithMembers();
            Team team = optional.get();
            List<Member> members = new ArrayList<>();
            List<Long> memberIds = teamMemberRepository.findByTeamIdAndStatus(team.getId(), Constants.IN_TEAM);
            memberIds.stream().forEach(memberId -> {
                Member member = memberRepository.findById(memberId).get();
                members.add(member);
            });
            teamsMembersVo.setMembers(members);
            BeanUtils.copyProperties(team, teamsMembersVo);
            return teamsMembersVo;
        } else {
            throw new BizException(CodeEnums.TEAM_NOT_EXIST.getCode(),
                CodeEnums.TEAM_NOT_EXIST.getMsg());
        }
    }

    public void batchJoin(TeamMemberBatchJoinVO teamMembers) {
        List<TeamMember> teamMemberList = new ArrayList<>();
        List<Long> memberIds = teamMembers.getMemberIds();

        // 批量查询已存在的TeamMember
        Map<Long, TeamMember> existingMembersMap = teamMemberRepository.findByTeamAndMembers(teamMembers.getTeamId(), memberIds)
            .stream()
            .collect(Collectors.toMap(TeamMember::getMemberId, Function.identity()));
        for (Long memberId : memberIds) {
            TeamMember teamMember = existingMembersMap.getOrDefault(memberId, new TeamMember());
            teamMember.setMemberId(memberId);
            teamMember.setTeamId(teamMembers.getTeamId());
            teamMember.setStatus(Constants.IN_TEAM);
            teamMemberList.add(teamMember);
        }
        teamMemberRepository.saveAll(teamMemberList);
    }
}