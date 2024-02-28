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

import static com.dl.officialsite.bot.telegram.TelegramMarkdownV2Escaper.escapeText;


@Slf4j
public class TelegramBotUtil {

    public static Pair<Boolean, String> sendMarkdownV2MessageToGeneral(TelegramBot tgBot, Long chatId, String text) {
        return sendMarkdownV2MessageToTopic(tgBot, chatId, text, null);
    }

    public static Pair<Boolean, String> sendMarkdownV2MessageToTopic(TelegramBot tgBot, Long chatId, String text, Integer messageThreadId) {
        return sendMessageToTopic(tgBot, chatId, text, ParseMode.MarkdownV2, messageThreadId);
    }


    public static Pair<Boolean, String> sendMessageToTopic(TelegramBot tgBot, Long chatId, String text, ParseMode parseMode,
                                                           Integer messageThreadId) {
        Assert.notNull(tgBot, "Telegram bot is not initialized.");

        SendMessage request = new SendMessage(chatId, escapeText(text)).parseMode(parseMode).replyMarkup(new ForceReply());
        if (messageThreadId != null && messageThreadId != 0) {
            request.messageThreadId(messageThreadId);
        }

        SendResponse sendResponse = tgBot.execute(request);
        boolean result = sendResponse.isOk();
        String message = Optional.ofNullable(sendResponse.description()).orElse("");

        log.info("Send message:[{}] to topic[{}] of group[{}], result:[{}:{}]", text, messageThreadId, chatId, result, message);
        return Pair.of(result, message);
    }
}