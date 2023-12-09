package com.dl.officialsite.team;


import com.dl.officialsite.common.constants.Constants;
import com.dl.officialsite.common.enums.CodeEnums;
import com.dl.officialsite.common.exception.BizException;
import com.dl.officialsite.mail.EmailService;
import com.dl.officialsite.member.Member;
import com.dl.officialsite.member.MemberRepository;
import com.dl.officialsite.team.vo.TeamMemberApproveVO;
import com.dl.officialsite.team.vo.TeamMemberBatchJoinVO;
import com.dl.officialsite.team.vo.TeamMemberJoinVO;
import com.dl.officialsite.team.vo.TeamQueryVo;
import com.dl.officialsite.team.vo.TeamVO;
import com.dl.officialsite.team.vo.TeamsMembersVo;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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
 * @Description TODO
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

    @Transactional
    public void add(TeamVO teamVO) {
        Team team = new Team();
        BeanUtils.copyProperties(teamVO, team);
        teamRepository.save(team);
    }

    public List<TeamsMembersVo> getTeamAndMembers(TeamQueryVo teamQueryVo) {
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

        List<TeamsMembersVo> teamsMembersVos = new ArrayList<>();
        List<Team> teams = teamRepository.findAll(queryParam);
        teams.stream().forEach(team -> {
            TeamsMembersVo teamsMembersVo = new TeamsMembersVo();
            BeanUtils.copyProperties(team, teamsMembersVo);
            List<Member> members = new ArrayList<>();
            List<Long> memberIds = teamMemberRepository.findByTeamIdStatus(team.getId(),
                teamQueryVo.getStatus());
            memberIds.stream().forEach(memberId -> {
                Member member = memberRepository.findById(memberId).get();
                members.add(member);
            });
            teamsMembersVo.setMembers(members);
            teamsMembersVos.add(teamsMembersVo);
        });
        return teamsMembersVos;
    }

    @Transactional(rollbackOn = Exception.class)
    public void join(TeamMemberJoinVO teamMember) {
        Member member = memberRepository.findById(teamMember.getMemberId()).get();
/*        if (ObjectUtils.isEmpty(member.getTelegramId()) || ObjectUtils.isEmpty(
            member.getWechatId())) {
            throw new BizException(CodeEnums.TELEGRAM_WECHAT_NOT_BIND.getCode(),
                CodeEnums.TELEGRAM_WECHAT_NOT_BIND.getMsg());
        }*/
        //判断是否已经退出过团队
        Optional<TeamMember> optional = teamMemberRepository.findByTeamAndMember(
            teamMember.getTeamId()
            , teamMember.getMemberId());
        if (optional.isPresent()) {
            TeamMember teamMember2 = optional.get();
            if (teamMember2.getStatus() == Constants.REQUEST_TEAM) {
                throw new BizException(CodeEnums.MEMBER_ALREADY_REQUEST_TEAM.getCode(),
                    CodeEnums.MEMBER_ALREADY_REQUEST_TEAM.getMsg());
            }
            teamMember2.setStatus(Constants.REQUEST_TEAM);
            teamMemberRepository.save(teamMember2);
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
                teamMember.setStatus(Constants.APPROVE_TEAM);
                teamMembers.add(teamMember);
            }
        });
        teamMemberRepository.saveAll(teamMembers);
    }


    public void exit(TeamMemberJoinVO teamMember) {
        TeamMember teamMember1 = new TeamMember();
        BeanUtils.copyProperties(teamMember, teamMember1);
        teamMember1.setStatus(Constants.EXIT_TEAM);
        teamMemberRepository.save(teamMember1);
        Team team = teamRepository.findById(teamMember.getTeamId()).get();
        Member member = memberRepository.findById(teamMember.getMemberId()).get();
        String subject = team.getTeamName() + "团队成员退出";
        String text = member.getNickName() + "成员退出";
        List<String> mailAddress = new ArrayList<>();
        if (mailAddress.size() != 0) {
            emailService.memberExitTeam(mailAddress, subject, text);
        }
    }

    public List<Member> getNeedApproveMembers(Long teamId) {
        List<Long> memberIds = teamMemberRepository.findByTeamIdAndStatus(teamId,
            Constants.REQUEST_TEAM);
        List<Member> members = new ArrayList<>();
        memberIds.stream().forEach(memberId -> {
            Member member = memberRepository.findById(memberId).get();
            members.add(member);
        });
        return members;
    }

    public List<TeamVO> getMemberRole(Long memberId) {
        List<TeamMember> teamMembers = teamMemberRepository.findByMemberId(memberId);
        if (teamMembers.size() == 0) {
            throw new BizException(CodeEnums.MEMBER_NOT_IN_TEAM.getCode(),
                CodeEnums.MEMBER_NOT_IN_TEAM.getMsg());
        } else {
            List<TeamVO> teamVOS = new ArrayList<>();
            teamMembers.stream().forEach(teamMember -> {
                Team team = teamRepository.findById(teamMember.getTeamId()).get();
                TeamVO teamVO = new TeamVO();
                BeanUtils.copyProperties(team, teamVO);
                teamVO.setStatus(teamMember.getStatus());
                teamVOS.add(teamVO);
            });
            return teamVOS;
        }
    }

    public TeamsMembersVo getTeamById(Long teamId) {
        Optional<Team> optional = teamRepository.findById(teamId);
        if (optional.isPresent()) {
            TeamsMembersVo teamsMembersVo = new TeamsMembersVo();
            Team team = optional.get();
            List<Member> members = new ArrayList<>();
            List<Long> memberIds = teamMemberRepository.findByTeamId(team.getId());
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
        teamMembers.getMemberIds().forEach(memberId -> {
            Optional<TeamMember> optional = teamMemberRepository.findByTeamAndMember(
                teamMembers.getTeamId(), memberId);
            if (optional.isPresent()) {
                TeamMember teamMember2 = optional.get();
                if (teamMember2.getStatus() == Constants.REQUEST_TEAM) {
                    throw new BizException(CodeEnums.MEMBER_ALREADY_REQUEST_TEAM.getCode(),
                        CodeEnums.MEMBER_ALREADY_REQUEST_TEAM.getMsg());
                }
                teamMember2.setStatus(Constants.APPROVE_TEAM);
                teamMemberRepository.save(teamMember2);
            } else {
                TeamMember teamMember1 = new TeamMember();
                teamMember1.setMemberId(memberId);
                teamMember1.setTeamId(teamMembers.getTeamId());
                teamMember1.setStatus(Constants.APPROVE_TEAM);
                teamMemberRepository.save(teamMember1);
            }
        });
    }
}