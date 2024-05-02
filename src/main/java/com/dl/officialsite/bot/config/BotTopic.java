package com.dl.officialsite.bot.config;

import com.dl.officialsite.bot.constant.ChannelEnum;
import lombok.Data;

@Data
public class BotTopic {
    private ChannelEnum name;
    private String threadOrTopicId;

    public static BotTopic build(ChannelEnum channelEnum, String threadOrTopicId) {
        BotTopic botTopic = new BotTopic();
        botTopic.setName(channelEnum);
        botTopic.setThreadOrTopicId(threadOrTopicId);
        return botTopic;
    }
}
