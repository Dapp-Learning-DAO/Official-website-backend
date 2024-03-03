package com.dl.officialsite.bot.telegram;

import com.dl.officialsite.bot.constant.ChannelEnum;
import com.dl.officialsite.bot.constant.GroupNameEnum;
import org.apache.commons.lang3.tuple.Pair;


public class TelegramBotService {
    private TelegramBotConfig telegramBotConfig;

    public TelegramBotService(TelegramBotConfig telegramBotConfig) {
        this.telegramBotConfig = telegramBotConfig;
    }

    public Pair<Boolean, String> sendMarkdownV2MessageToTopic(GroupNameEnum groupNameEnum, ChannelEnum channelEnum, String text) {
        Pair<Long, Integer> groupIdAndChannelId = this.telegramBotConfig.getGroupIdAndChannelIdByName(groupNameEnum, channelEnum);
        return TelegramBotUtil.sendMarkdownV2MessageToTopic(telegramBotConfig.getBot(), groupIdAndChannelId.getKey(), text,
            groupIdAndChannelId.getValue());
    }
}