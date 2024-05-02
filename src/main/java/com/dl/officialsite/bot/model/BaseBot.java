package com.dl.officialsite.bot.model;

import com.dl.officialsite.bot.config.BotServerConfig;
import com.dl.officialsite.bot.config.BotTopic;
import com.dl.officialsite.bot.constant.BotEnum;
import com.dl.officialsite.bot.constant.ChannelEnum;
import com.dl.officialsite.bot.constant.GroupNameEnum;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;

@Slf4j
@Getter
public abstract class BaseBot<B> {
    protected BotEnum botEnum;
    protected BotServerConfig botServerConfig;

    protected B bot;

    public abstract void initBot();

    public void init() {
        log.info("Start to init bot:[{}] ...", botEnum);

        if (StringUtils.containsIgnoreCase(botServerConfig.getBotToken(), "BOT_TOKEN")) {
            log.warn("Bot:[{}] is not configured properly!!!", botEnum);
        } else {
            initBot();
        }
        log.info("The {} Bot is initialized and ready with detail:[{}]!!!", botEnum, this.toString());
    }

    public Pair<String, String> getGroupIdAndChannelIdByName(GroupNameEnum groupNameEnum, ChannelEnum channelEnum) {
        final GroupNameEnum groupName = groupNameEnum == null ? GroupNameEnum.DAPP_LEARNING : groupNameEnum;
        final ChannelEnum channel = channelEnum == null ? ChannelEnum.GENERAL : channelEnum;

        return botServerConfig.getGroupList().stream().filter(group -> group.getGroupName() == groupName)
            .map(group -> Pair.of(group.getGroupId(),
                group.getTopicOrChannelList().stream().filter(topic -> topic.getName() == channel)
                    .map(BotTopic::getThreadOrTopicId)
                    .findFirst()
                    .orElse(null)))
            .findFirst()
            .orElseThrow(
                () -> new RuntimeException(String.format("Cannot find group:[%s] of [%s]", groupName, botEnum.name())));
    }
}
