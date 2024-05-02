package com.dl.officialsite.bot.constant;

import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
public enum ChannelEnum {
    GENERAL("general-chat", "general"),
    HIRING("hiring", "job", "hiring-and-applying", "applying"),
    SHARING("sharing", "sharing_team"),
    BUILDER("builder"),
    TRANSLATION("translation"),
    GIT_HUB("github"),
    COLLABLAND_JOIN("collabland-join"),
    WELCOME("welcome"),
    SELF_INTRO("self-intro"),
    NEWS("news"),
    ;

    ChannelEnum(String... channelNameKeyWords) {
        this.channelNameKeyWordsSet = Arrays.stream(channelNameKeyWords).collect(Collectors.toSet());
    }

    public static ChannelEnum of(String channelNameOrTopicName) {
        return Arrays.stream(ChannelEnum.values())
            .filter(channelEnum -> channelEnum.channelNameKeyWordsSet.stream()
                .anyMatch(value -> StringUtils.containsIgnoreCase(channelNameOrTopicName, value)))
            .findFirst().orElse(null);
    }

    private final Set<String> channelNameKeyWordsSet;
}
