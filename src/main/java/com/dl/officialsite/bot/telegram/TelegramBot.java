package com.dl.officialsite.bot.telegram;

import com.dl.officialsite.bot.config.BotServerConfig;
import com.dl.officialsite.bot.constant.BotEnum;
import com.dl.officialsite.bot.model.BaseBot;
import com.dl.officialsite.config.bean.Refreshable;
import com.dl.officialsite.config.constant.ConfigEnum;
import com.dl.officialsite.config.service.ServerConfigCacheService;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Slf4j
@Data
@EqualsAndHashCode(callSuper = false)
@Service
public class TelegramBot extends BaseBot<com.pengrad.telegrambot.TelegramBot> implements Refreshable {
    @Autowired
    private ServerConfigCacheService serverConfigCacheService;

    @EventListener(ApplicationReadyEvent.class)
    public void startUpOrRefresh() {
        this.botEnum = BotEnum.TELEGRAM;
        this.botServerConfig = serverConfigCacheService.get(ConfigEnum.TELEGRAM_BOT_CONFIG, BotServerConfig.class);
        Optional.ofNullable(this.botServerConfig).ifPresent(config ->{
            this.init();
        });
    }

    @Override
    public void initBot() {
        int timeout = NumberUtils.toInt(this.botServerConfig.getTimeoutInSeconds(), 10);

        // Create an OkHttpClient instance
        OkHttpClient client = new OkHttpClient.Builder()
            .connectTimeout(timeout, TimeUnit.SECONDS)   // Set the connection timeout
            .readTimeout(timeout, TimeUnit.SECONDS)      // Set the read timeout
            .writeTimeout(timeout, TimeUnit.SECONDS)     // Set the write timeout
            .build();

        bot = new com.pengrad.telegrambot.TelegramBot.Builder(this.botServerConfig.getBotToken()).okHttpClient(client).build();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("botServerConfig", botServerConfig)
            .append("bot", bot)
            .toString();
    }
}