/**
 *
 */


package com.dl.officialsite.config.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public enum ConfigEnum {
    ANNUAL_ACTIVITY_3ND("ANNUAL_ACTIVITY_3ND"),
    TELEGRAM_BOT_CONFIG("TELEGRAM_BOT_CONFIG"),
    DISCORD_BOT_CONFIG("DISCORD_BOT_CONFIG");

    private String configName;

}