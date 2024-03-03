package com.dl.officialsite.bot.discord;

import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;


@Slf4j
public class DiscordBotUtil {

    public static Pair<Boolean, String> sendMessageToChannel(JDA jda, String channelId, String text) {
        if (jda == null) {
            return Pair.of(false, "Discord bot is not initialized.");
        }
        if (StringUtils.isBlank(channelId)) {
            return Pair.of(false, "Channel id should not be empty.");
        }

        TextChannel textChannel = jda.getTextChannelById(channelId);
        if (!textChannel.canTalk()) {
            return Pair.of(false, "Cannot talk to channel.");
        }

        textChannel.sendMessage(text).queue();
        log.info("Send message:[{}] to channel[{}] success", text, channelId);
        return Pair.of(true, "Message send success.");
    }
}