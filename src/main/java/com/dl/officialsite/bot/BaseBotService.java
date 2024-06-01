package com.dl.officialsite.bot;

import com.dl.officialsite.bot.constant.ChannelEnum;
import com.dl.officialsite.bot.constant.GroupNameEnum;
import com.dl.officialsite.bot.model.Message;
import org.apache.commons.lang3.tuple.Pair;


public interface  BaseBotService {

    boolean isBotInitialized();

    Pair<Boolean, String> sendMessage(GroupNameEnum groupNameEnum, ChannelEnum channelEnum, Message text);

    boolean isUserInChannel(String channelId, String userId);

}