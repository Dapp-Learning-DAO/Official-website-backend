package com.dl.officialsite.activity.constant;

import lombok.Getter;

@Getter
public enum TaskTypeEnum {
    GIT_HUB("GitHub"),
    TELEGRAM("Telegram"),
    DISCORD("Discord");

    private String value;

    private TaskTypeEnum(String value) {
        this.value = value;
    }
}