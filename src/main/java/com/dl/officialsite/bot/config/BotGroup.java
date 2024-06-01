package com.dl.officialsite.bot.config;

import com.dl.officialsite.bot.constant.GroupNameEnum;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class BotGroup{
    private String groupId;
    private GroupNameEnum groupName;
    private List<BotTopic> topicOrChannelList = new ArrayList<>();
}
