package com.dl.officialsite.bot.event;

import com.dl.officialsite.bot.constant.BotEnum;
import com.dl.officialsite.bot.constant.ChannelEnum;
import com.dl.officialsite.bot.discord.DiscordBotService;
import com.dl.officialsite.bot.telegram.TelegramBotService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Optional;


@Slf4j
@Component
public class NotifyEventListener implements ApplicationListener<EventNotify> {
    @Autowired(required = false)
    private TelegramBotService telegramBotService;
    @Autowired(required = false)
    private DiscordBotService discordBotService;

    public static String socialMedia = "website: https://dapplearning.org/\n" +
        "github: https://github.com/Dapp-Learning-DAO/Dapp-Learning-DAO\n" +
        "twitter: https://twitter.com/Dapp_Learning\n" +
        "youtube: https://www.youtube.com/@DappLearning\n";

    @Override
    public void onApplicationEvent(EventNotify event) {
        String sourceName = event.getSource().toString();
        log.info("event class：{}, message:{}", sourceName, event);

        if (sourceName.contains("member")) {
            Optional.of(event.getBotEnum())
                .map(botEnum -> event.getBotEnum() == BotEnum.ALL ? Arrays.asList(BotEnum.values()) : Arrays.asList(botEnum))
                .ifPresent(
                    botList -> botList.forEach(bot -> sendMessage(bot, event.getChannelEnum(), event.getMsg() + "\n" + socialMedia)));
        }
    }

    private void sendMessage(BotEnum botEnum, ChannelEnum channelEnum, String message) {
        switch (botEnum) {
            case DISCORD :
                Optional.ofNullable(discordBotService).ifPresent(service ->
                    discordBotService.sendMessageToChannel(message,  channelEnum.getChannelName()));
                break;
            case TELEGRAM :
                Optional.ofNullable(telegramBotService).ifPresent(service ->
                    telegramBotService.sendMarkdownV2MessageToTopic(message, channelEnum.getChannelName()));
                break;
            default:
                break;
        }
    }
}
