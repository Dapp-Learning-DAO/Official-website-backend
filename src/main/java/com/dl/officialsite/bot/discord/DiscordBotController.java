package com.dl.officialsite.bot.discord;

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
@RequestMapping("/dev/test/discord")
@Profile({"dev", "local"})
public class DiscordBotController {
    @Autowired
    private DiscordBotService discordBotService;


    @GetMapping("/send")
    public BaseResponse add(
        @RequestParam String message,
        @RequestParam String channelName) {
        return BaseResponse.successWithData(this.discordBotService.sendMessageToChannel(message, channelName));
    }
}