package com.dl.officialsite.bot.discord;

import com.dl.officialsite.bot.constant.ChannelEnum;
import com.dl.officialsite.bot.constant.GroupNameEnum;
import org.apache.commons.lang3.tuple.Pair;


public class DiscordBotService {
    private DiscordBotConfig discordBotConfig;

    public DiscordBotService(DiscordBotConfig discordBotConfig) {
        this.discordBotConfig = discordBotConfig;
    }

    public Pair<Boolean, String> sendMessageToChannel(GroupNameEnum groupNameEnum, ChannelEnum channelEnum, String text) {
        Pair<String, String> channelIdByName = this.discordBotConfig.getGroupIdAndChannelIdByName(groupNameEnum, channelEnum);
        return DiscordBotUtil.sendMessageToChannel(discordBotConfig.getBot(), channelIdByName.getValue(), text);
    }
}