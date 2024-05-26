package com.dl.officialsite.activity.config;

import com.dl.officialsite.activity.constant.TaskTypeEnum;
import com.dl.officialsite.activity.model.MemberTaskStatus;
import com.dl.officialsite.config.bean.Configurable;
import com.dl.officialsite.config.bean.Refreshable;
import com.dl.officialsite.config.constant.ConfigEnum;
import com.dl.officialsite.config.service.ServerConfigCacheService;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Data
@EqualsAndHashCode(callSuper = false)
@ToString
@Service
@Slf4j
public class ActivityConfig implements Refreshable {
    private static long taskCount;
    private AnnualActivityConfig annualActivityConfig;

    @Autowired
    private ServerConfigCacheService serverConfigCacheService;

    @EventListener(ApplicationReadyEvent.class)
    public void startUpOrRefresh() {
        annualActivityConfig = this.serverConfigCacheService.get(ConfigEnum.ANNUAL_ACTIVITY_3ND, AnnualActivityConfig.class);

        taskCount = Optional.ofNullable(annualActivityConfig)
            .map(AnnualActivityConfig::getTaskMap).orElse(Collections.emptyMap())
            .values().stream()
            .map(Collection::size).mapToInt(Integer::intValue).sum();

        log.info("Init ActivityConfig:[{}], taskCount:[{}]", this, taskCount);
    }

    public Optional<Task> findTask(TaskTypeEnum taskType, String target) {
        return Optional.ofNullable(annualActivityConfig)
            .map(AnnualActivityConfig::getTaskMap)
            .map(map -> map.get(taskType))
            .flatMap(list -> list.stream().filter(task -> StringUtils.equalsIgnoreCase(task.getTarget(), target))
                .reduce((a, b) -> {
                    throw new IllegalStateException("Shouldn't be multiple records");
                }));
    }

    public List<MemberTaskStatus> fetchMemberTaskStatusList() {
        Map<TaskTypeEnum, List<Task>> taskTypeEnumListMap =
            Optional.ofNullable(annualActivityConfig).map(AnnualActivityConfig::getTaskMap).orElse(Collections.emptyMap());

        return taskTypeEnumListMap.entrySet().stream().flatMap(entry ->
            entry.getValue().stream().map(task -> new MemberTaskStatus(entry.getKey(), task.getName(), task.getTarget(),
                task.getTargetUrl(), task.getDescription()))
        ).collect(Collectors.toList());
    }

    public String getName() {
        return Optional.ofNullable(annualActivityConfig).map(AnnualActivityConfig::getName).orElse("");
    }
}

@Data
class AnnualActivityConfig implements Configurable {
    private String name;
    private Map<TaskTypeEnum, List<Task>> taskMap;
}
