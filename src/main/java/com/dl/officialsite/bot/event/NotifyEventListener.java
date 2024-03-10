package com.dl.officialsite.bot.event;

import com.dl.officialsite.bot.constant.BotEnum;
import com.dl.officialsite.bot.constant.ChannelEnum;
import com.dl.officialsite.bot.constant.GroupNameEnum;
import com.dl.officialsite.bot.discord.DiscordBotService;
import com.dl.officialsite.bot.model.Message;
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
    @Autowired
    private TelegramBotService telegramBotService;
    @Autowired
    private DiscordBotService discordBotService;

    @Override
    public void onApplicationEvent(EventNotify event) {
        String sourceName = event.getSource().toString();
        log.info("event class：{}, message:{}", sourceName, event);

        Optional.of(event.getBotEnum())
            .map(botEnum -> event.getBotEnum() == BotEnum.ALL ? Arrays.asList(BotEnum.values()) : Arrays.asList(botEnum))
            .ifPresent(
                botList -> botList.forEach(bot -> sendMessage(bot, event.getGroupEnum(), event.getChannelEnum(), event.getMsg())));
    }

    private void sendMessage(BotEnum botEnum, GroupNameEnum group, ChannelEnum channelEnum, Message message) {
        switch (botEnum) {
            case DISCORD:
                Optional.ofNullable(discordBotService.getBotConfig().getBot()).ifPresent(service ->
                    discordBotService.sendMessage(group, channelEnum, message));
                break;
            case TELEGRAM:
                Optional.ofNullable(telegramBotService.getBotConfig().getBot()).ifPresent(service ->
                    telegramBotService.sendMessage(group, channelEnum, message));
                break;
            default:
                break;
        }
    }
}
