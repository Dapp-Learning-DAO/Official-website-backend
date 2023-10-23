package com.dl.officialsite.team;

import com.dl.officialsite.common.base.BaseResponse;
import com.dl.officialsite.member.Member;
import com.dl.officialsite.team.vo.TeamMemberApproveVO;
import com.dl.officialsite.team.vo.TeamMemberJoinVO;
import com.dl.officialsite.team.vo.TeamQueryVo;
import com.dl.officialsite.team.vo.TeamVO;
import com.dl.officialsite.team.vo.TeamsMembersVo;
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

    //1 add create team ( or sql init) 完成
    //2 query   all  team and members 完成
    //3 query by name like 完成
    //4 member apply to   join   team and  exit team 完成
    //4.1 join need wechat or telegram info. and  send an email to team admin; 完成
    //4.2 exit  team , send an email to team admin; 完成
    // 5 team admin approve 完成

    @Autowired
    private TeamService teamService;

    /**
     * 新增团队
     */
    @PutMapping
    BaseResponse add(@RequestBody TeamVO team, @RequestParam String address) {
        teamService.add(team);
        return BaseResponse.successWithData(team);
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
    @GetMapping("/all")
    BaseResponse getTeams(@RequestBody TeamQueryVo teamQueryVo, @RequestParam String address) {
        List<TeamsMembersVo> teamAndMembers = teamService.getTeamAndMembers(teamQueryVo);
        return BaseResponse.successWithData(teamAndMembers);
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
