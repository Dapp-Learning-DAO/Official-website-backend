package com.dl.officialsite.activity.service;

import com.dl.officialsite.activity.bean.MemberTaskRecord;
import com.dl.officialsite.activity.bean.MemberTaskRecordRepository;
import com.dl.officialsite.activity.config.ActivityConfig;
import com.dl.officialsite.activity.constant.TaskTypeEnum;
import com.dl.officialsite.activity.model.MemberTaskStatus;
import com.dl.officialsite.common.base.BaseResponse;
import com.dl.officialsite.member.Member;
import com.dl.officialsite.member.MemberRepository;
import com.dl.officialsite.oauth2.AccessTokenCacheService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class MemberTaskService {
    private MemberTaskRecordRepository memberTaskRecordRepository;
    private MemberRepository memberRepository;

    private ActivityConfig activityConfig;

    public BaseResponse checkStatus(String address, TaskTypeEnum taskType, String target) {
        Optional<MemberTaskRecord> finishRecord =
            memberTaskRecordRepository.findOne(activityConfig.getName(), address, taskType, target);
        if (finishRecord.isPresent() && finishRecord.get().isFinished()) {
            return BaseResponse.success();
        }

        // TODO.
        switch (taskType) {
            case DISCORD:
                return null;
            case GIT_HUB:
                // GitHub 需要判断 session 是否有 access_token
                return null;
            case TELEGRAM:
                return null;
            default:
                throw new RuntimeException("Unknown task type: " + taskType);
        }
    }

    public List<MemberTaskStatus> getMemberTasksStatusByAddress(String address, Member member) {

        List<MemberTaskRecord> memberTaskRecordList =
            memberTaskRecordRepository.findActivityRecordsByAddress(activityConfig.getName(), address);

        // all the tasks
        List<MemberTaskStatus> memberTaskStatuses = this.activityConfig.fetchTaskList();
        memberTaskStatuses.forEach(status -> {
            Optional<MemberTaskRecord> finishRecord = filter(memberTaskRecordList, status.getTaskType(), status.getTarget());
            if (finishRecord.isPresent() && finishRecord.get().isFinished()) {
                status.setFinished(true);
            } else {
                // 任务未完成
                switch (status.getTaskType()) {
                    case DISCORD:
                        status.setRequiredAuthorization(StringUtils.isBlank(member.getDiscordId()));
                        break;
                    case GIT_HUB:
                        // GitHub 需要判断 session 是否有 access_token
                        status.setRequiredAuthorization(
                            StringUtils.isBlank(AccessTokenCacheService.getGitHubAccessToken(member.getGithubId())));
                        break;
                    case TELEGRAM:
                        status.setRequiredAuthorization(StringUtils.isBlank(member.getTelegramUserId()));
                        break;
                }
            }
        });
        return memberTaskStatuses;
    }

    private Optional<MemberTaskRecord> filter(List<MemberTaskRecord> memberTaskRecordList, TaskTypeEnum taskTypeEnum, String target) {
        return memberTaskRecordList.stream()
            .filter(record -> taskTypeEnum.equals(record.getTaskType()))
            .filter(record -> target.equals(record.getTarget()))
            .reduce((a, b) -> {
                throw new IllegalStateException("Shouldn't be multiple records");
            });
    }
}