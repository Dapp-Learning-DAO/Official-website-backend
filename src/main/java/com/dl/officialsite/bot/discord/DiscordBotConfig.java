package com.dl.officialsite.bot.discord;

import com.dl.officialsite.bot.constant.BotEnum;
import com.dl.officialsite.bot.constant.ChannelEnum;
import com.dl.officialsite.bot.model.BaseBotConfig;
import com.dl.officialsite.bot.model.BotTopic;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Data
@ConfigurationProperties(prefix = "bot.discord", ignoreInvalidFields = true)
public class DiscordBotConfig extends BaseBotConfig<JDA, String, String> {
    public DiscordBotConfig() {
        super(BotEnum.DISCORD);
    }

    @Override
    public JDA initBot() throws InterruptedException {
        JDA jda = JDABuilder.createDefault(botToken).build().awaitReady();
        log.info("Init Guild(server) and threads map...");
        groupList.forEach(guildConfig -> {
            Guild guild = jda.getGuildById(guildConfig.getGroupId());
            if (guild == null){
                log.error("Cannot find Guild for id:[{}]", guildConfig.getGroupId());
                return;
            }
            List<TextChannel> textChannels = guild.getTextChannels();
            if (CollectionUtils.isEmpty(textChannels)){
                log.error("Cannot find any text channel for id:[{}]", guildConfig.getGroupId());
                return;
            }
            List<BotTopic<String>> botTopicList = textChannels.stream()
                .map(textChannel -> BotTopic.build(ChannelEnum.of(textChannel.getName()), textChannel.getId()))
                .filter(botTopic -> Objects.nonNull(botTopic.getName()))
                .collect(Collectors.toList());
            guildConfig.setTopicOrChannelList(botTopicList);
        });
        return jda;
    }


    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("DiscordBotConfig{");
        sb.append("botToken='").append(botToken).append('\'');
        sb.append(", bot=").append(bot);
        sb.append(", timeoutInSeconds=").append(timeoutInSeconds);
        sb.append(", groupList=").append(groupList);
        sb.append('}');
        return sb.toString();
    }
}