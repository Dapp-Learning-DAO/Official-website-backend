package com.dl.officialsite.team;

import com.dl.officialsite.common.constants.Constants;
import com.dl.officialsite.common.enums.CodeEnums;
import com.dl.officialsite.common.exception.BizException;
import com.dl.officialsite.login.enums.UserRoleEnum;
import com.dl.officialsite.mail.EmailService;
import com.dl.officialsite.member.Member;
import com.dl.officialsite.member.MemberManager;
import com.dl.officialsite.member.MemberRepository;
import com.dl.officialsite.team.teammember.TeamMember;
import com.dl.officialsite.team.teammember.TeamMemberRepository;
import com.dl.officialsite.team.vo.TeamMemberApproveVO;
import com.dl.officialsite.team.vo.TeamMemberBatchJoinVO;
import com.dl.officialsite.team.vo.TeamMemberJoinVO;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
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
    private MemberManager memberManager;

    @Transactional
    public Team add(Team team) {
        // check address
        Member member = this.memberManager.getMemberByAddress(team.getAdministrator());
        if (member == null) {
            throw new BizException(CodeEnums.NOT_FOUND_MEMBER.getCode(), CodeEnums.NOT_FOUND_MEMBER.getMsg());
        }
        teamRepository.save(team);
        TeamMember teamMember = new TeamMember();
        teamMember.setTeamId(team.getId());
        teamMember.setMemberId(member.getId());
        teamMember.setRole(UserRoleEnum.ADMIN);
        teamMember.setStatus(0);
        teamMemberRepository.save(teamMember);
        return team;

    }

    public List<TeamsWithMembers> getTeamWithMembersByTeamNameAndStatus(String teamName, int status) {
        // find team
        Specification<Team> queryParam = new Specification<Team>() {
            @Override
            public javax.persistence.criteria.Predicate toPredicate(
                    Root<Team> root,
                    CriteriaQuery<?> criteriaQuery,
                    CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                if (teamName != null) {
                    predicates.add(criteriaBuilder.like(root.get("teamName"),
                            "%" + teamName + "%"));
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

            List<Long> memberIds = teamMemberRepository.findByTeamIdAndStatus(team.getId(), status);
            List<Member> members = memberRepository.findByIdIn(memberIds);
            teamsMembersVo.setMembers(members);
            teamsWithMembers.add(teamsMembersVo);
        });
        return teamsWithMembers;
    }

    public Page<TeamsWithMembers> getAllTeamWithMembers(Pageable pageable) {

        Page<Team> teamsPage = teamRepository.findAll(pageable);
        List teams = teamsPage.getContent();
        List<TeamsWithMembers> teamsWithMembers = new ArrayList<>();
        teams.stream().forEach(team -> {
            TeamsWithMembers teamsMembersVo = new TeamsWithMembers();
            BeanUtils.copyProperties(team, teamsMembersVo);

            List<Long> memberIds = teamMemberRepository.findAll().stream().map(x -> x.getMemberId())
                    .collect(Collectors.toList());
            List<Member> members = memberRepository.findByIdIn(memberIds);
            teamsMembersVo.setMembers(members);
            teamsWithMembers.add(teamsMembersVo);
        });
        return new PageImpl<>(teamsWithMembers, pageable, teamsWithMembers.size());

    }

    @Transactional(rollbackOn = Exception.class)
    public void join(TeamMemberJoinVO teamMember) {
        Optional<TeamMember> teamMemberOptional = teamMemberRepository.findByTeamAndMember(
                teamMember.getTeamId(), teamMember.getMemberId());

        // refactor
        if (teamMemberOptional.isPresent()) {
            TeamMember teamMember2 = teamMemberOptional.get();
            // already apply
            if (teamMember2.getStatus() == Constants.REQUEST_TEAM) {
                throw new BizException(CodeEnums.MEMBER_ALREADY_REQUEST_TEAM.getCode(),
                        CodeEnums.MEMBER_ALREADY_REQUEST_TEAM.getMsg());
            }
            if (teamMember2.getStatus() == Constants.IN_TEAM) {
                throw new BizException(CodeEnums.MEMBER_ALREADY_IN_TEAM.getCode(),
                        CodeEnums.MEMBER_ALREADY_IN_TEAM.getMsg());
            }

            // removed or rejected before
            teamMember2.setStatus(Constants.REQUEST_TEAM);
            teamMemberRepository.save(teamMember2);
            // 发送邮件
            Team team = teamRepository.findById(teamMember.getTeamId()).get();
            Member member = memberRepository.findById(teamMember.getMemberId()).get();
            String administratorAddress = team.getAdministrator();
            if (!ObjectUtils.isEmpty(administratorAddress) || !"".equals(administratorAddress)) {
                Optional<Member> admin = memberRepository.findByAddress(administratorAddress);
                if (admin.isPresent()) {
                    Member member1 = admin.get();
                    String email = member1.getEmail();
                    String subject = "新成员加入通知： team:"+team.getTeamName() + "有新成员"+ member.getNickName()+"加入申请！";
                    String content = "新成员加入通知: 点击此链接去处理" + "https://dapplearning.org/team/admin";
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
            //todo
            TeamMember teamMember1 = new TeamMember();
            BeanUtils.copyProperties(teamMember, teamMember1);
            teamMember1.setStatus(Constants.REQUEST_TEAM);
            teamMemberRepository.save(teamMember1);
            // 发送邮件
            Team team = teamRepository.findById(teamMember.getTeamId()).get();
            Member member = memberRepository.findById(teamMember.getMemberId()).get();
            String administratorAddress = team.getAdministrator();
            if (!ObjectUtils.isEmpty(administratorAddress) || !"".equals(administratorAddress)) {
                Optional<Member> admin = memberRepository.findByAddress(administratorAddress);
                if (admin.isPresent()) {
                    Member member1 = admin.get();
                    String email = member1.getEmail();
                    String subject = "新成员加入通知： team:"+team.getTeamName() + "有新成员"+ member.getNickName()+"加入申请！";
                    String content = "新成员加入通知: 点击此链接去处理" + "https://dapplearning.org/team/admin";
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
        // todo 增加discord telegram 通知   显示成员名 + 加入 + 团队名
    }

    public void exit(TeamMemberJoinVO teamMember, String address) {

        Optional<Member> member = memberRepository.findById(teamMember.getMemberId());
        if (member.isPresent()) {
            // only self or admin
            if (checkMemberIsAdmin(address) || member.get().getAddress().equals(address)) {
                teamMemberRepository.findByTeamAndMember(teamMember.getTeamId(),
                        teamMember.getMemberId()).ifPresent(teamMember1 -> {
                            teamMember1.setStatus(Constants.EXIT_TEAM);
                            teamMemberRepository.save(teamMember1);
                        });

                Team team = teamRepository.findById(teamMember.getTeamId()).get();
                Member member1 = memberRepository.findById(teamMember.getMemberId()).get();
                String subject = team.getTeamName() + "团队成员退出";
                String text = member1.getNickName() + "成员退出";
                emailService.sendMail(member.get().getEmail(), subject, text);
            } else {
                throw new BizException(CodeEnums.NOT_AUTHORITY_FOR_EXIT.getCode(),
                        CodeEnums.NOT_AUTHORITY_FOR_EXIT.getMsg());
            }

        }
    }

    public List<Member> getNeedApproveMembers(Long teamId) {
        List<Long> memberIds = teamMemberRepository.findByTeamIdAndStatus(teamId,
                Constants.REQUEST_TEAM);
        List<Member> members = memberRepository.findByIdIn(memberIds);
        return members;
    }

    public List<TeamsWithMembers> getMemberTeamsInfo(Long memberId) {
        List<TeamMember> teamMembers = teamMemberRepository.findByMemberId(memberId);
        // ids
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

    public boolean checkMemberIsAdmin(String address) {

        Member member = this.memberManager.getMemberByAddress(address);
        if (member == null) {
            return false;
        }
        // id 0
        List<Long> adminMembers = teamMemberRepository.findByTeamId(1L);
        if (adminMembers.contains(member.getId())) {
            return true;
        }
        return false;
    }

    /**
     * 检查是否为超级管理员
     */
    public boolean checkMemberIsSuperAdmin(String address) {

        Member member = this.memberManager.getMemberByAddress(address);
        if (member == null) {
            return false;
        }
        // id 0
        Team team = teamRepository.findById(1L).orElseThrow(() -> new BizException(CodeEnums.TEAM_NOT_EXIST.getCode(),
                CodeEnums.TEAM_NOT_EXIST.getMsg()));
        String admin = team.getAdministrator();
        if (admin.equals(address)) {
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
        Map<Long, TeamMember> existingMembersMap = teamMemberRepository
                .findByTeamAndMembers(teamMembers.getTeamId(), memberIds)
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

    public void update(Team team, String address) {
        if (team.getAdministrator() != null) {
            if (!checkMemberIsSupperAdmin(address)) {
                throw new BizException(CodeEnums.NOT_THE_SUPER_ADMIN.getCode(),
                        CodeEnums.NOT_THE_SUPER_ADMIN.getMsg());
            }
            Team teamdb = teamRepository.findById(team.getId()).get();
            teamdb.setAdministrator(team.getAdministrator());
            teamRepository.save(teamdb);
        } else {
            Team teamdb = teamRepository.findById(team.getId()).get();
            if (team.getTeamName() != null) {
                teamdb.setTeamName(team.getTeamName());
            }
            if (team.getTeamProfile() != null) {
                teamdb.setTeamProfile(team.getTeamProfile());
            }
            if(team.getLink()!=null) {
                teamdb.setLink(team.getLink());
            }
            teamRepository.save(teamdb);
        }

    }

    public boolean checkMemberIsSupperAdmin(String address) {
        Team adminTeam = teamRepository.findById(1L).get();
        if (adminTeam.getAdministrator().equals(address)) {
            return true;
        }
        return false;
    }

    @Transactional(rollbackOn = Exception.class)
    public void delete(Long teamId) {
        teamRepository.deleteById(teamId);
        this.deleteTeamMember(teamId);
    }

    public void deleteTeamMember(Long teamId) {
        teamMemberRepository.deleteByTeamId(teamId);
    }
}