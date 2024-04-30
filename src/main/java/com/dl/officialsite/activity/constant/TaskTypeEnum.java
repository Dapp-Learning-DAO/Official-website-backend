package com.dl.officialsite.activity.constant;

import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

@Getter
public enum TaskTypeEnum {
    GIT_HUB("GitHub"),
    TELEGRAM("Telegram"),
    DISCORD("Discord");

    private String value;

    private TaskTypeEnum(String value) {
        this.value = value;
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