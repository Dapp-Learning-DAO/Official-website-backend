package com.dl.officialsite.activity.config;

import com.dl.officialsite.activity.constant.TaskTypeEnum;
import com.dl.officialsite.activity.model.MemberTaskStatus;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Data
@EqualsAndHashCode(callSuper = false)
@ToString
@Configuration
@ConfigurationProperties(prefix = "activity", ignoreInvalidFields = true)
public class ActivityConfig {
    private String name;
    private Map<TaskTypeEnum, List<Task>> taskMap;

    private static long taskCount;

    @PostConstruct
    public void init() {
        taskCount = Optional.of(taskMap).orElse(Collections.emptyMap()).values().stream().
            map(Collection::size).mapToInt(Integer::intValue).sum();

        log.info("Init ActivityConfig:[{}], taskCount:[{}]", this.toString(), taskCount);
    }

    public List<MemberTaskStatus> fetchTaskList() {
        return taskMap.entrySet().stream().flatMap(entry ->
            entry.getValue().stream().map(task -> new MemberTaskStatus(entry.getKey(), task.getName(), task.getTarget(),
                task.getTargetUrl()))
        ).collect(Collectors.toList());
    }

}