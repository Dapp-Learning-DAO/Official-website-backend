package com.dl.officialsite.team;

import com.dl.officialsite.common.base.BaseResponse;
import com.dl.officialsite.common.enums.CodeEnums;
import com.dl.officialsite.common.exception.BizException;
import com.dl.officialsite.login.Auth;
import com.dl.officialsite.member.Member;
import com.dl.officialsite.team.vo.TeamMemberApproveVO;
import com.dl.officialsite.team.vo.TeamMemberBatchJoinVO;
import com.dl.officialsite.team.vo.TeamMemberJoinVO;
import com.dl.officialsite.team.vo.TeamQueryVo;
import com.dl.officialsite.team.vo.TeamVO;
import com.dl.officialsite.team.vo.TeamsWithMembers;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
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


    @GetMapping("/admin/list")
    BaseResponse list(@RequestParam String address) {
        List<String> list = new ArrayList<>();
        list.add("0x4DDE628ef50dE13E6E369353128A0d7899B54B6b");
        return BaseResponse.successWithData(list);
    }

    /**
     * 新增团队
     */
    @PutMapping
    @Auth("admin")
    BaseResponse create(@RequestBody TeamVO team, @RequestParam String address) {
        if (teamService.checkMemberIsAdmin(address)) {
            throw new BizException(CodeEnums.NOT_THE_ADMIN.getCode(), CodeEnums.NOT_THE_ADMIN.getMsg());
        }
        Team TeamNew =  teamService.add(team);
        return BaseResponse.successWithData(TeamNew);
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
    BaseResponse batchJoin(@RequestBody TeamMemberBatchJoinVO teamMembers, @RequestParam String address) {
        if (teamService.checkMemberIsAdmin(address)) {
            throw new BizException(CodeEnums.NOT_THE_ADMIN.getCode(), CodeEnums.NOT_THE_ADMIN.getMsg());
        }
        teamService.batchJoin(teamMembers);
        return BaseResponse.successWithData(null);
    }

    /**
     * 退出团队
     */
    @PostMapping("/exit")
    BaseResponse exit(@RequestBody TeamMemberJoinVO teamMember, @RequestParam String address) {
        teamService.exit(teamMember);
        return BaseResponse.successWithData(null);
    }

    /**
     * 审批加入团队
     */
    @PostMapping("/approve")
    BaseResponse approve(@RequestBody TeamMemberApproveVO teamMemberApproveVO, @RequestParam String address) {
        teamService.approve(teamMemberApproveVO);
        return BaseResponse.successWithData(null);
    }

    /**
     * 查询所有团队
     */
    @PostMapping("/all")
    BaseResponse getTeams(@RequestBody TeamQueryVo teamQueryVo, @RequestParam String address) {
        List<TeamsWithMembers> teamAndMembers = teamService.getTeamWithMembersByTeamNameAndStatus(teamQueryVo);
        return BaseResponse.successWithData(teamAndMembers);
    }

    @GetMapping("/id")
    BaseResponse getTeamById(@RequestParam Long teamId, @RequestParam String address) {
        TeamsWithMembers teamAndMembers = teamService.getTeamById(teamId);
        return BaseResponse.successWithData(teamAndMembers);
    }

    /**
     * 查询member属于那个团队
     */
    @GetMapping("/member/role")
    BaseResponse getMemberRole(@RequestParam Long memberId, @RequestParam String address) {
        List<TeamVO> teamMembers = teamService.getMemberRole(memberId);
        return BaseResponse.successWithData(teamMembers);
    }

    /**
     * 查询需要审批成员
     */
    @GetMapping("/need/approve")
    BaseResponse getNeedApproveMembers(Long teamId, @RequestParam String address) {
        List<Member> members = teamService.getNeedApproveMembers(teamId);
        return BaseResponse.successWithData(members);
    }

}
