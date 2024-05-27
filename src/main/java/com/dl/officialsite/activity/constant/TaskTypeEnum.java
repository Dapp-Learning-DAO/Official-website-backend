package com.dl.officialsite.activity.constant;

import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

@Getter
public enum TaskTypeEnum {
    GIT_HUB("GitHub"),
    TELEGRAM("Telegram"),
    DISCORD("Discord"),
    YOUTUBE("YouTube", true),
    TWITTER("Twitter", true);

    private String value;
    private boolean isClickTask = false;

    private TaskTypeEnum(String value) {
        this.value = value;
    }

    TaskTypeEnum(String value, boolean isClickTask) {
        this.value = value;
        this.isClickTask = isClickTask;
    }

    public static TaskTypeEnum fromValue(String value) {
        for (TaskTypeEnum taskTypeEnum : TaskTypeEnum.values()) {
            if (StringUtils.equalsIgnoreCase(taskTypeEnum.getValue(), value)) {
                return taskTypeEnum;
            }
        }
        return null;
    }
}