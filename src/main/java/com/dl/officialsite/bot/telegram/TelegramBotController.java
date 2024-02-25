package com.dl.officialsite.bot.telegram;

import com.dl.officialsite.bot.constant.BotEnum;
import com.dl.officialsite.bot.constant.ChannelEnum;
import com.dl.officialsite.bot.event.EventNotify;
import com.dl.officialsite.common.base.BaseResponse;
import com.dl.officialsite.member.Member;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@RestController
@RequestMapping("/dev/test/telegram")
@Profile({"dev", "local"})
public class TelegramBotController {
    @Autowired(required = false)
    private ApplicationContext applicationContext;
    @Autowired(required = false)
    private TelegramBotService telegramBotService;

    @GetMapping("/send")
    public BaseResponse send(
        @RequestParam String message,
        @RequestParam(defaultValue = "", required = false) String topicName) {
        if (this.telegramBotService == null) {
            return BaseResponse.failWithReason("999999", "Telegram is not configured.");
        }
        return BaseResponse.successWithData(this.telegramBotService.sendMarkdownV2MessageToTopic(message, topicName));
    }

    @GetMapping("/send-event")
    public BaseResponse sendEvent(
        @RequestParam String message,
        @RequestParam(defaultValue = "", required = false) String topicName) {
        this.applicationContext.publishEvent(new EventNotify(Member.class, BotEnum.TELEGRAM, ChannelEnum.of(topicName), message));
        return BaseResponse.success();
    }
}