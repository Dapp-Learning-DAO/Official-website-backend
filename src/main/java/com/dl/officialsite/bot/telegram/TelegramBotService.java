package com.dl.officialsite.bot.telegram;

import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;


@Component
@EnableConfigurationProperties(TelegramBotConfig.class)
public class TelegramBotService {
    @Autowired
    private TelegramBotConfig telegramBotConfig;

    public Pair<Boolean, String> sendMarkdownV2MessageToGeneral(String text) {
        return this.sendMarkdownV2MessageToTopic(text, null);
    }

    public Pair<Boolean, String> sendMarkdownV2MessageToTopic(String text, String topicName) {
        Integer messageThreadId = this.telegramBotConfig.getFirstMessageThreadIdByTopicName(topicName);
        return TelegramBotUtil.sendMarkdownV2MessageToTopic(telegramBotConfig.getTgBot(), telegramBotConfig.getGroupId(), text,
            messageThreadId);
    }
}