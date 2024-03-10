package com.dl.officialsite.bot;

import com.dl.officialsite.bot.constant.BotEnum;
import com.dl.officialsite.bot.constant.ChannelEnum;
import com.dl.officialsite.bot.discord.DiscordBotService;
import com.dl.officialsite.bot.event.EventNotify;
import com.dl.officialsite.bot.event.NotifyMessageFactory;
import com.dl.officialsite.bot.telegram.TelegramBotService;
import com.dl.officialsite.common.base.BaseResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/dev/test")
@Profile({"dev", "local"})
public class BotTestController {
    @Autowired
    private ApplicationContext applicationContext;
    @Autowired
    private DiscordBotService discordBotService;
    @Autowired
    private TelegramBotService telegramBotService;

    @GetMapping("/{botType}/send-event")
    public BaseResponse sendEvent(@PathVariable String botType,
                                  @RequestParam String message,
                                  @RequestParam(defaultValue = "", required = false) String channelOrTopicName,
                                  @RequestParam(defaultValue = "false", required = false) boolean testAll) {
        if (this.discordBotService == null) {
            return BaseResponse.failWithReason("999999", "Discord is not configured.");
        }
        BotEnum bot = BotEnum.valueOf(StringUtils.upperCase(botType));
        ChannelEnum channelEnum = ChannelEnum.of(channelOrTopicName);
        if (bot == null) {
            return BaseResponse.failWithReason("999999", "Bot type is invalid, should be in " + StringUtils.join(BotEnum.values(), ","));
        }

        if (testAll) {
            this.applicationContext.publishEvent(new EventNotify(EventNotify.class, bot, ChannelEnum.GENERAL,
                NotifyMessageFactory.welcomeUserMessage("Test from controller")));

            this.applicationContext.publishEvent(new EventNotify(EventNotify.class, bot, ChannelEnum.HIRING,
                NotifyMessageFactory.bountyMessage("Test from controller", "Bounty Title")));

            this.applicationContext.publishEvent(new EventNotify(EventNotify.class, bot, ChannelEnum.SHARING,
                NotifyMessageFactory.sharingMessage("Create or Edit", "Test from controller", "Sharing Title", "Date")));

            this.applicationContext.publishEvent(new EventNotify(EventNotify.class, bot, ChannelEnum.BUILDER,
                NotifyMessageFactory.joinTeamMessage("Test from controller", "Join test team")));

            this.applicationContext.publishEvent(new EventNotify(EventNotify.class, bot, ChannelEnum.BUILDER,
            NotifyMessageFactory.exitTeamMessage("Test from controller", "Exit test team")));
        }else{
            this.applicationContext.publishEvent(new EventNotify(EventNotify.class, bot, channelEnum,
                NotifyMessageFactory.testMessage(message)));
        }
        return BaseResponse.success();
    }

}