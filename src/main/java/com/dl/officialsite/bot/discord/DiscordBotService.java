package com.dl.officialsite.bot.discord;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;


@Component
@EnableConfigurationProperties(DiscordBotConfig.class)
public class DiscordBotService {

    @Autowired
    private DiscordBotConfig discordBotConfig;

    public Pair<Boolean, String> sendMessageToChannel(String text, String channelName) {
        String channelId = this.discordBotConfig.getChannelIdByName(channelName);
        if (StringUtils.isBlank(channelId)){
            return Pair.of(false, String.format("Cannot find any channel by name:[%s]", channelName));
        }
        return DiscordBotUtil.sendMessageToChannel(discordBotConfig.getJdaBot(), channelId, text);
    }
}