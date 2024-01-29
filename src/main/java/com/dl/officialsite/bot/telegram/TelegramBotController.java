package com.dl.officialsite.bot.telegram;

import com.dl.officialsite.common.base.BaseResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
    @Autowired private TelegramBotService telegramBotService;


    @GetMapping("/send")
    public BaseResponse add(
        @RequestParam String message,
        @RequestParam(defaultValue = "", required = false) String topicName) {
        return BaseResponse.successWithData(this.telegramBotService.sendMarkdownV2MessageToTopic(message, topicName));
    }
}