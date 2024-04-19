package com.dl.officialsite.activity;

import com.dl.officialsite.activity.constant.TaskTypeEnum;
import com.dl.officialsite.activity.service.MemberTaskService;
import com.dl.officialsite.common.base.BaseResponse;
import com.dl.officialsite.member.Member;
import com.dl.officialsite.member.MemberRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.util.Optional;

/**
 * 活动模块
 */
@RestController
@RequestMapping("/activity")
@Slf4j
public class ActivityController {
    @Autowired
    private MemberTaskService memberTaskService;
    private MemberRepository memberRepository;

    /**
     * 获取活动中用户的状态
     */
    @GetMapping("/status")
    public BaseResponse fetchMemberTasksStatus(@RequestParam String address) {
        Optional<Member> memberOptional = memberRepository.findByAddress(address);
        return memberOptional.map(member -> BaseResponse.successWithData(memberTaskService.getMemberTasksStatusByAddress(address, member)))
            .orElseGet(() -> BaseResponse.failWithReason("1001", "no user found")); // 用户需要注册
    }

    /**
     * 检查用户是否完成任务
     */
    @GetMapping("/check")
    public BaseResponse checkTask(@NotNull @RequestParam String address, @NotNull @RequestParam("taskType") TaskTypeEnum taskType,
                                  @NotNull @RequestParam("target") String target) {
        Optional<Member> memberOptional = memberRepository.findByAddress(address);
        return memberOptional.map(member -> memberTaskService.checkStatus(address, taskType, target))
            .orElseGet(() -> BaseResponse.failWithReason("1001", "no user found")); // 用户需要注册
    }

}