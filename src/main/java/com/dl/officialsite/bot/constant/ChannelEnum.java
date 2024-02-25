package com.dl.officialsite.bot.constant;

import org.apache.commons.lang3.StringUtils;

public enum ChannelEnum {
    GENERAL("general"),
    HIRING("hiring"),
    SHARING("sharing"),
    ;

    ChannelEnum(String channelName) {
        this.channelName = channelName;
    }

    public static ChannelEnum of(String channelNameOrTopicName) {
        if (StringUtils.isNotBlank(channelNameOrTopicName)) {
            for (ChannelEnum value : ChannelEnum.values()) {
                if (StringUtils.containsIgnoreCase(value.channelName, channelNameOrTopicName)) {
                    return value;
                }
            }
        }
        return ChannelEnum.GENERAL;
    }

    private String channelName;

    public String getChannelName() {
        return channelName;
    }
}
