package com.dl.officialsite.bot;

import com.dl.officialsite.bot.constant.ChannelEnum;
import com.dl.officialsite.bot.constant.GroupNameEnum;
import com.dl.officialsite.bot.model.BaseBotConfig;
import com.dl.officialsite.bot.model.Message;
import lombok.Data;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;


@Data
public abstract class BaseBotService<T extends BaseBotConfig> {
    @Autowired private T botConfig;

    public abstract Pair<Boolean, String> sendMessage(GroupNameEnum groupNameEnum, ChannelEnum channelEnum, Message text);

    public abstract boolean isUserInChannel(String channelId, String userId);

}