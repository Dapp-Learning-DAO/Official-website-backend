package com.dl.officialsite.bot.model;

import com.dl.officialsite.bot.constant.ChannelEnum;
import lombok.Data;

@Data
public class BotTopic<T> {
    private ChannelEnum name;
    private T threadOrTopicId;

    public static <T> BotTopic<T> build(ChannelEnum channelEnum, T threadOrTopicId){
        BotTopic botTopic = new BotTopic();
        botTopic.setName(channelEnum);
        botTopic.setThreadOrTopicId(threadOrTopicId);
        return botTopic;
    }
}