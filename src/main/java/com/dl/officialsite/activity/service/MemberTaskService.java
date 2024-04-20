package com.dl.officialsite.activity.service;

import com.dl.officialsite.activity.bean.MemberTaskRecord;
import com.dl.officialsite.activity.bean.MemberTaskRecordRepository;
import com.dl.officialsite.activity.config.ActivityConfig;
import com.dl.officialsite.activity.config.Task;
import com.dl.officialsite.activity.constant.TaskTypeEnum;
import com.dl.officialsite.activity.model.MemberTaskStatus;
import com.dl.officialsite.bot.discord.DiscordBotService;
import com.dl.officialsite.bot.telegram.TelegramBotService;
import com.dl.officialsite.common.base.BaseResponse;
import com.dl.officialsite.member.Member;
import com.dl.officialsite.member.MemberRepository;
import com.dl.officialsite.oauth2.AccessTokenCacheService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class MemberTaskService {
    private static final String TOKEN = "xxxxxxxxxxxxx"; // 替换成你的 GitHub Token
    private static final String API_URL = "https://api.github.com/user/starred/%s";

    @Autowired
    private MemberTaskRecordRepository memberTaskRecordRepository;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private ActivityConfig activityConfig;
    @Autowired
    private DiscordBotService discordBotService;
    @Autowired
    private TelegramBotService telegramBotService;
    @Autowired
    private RestTemplate restTemplate;


    public BaseResponse checkStatus(Member member, String address, TaskTypeEnum taskType, Task task) {
        List<MemberTaskRecord> finishRecord =
            memberTaskRecordRepository.findByAddressAndActivityNameAndTaskTypeAndTarget(address, activityConfig.getName(), taskType.getValue(),
                task.getTarget());
        if (CollectionUtils.size(finishRecord) == 1 && finishRecord.get(0).isFinished()) {
            return BaseResponse.successWithData(true);
        }

        boolean result = false;
        switch (taskType) {
            case DISCORD:
                if (StringUtils.isBlank(member.getDiscordId())) {
                    return BaseResponse.failWithReason("1202", "Discord id is null.");
                }
                result = this.discordBotService.isUserInChannel(task.getTarget(), member.getDiscordId());
                break;
            case GIT_HUB:
                // GitHub 需要判断 session 是否有 access_token
                if (StringUtils.isBlank(member.getGithubId())) {
                    return BaseResponse.failWithReason("1202", "GitHub id is null.");
                }
                String gitHubAccessToken = AccessTokenCacheService.getGitHubAccessToken(member.getGithubId());
                if (StringUtils.isBlank(gitHubAccessToken)) {
                    return BaseResponse.failWithReason("1202", "GitHub access token is null.");
                }
                result = githubCheckIfUserStarredRepo(gitHubAccessToken, task.getTarget());
                break;
            case TELEGRAM:
                if (StringUtils.isBlank(member.getTelegramUserId())) {
                    return BaseResponse.failWithReason("1202", "Telegram id is null.");
                }
                result = this.telegramBotService.isUserInChannel(task.getTarget(), member.getTelegramUserId());
                break;
            default:
                throw new RuntimeException("Unknown task type: " + taskType);
        }
        if (result) {
            MemberTaskRecord memberTaskRecord = new MemberTaskRecord();
            memberTaskRecord.setAddress(address);
            memberTaskRecord.setActivityName(activityConfig.getName());
            memberTaskRecord.setFinishTime(Instant.now().getEpochSecond());
            memberTaskRecord.setFinished(result);
            memberTaskRecord.setTarget(task.getTarget());
            memberTaskRecord.setTaskType(taskType);
            memberTaskRecordRepository.save(memberTaskRecord);
        }
        return BaseResponse.successWithData(result);
    }

    public List<MemberTaskStatus> getMemberTasksStatusByAddress(String address, Optional<Member> member) {
        List<MemberTaskRecord> memberTaskRecordList =
            memberTaskRecordRepository.findActivityRecordsByAddress(activityConfig.getName(), address);

        // all the tasks
        List<MemberTaskStatus> memberTaskStatuses = this.activityConfig.fetchMemberTaskStatusList();
        memberTaskStatuses.forEach(status -> {
            Optional<MemberTaskRecord> finishRecord = filter(memberTaskRecordList, status.getTaskType(), status.getTarget());
            if (finishRecord.isPresent() && finishRecord.get().isFinished()) {
                status.setFinished(true);
            } else if (member.isPresent()) { // 用户注册了，可以检查任务完成情况
                switch (status.getTaskType()) {
                    case DISCORD:
                        status.setRequiredAuthorization(StringUtils.isBlank(member.get().getDiscordId()));
                        break;
                    case GIT_HUB:
                        // GitHub 判断 session 是否有 access_token
                        status.setRequiredAuthorization(
                            StringUtils.isBlank(AccessTokenCacheService.getGitHubAccessToken(member.get().getGithubId())));
                        break;
                    case TELEGRAM:
                        status.setRequiredAuthorization(StringUtils.isBlank(member.get().getTelegramUserId()));
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

    private boolean githubCheckIfUserStarredRepo(String accessToken, String targetRepo) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", "application/vnd.github+json");
        headers.set("Authorization", "Bearer " + accessToken);
        headers.set("X-GitHub-Api-Version", "2022-11-28");

        HttpEntity<String> entity = new HttpEntity<>(headers);
        try {
            targetRepo = StringUtils.removeStart(targetRepo, "/");
            ResponseEntity<String> response = restTemplate.exchange(String.format(API_URL, targetRepo), HttpMethod.GET, entity,
                String.class);
            // 检查响应状态码
            return response.getStatusCode() == HttpStatus.NO_CONTENT; // 204 NO_CONTENT 表示用户已标星
        } catch (HttpClientErrorException ex) {
            ex.printStackTrace();
            return false;
        }

    }
}