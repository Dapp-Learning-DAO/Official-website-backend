package com.dl.officialsite.activity;

import com.dl.officialsite.activity.config.ActivityConfig;
import com.dl.officialsite.activity.config.Task;
import com.dl.officialsite.activity.constant.TaskTypeEnum;
import com.dl.officialsite.activity.service.MemberTaskService;
import com.dl.officialsite.common.base.BaseResponse;
import com.dl.officialsite.common.utils.HttpSessionUtils;
import com.dl.officialsite.login.model.SessionUserInfo;
import com.dl.officialsite.member.Member;
import com.dl.officialsite.member.MemberRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
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
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private ActivityConfig activityConfig;

    /**
     * 获取活动中用户的状态
     */
    @GetMapping("/status")
    public BaseResponse fetchMemberTasksStatus(@RequestParam(required = false) String addressForTesting, HttpSession session) {
        SessionUserInfo sessionUserInfo = HttpSessionUtils.getMember(session);
        final String address = sessionUserInfo != null ? sessionUserInfo.getAddress() : addressForTesting;

        Optional<Member> memberOptional = memberRepository.findByAddress(address);
        return BaseResponse.successWithData(memberTaskService.getMemberTasksStatusByAddress(address, memberOptional));
    }

    /**
     * 检查用户是否完成任务
     */
    @PostMapping("/check")
    public BaseResponse checkTask(@NotNull @RequestParam("taskType") TaskTypeEnum taskType, @NotNull @RequestParam("target") String target,
                                  @RequestParam(required = false) String addressForTesting, HttpSession session) {
        if (taskType == null) {
            return BaseResponse.failWithReason("1201", "Parameter [taskType] should not be null.");
        }
        if (StringUtils.isBlank(target)) {
            return BaseResponse.failWithReason("1201", "Parameter [target] should not be null.");
        }
        Optional<Task> task = activityConfig.findTask(taskType, target);
        if (!task.isPresent()) {
            return BaseResponse.failWithReason("1201", String.format("Task [%s:%s] is not defined.", taskType, target));
        }

        SessionUserInfo sessionUserInfo = HttpSessionUtils.getMember(session);
        final String address = sessionUserInfo != null ? sessionUserInfo.getAddress() : addressForTesting;

        Optional<Member> memberOptional = memberRepository.findByAddress(address);
        return memberOptional.map(member -> memberTaskService.checkStatus(member, address, taskType, task.get()))
            .orElseGet(() -> BaseResponse.failWithReason("1001", "no user found")); // 用户需要注册
    }

}