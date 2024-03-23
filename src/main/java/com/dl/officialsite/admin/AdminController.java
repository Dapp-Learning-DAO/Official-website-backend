package com.dl.officialsite.admin;

import com.dl.officialsite.admin.vo.HireSearchParams;
import com.dl.officialsite.bounty.BountyService;
import com.dl.officialsite.bounty.vo.BountySearchVo;
import com.dl.officialsite.bounty.vo.BountyVo;
import com.dl.officialsite.common.base.BaseResponse;
import com.dl.officialsite.common.enums.CodeEnums;
import com.dl.officialsite.common.exception.BizException;
import com.dl.officialsite.hiring.HireService;
import com.dl.officialsite.member.MemberService;
import com.dl.officialsite.team.TeamService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName AdminController
 * @Author jackchen
 * @Date 2024/3/23 15:26
 * @Description admin
 **/
@RestController
@RequestMapping("/admin")
@Slf4j
public class AdminController {

    private final MemberService memberService;

    private final TeamService teamService;

    private final HireService hireService;

    private final BountyService bountyService;

    public AdminController(MemberService memberService, TeamService teamService, HireService hireService,
        BountyService bountyService) {
        this.memberService = memberService;
        this.teamService = teamService;
        this.hireService = hireService;
        this.bountyService = bountyService;
    }

    /**
     * freeze member
     * //todo 查询member list把禁用的member filter out/ member不可登录
     */
    @PutMapping("/member/freeze")
    public BaseResponse freezeMember(@RequestParam String adminAddress,
        @RequestParam String address) {
        if (!teamService.checkMemberIsSuperAdmin(adminAddress)) {
            throw new BizException(CodeEnums.NOT_THE_ADMIN);
        }
        memberService.freeze(address);
        return BaseResponse.success();
    }

    @PostMapping("/hire/all")
    public BaseResponse allHire(@RequestParam String adminAddress,
        @RequestBody HireSearchParams hireSearchParams) {
        if (!teamService.checkMemberIsSuperAdmin(adminAddress)) {
            throw new BizException(CodeEnums.NOT_THE_ADMIN);
        }
        return BaseResponse.successWithData(hireService.getAllHire(hireSearchParams));
    }

    @DeleteMapping("/hire/delete")
    public BaseResponse deleteHire(@RequestParam String adminAddress, @RequestParam Long hireId) {
        if (!teamService.checkMemberIsSuperAdmin(adminAddress)) {
            throw new BizException(CodeEnums.NOT_THE_ADMIN);
        }
        hireService.deleteHire(hireId);
        return BaseResponse.success();
    }

    @PostMapping("/bounty/all")
    public BaseResponse allBounty(@RequestParam String adminAddress,
        @RequestParam(defaultValue = "1") Integer pageNumber,
        @RequestParam(defaultValue = "10") Integer pageSize,
        @RequestBody BountySearchVo bountySearchParams) {
        if (!teamService.checkMemberIsSuperAdmin(adminAddress)) {
            throw new BizException(CodeEnums.NOT_THE_ADMIN);
        }
        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize, Sort.by(Sort.Direction.DESC, "createTime"));
        Page<BountyVo> page = bountyService.search(bountySearchParams, pageable);
        return BaseResponse.successWithData(page);
    }
}
