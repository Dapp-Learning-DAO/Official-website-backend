package com.dl.officialsite.bot.telegram;

import com.dl.officialsite.bot.BaseBotService;
import com.dl.officialsite.bot.constant.ChannelEnum;
import com.dl.officialsite.bot.constant.GroupNameEnum;
import com.dl.officialsite.bot.model.Message;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Service;


@Service
public class TelegramBotService extends BaseBotService<TelegramBotConfig> {
    @Override
    public Pair<Boolean, String> sendMessage(GroupNameEnum groupNameEnum, ChannelEnum channelEnum, Message msg) {
        Pair<Long, Integer> groupIdAndChannelId = this.getBotConfig().getGroupIdAndChannelIdByName(groupNameEnum, channelEnum);
        return TelegramBotUtil.sendMarkdownV2MessageToTopic(this.getBotConfig().getBot(), groupIdAndChannelId.getKey(), msg,
            groupIdAndChannelId.getValue());
    }
}