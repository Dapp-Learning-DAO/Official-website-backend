package com.dl.officialsite.bot.model;

import com.dl.officialsite.bot.constant.BotEnum;
import com.dl.officialsite.bot.constant.ChannelEnum;
import com.dl.officialsite.bot.constant.GroupNameEnum;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;

@Getter
@Setter
@Slf4j
public abstract class BaseBotConfig<B, G, T> {
    BotEnum botEnum;
    protected String botToken;

    protected B bot;
    protected int timeoutInSeconds = 10;

    public BaseBotConfig(BotEnum botEnum) {
        this.botEnum = botEnum;
    }

    protected List<BotGroup<G, T>> groupList = new ArrayList<>();

    public abstract B initBot() throws InterruptedException;

/*    @PostConstruct
    public void init() throws InterruptedException {
        log.info("Start to init bot:[{}] ...", botEnum);

        if (StringUtils.containsIgnoreCase(botToken, "BOT_TOKEN")) {
            log.warn("Bot:[{}] is not configured properly!!!", botEnum);
        } else {
            bot = initBot();
        }
        log.info("The {} Bot is initialized and ready with detail:[{}]!!!", botEnum, this.toString());
    }*/

    public Pair<G, T> getGroupIdAndChannelIdByName(GroupNameEnum groupNameEnum, ChannelEnum channelEnum) {
        final GroupNameEnum groupName = groupNameEnum == null ? GroupNameEnum.DAPP_LEARNING : groupNameEnum;
        final ChannelEnum channel = channelEnum == null ? ChannelEnum.GENERAL : channelEnum;

        return groupList.stream().filter(group -> group.getGroupName() == groupName)
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
