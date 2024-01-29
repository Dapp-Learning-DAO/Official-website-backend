package com.dl.officialsite.bot.telegram;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.request.ForceReply;
import com.pengrad.telegrambot.model.request.ParseMode;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.util.Assert;

import java.util.Optional;


@Slf4j
public class TelegramBotUtil {

    public static Pair<Boolean, String> sendMarkdownV2MessageToGeneral(TelegramBot tgBog, Long chatId, String text) {
        return sendMarkdownV2MessageToTopic(tgBog, chatId, text, null);
    }

    public static Pair<Boolean, String> sendMarkdownV2MessageToTopic(TelegramBot tgBog, Long chatId, String text, Integer messageThreadId) {
        return sendMessageToTopic(tgBog, chatId, text, ParseMode.MarkdownV2, messageThreadId);
    }


    public static Pair<Boolean, String> sendMessageToTopic(TelegramBot tgBog, Long chatId, String text, ParseMode parseMode,
                                                           Integer messageThreadId) {
        Assert.notNull(tgBog, "Telegram bot is not initialized.");

        SendMessage request = new SendMessage(chatId, text).parseMode(parseMode).replyMarkup(new ForceReply());
        if (messageThreadId != null && messageThreadId != 0) {
            request.messageThreadId(messageThreadId);
        }

        SendResponse sendResponse = tgBog.execute(request);
        boolean result = sendResponse.isOk();
        String message = Optional.ofNullable(sendResponse.description()).orElse("");

        log.info("Send message:[{}] to topic[{}] of group[{}], result:[{}:{}]", text, messageThreadId, chatId, result, message);
        return Pair.of(result, message);
    }
}