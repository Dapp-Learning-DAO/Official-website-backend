package com.dl.officialsite.bot.discord;

import com.dl.officialsite.bot.constant.BotEnum;
import com.dl.officialsite.bot.constant.ChannelEnum;
import com.dl.officialsite.bot.constant.GroupNameEnum;
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
@RequestMapping("/dev/test/discord")
@Profile({"dev", "local"})
public class DiscordBotController {

    @Autowired(required = false)
    private ApplicationContext applicationContext;
    @Autowired(required = false)
    private DiscordBotService discordBotService;


    @GetMapping("/send")
    public BaseResponse send(
        @RequestParam String message,
        @RequestParam(defaultValue = "", required = false) String channelName) {
        if (this.discordBotService == null) {
            return BaseResponse.failWithReason("999999", "Discord is not configured.");
        }
        return BaseResponse.successWithData(this.discordBotService.sendMessageToChannel(GroupNameEnum.DAPP_LEARNING,
            ChannelEnum.of(channelName), message));
    }

    @GetMapping("/send-event")
    public BaseResponse sendEvent(
        @RequestParam String message,
        @RequestParam(defaultValue = "", required = false) String channelName) {
        this.applicationContext.publishEvent(new EventNotify(Member.class, BotEnum.DISCORD, ChannelEnum.of(channelName), message));
        return BaseResponse.success();
    }
}