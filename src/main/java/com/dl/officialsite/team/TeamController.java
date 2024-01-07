package com.dl.officialsite.team;

import com.dl.officialsite.common.base.BaseResponse;
import com.dl.officialsite.common.enums.CodeEnums;
import com.dl.officialsite.common.exception.BizException;
import com.dl.officialsite.login.Auth;
import com.dl.officialsite.member.Member;
import com.dl.officialsite.member.MemberService;
import com.dl.officialsite.team.vo.TeamMemberApproveVO;
import com.dl.officialsite.team.vo.TeamMemberBatchJoinVO;
import com.dl.officialsite.team.vo.TeamMemberJoinVO;
import com.dl.officialsite.team.vo.TeamsWithMembers;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/team")
public class TeamController {
    @Autowired
    private TeamService teamService;

    @Autowired
    private MemberService memberService;


    /**
     * 新增团队
     */
    @PutMapping
    @Auth("admin")
    BaseResponse create(@RequestBody Team team, @RequestParam String address) {
        Team TeamNew =  teamService.add(team);
        return BaseResponse.successWithData(TeamNew);
    }

    /**
     * 更改管理员 todo
     */
    @PostMapping("update")
    @Auth("admin")
    BaseResponse update(@RequestBody Team team, @RequestParam String address) {
        teamService.update(team,address);
        return BaseResponse.successWithData(null);
    }

    /**
     * 加入团队
     */
    @PutMapping("/join")
    BaseResponse join(@RequestBody TeamMemberJoinVO teamMember, @RequestParam String address) {
        teamService.join(teamMember);
        return BaseResponse.successWithData(null);
    }

    /**
     * 批量加入团队
     */
    @PostMapping("/join/batch")
    @Auth("admin")
    BaseResponse batchJoin(@RequestBody TeamMemberBatchJoinVO teamMembers, @RequestParam String address) {
        teamService.batchJoin(teamMembers);
        return BaseResponse.successWithData(null);
    }

    /**
     * 退出团队
     */
    @PostMapping("/exit")
    BaseResponse exit(@RequestBody TeamMemberJoinVO teamMember, @RequestParam String address) {
        teamService.exit(teamMember, address );
        return BaseResponse.successWithData(null);
    }

    @PostMapping("/exit/admin")
    @Auth("admin")
    BaseResponse exitByAdmin(@RequestBody TeamMemberJoinVO teamMember, @RequestParam String address) {
        teamService.exit(teamMember, address );
        return BaseResponse.successWithData(null);
    }

    /**
     * 审批加入团队
     */
    @PostMapping("/approve")
    @Auth("admin")
    BaseResponse approve(@RequestBody TeamMemberApproveVO teamMemberApproveVO, @RequestParam String address) {
        teamService.approve(teamMemberApproveVO);
        return BaseResponse.successWithData(null);
    }

    /**
     * 查询所有团队
     */
    @GetMapping("/all")
    BaseResponse getAllTeams(
                          @RequestParam String address,
                          @RequestParam(defaultValue = "1") Integer pageNumber,
                          @RequestParam(defaultValue = "10") Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize);
        Page<TeamsWithMembers> allTeamsWithMembers = teamService.getAllTeamWithMembers(pageable);
        return  BaseResponse.successWithData(allTeamsWithMembers);
    }

    @GetMapping("/query")
    BaseResponse getTeams(@RequestParam(required = false) String  teamName,
                          @RequestParam(defaultValue = "0") int status,
                          @RequestParam String address,
                          @RequestParam(defaultValue = "1") Integer pageNumber,
                          @RequestParam(defaultValue = "10") Integer pageSize) {

        List<TeamsWithMembers> teamWithMembers = teamService.getTeamWithMembersByTeamNameAndStatus(teamName,status);

        PageRequest pageRequest =  PageRequest.of(pageNumber-1, pageSize);
        int start = (int) pageRequest.getOffset()  ;
        int end = Math.min((start + pageRequest.getPageSize()), teamWithMembers.size());
        List<TeamsWithMembers> pageContent = teamWithMembers.subList(start, end);
        return BaseResponse.successWithData(  new PageImpl<>(pageContent, pageRequest, teamWithMembers.size()));
    }

    @GetMapping("/id")
    BaseResponse getTeamById(@RequestParam Long teamId, @RequestParam String address) {
        TeamsWithMembers teamAndMembers = teamService.getTeamById(teamId);
        return BaseResponse.successWithData(teamAndMembers);
    }

    /**
     * 查询member属于那个团队
     */
    @GetMapping("/member-team-info")
    BaseResponse getMemberTeamsInfo(@RequestParam Long memberId, @RequestParam String address) {
        List<TeamsWithMembers> teamMembers = teamService.getMemberTeamsInfo(memberId);
        return BaseResponse.successWithData(teamMembers);
    }

    /**
     * 查询需要审批成员
     */
    @GetMapping("/need/approve")
    @Auth("admin")
    BaseResponse getNeedApproveMembers(Long teamId, @RequestParam String address) {
        List<Member> members = teamService.getNeedApproveMembers(teamId);
        return BaseResponse.successWithData(members);
    }

    /**
     * 删除Team
     */
    @DeleteMapping
    BaseResponse delete(@RequestParam Long teamId, @RequestParam String address) {
        if (teamId == 1L) {
            throw new BizException(CodeEnums.NOT_DELETE_TEAM.getCode(),
                CodeEnums.NOT_DELETE_TEAM.getMsg()) ;
        }
        teamService.checkMemberIsAdmin(address);
        teamService.delete(teamId);

        return BaseResponse.successWithData(null);
    }

}
