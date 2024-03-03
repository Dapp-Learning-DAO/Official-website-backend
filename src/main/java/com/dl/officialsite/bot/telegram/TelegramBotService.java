package com.dl.officialsite.bot.telegram;

import org.apache.commons.lang3.tuple.Pair;


public class TelegramBotService {
    private TelegramBotConfig telegramBotConfig;

    public TelegramBotService(TelegramBotConfig telegramBotConfig) {
        this.telegramBotConfig = telegramBotConfig;
    }

    public Pair<Boolean, String> sendMarkdownV2MessageToGeneral(String text) {
        return this.sendMarkdownV2MessageToTopic(text, null);
    }

    public Pair<Boolean, String> sendMarkdownV2MessageToTopic(String text, String topicName) {
        Integer messageThreadId = this.telegramBotConfig.getFirstMessageThreadIdByTopicName(topicName);
        return TelegramBotUtil.sendMarkdownV2MessageToTopic(telegramBotConfig.getTgBot(), telegramBotConfig.getGroupId(), text,
            messageThreadId);
    }
}