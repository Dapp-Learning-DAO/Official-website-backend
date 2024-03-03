package com.dl.officialsite.bot.model;

import com.dl.officialsite.bot.constant.GroupNameEnum;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class BotGroup<G, T> {
    private G groupId;
    private GroupNameEnum groupName;
    private List<BotTopic<T>> topicOrChannelList = new ArrayList<>();
}