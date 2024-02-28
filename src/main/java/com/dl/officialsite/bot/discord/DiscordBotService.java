package com.dl.officialsite.bot.discord;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;


public class DiscordBotService {
    private DiscordBotConfig discordBotConfig;

    public DiscordBotService(DiscordBotConfig discordBotConfig) {
        this.discordBotConfig = discordBotConfig;
    }

    public Pair<Boolean, String> sendMessageToChannel(String text, String channelName) {
        String channelId = this.discordBotConfig.getChannelIdByName(channelName);
        if (StringUtils.isBlank(channelId)){
            return Pair.of(false, String.format("Cannot find any channel by name:[%s]", channelName));
        }
        return DiscordBotUtil.sendMessageToChannel(discordBotConfig.getJdaBot(), channelId, text);
    }
}