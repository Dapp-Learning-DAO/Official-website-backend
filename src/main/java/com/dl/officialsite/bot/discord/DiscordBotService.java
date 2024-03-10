package com.dl.officialsite.bot.discord;

import com.dl.officialsite.bot.BaseBotService;
import com.dl.officialsite.bot.constant.ChannelEnum;
import com.dl.officialsite.bot.constant.GroupNameEnum;
import com.dl.officialsite.bot.model.Message;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Service;


@Service
public class DiscordBotService extends BaseBotService<DiscordBotConfig> {

    @Override
    public Pair<Boolean, String> sendMessage(GroupNameEnum groupNameEnum, ChannelEnum channelEnum, Message msg) {
        Pair<String, String> channelIdByName = this.getBotConfig().getGroupIdAndChannelIdByName(groupNameEnum, channelEnum);
        return DiscordBotUtil.sendMessageToChannel(this.getBotConfig().getBot(), channelIdByName.getValue(), msg);
    }
}