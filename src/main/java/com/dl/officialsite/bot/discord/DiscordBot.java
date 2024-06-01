package com.dl.officialsite.bot.discord;

import com.dl.officialsite.bot.config.BotServerConfig;
import com.dl.officialsite.bot.config.BotTopic;
import com.dl.officialsite.bot.constant.BotEnum;
import com.dl.officialsite.bot.constant.ChannelEnum;
import com.dl.officialsite.bot.model.BaseBot;
import com.dl.officialsite.config.bean.Refreshable;
import com.dl.officialsite.config.constant.ConfigEnum;
import com.dl.officialsite.config.service.ServerConfigCacheService;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Data
@EqualsAndHashCode(callSuper = false)
@Service
public class DiscordBot extends BaseBot<JDA> implements Refreshable {
    @Autowired
    private ServerConfigCacheService serverConfigCacheService;

    @EventListener(ApplicationReadyEvent.class)
    public void startUpOrRefresh() {
        this.botEnum = BotEnum.DISCORD;
        this.botServerConfig = serverConfigCacheService.get(ConfigEnum.DISCORD_BOT_CONFIG, BotServerConfig.class);
        Optional.ofNullable(this.botServerConfig).ifPresent(config -> {
            this.init();
        });
    }

    @Override
    public void initBot() {
        try {
            bot = JDABuilder.createDefault(this.botServerConfig.getBotToken()).build().awaitReady();
            log.info("Init Guild(server) and threads map...");
            this.botServerConfig.getGroupList().forEach(guildConfig -> {
                Guild guild = bot.getGuildById(guildConfig.getGroupId());
                if (guild == null) {
                    log.error("Cannot find Guild for id:[{}]", guildConfig.getGroupId());
                    return;
                }
                List<TextChannel> textChannels = guild.getTextChannels();
                if (CollectionUtils.isEmpty(textChannels)) {
                    log.error("Cannot find any text channel for id:[{}]", guildConfig.getGroupId());
                    return;
                }
                List<BotTopic> botTopicList = textChannels.stream()
                    .map(textChannel -> BotTopic.build(ChannelEnum.of(textChannel.getName()), textChannel.getId()))
                    .filter(botTopic -> Objects.nonNull(botTopic.getName()))
                    .collect(Collectors.toList());
                guildConfig.setTopicOrChannelList(botTopicList);
            });
        } catch (InterruptedException e) {
            log.error("Init Discord bot error.", e);
        }
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("botServerConfig", botServerConfig)
            .append("bot", bot)
            .toString();
    }
}