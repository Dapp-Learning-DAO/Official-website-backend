package com.dl.officialsite.bot.telegram;

import com.dl.officialsite.bot.constant.BotEnum;
import com.dl.officialsite.bot.model.BaseBotConfig;
import com.pengrad.telegrambot.TelegramBot;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.concurrent.TimeUnit;

@Slf4j
@Data
@EqualsAndHashCode(callSuper=false)
@ConfigurationProperties(prefix = "bot.telegram", ignoreInvalidFields = true)
public class TelegramBotConfig extends BaseBotConfig<TelegramBot, Long, Integer> {

    public TelegramBotConfig() {
        super(BotEnum.TELEGRAM);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("TelegramBotConfig{");
        sb.append("botToken='").append(botToken).append('\'');
        sb.append(", bot=").append(bot);
        sb.append(", timeoutInSeconds=").append(timeoutInSeconds);
        sb.append(", groupList=").append(groupList);
        sb.append('}');
        return sb.toString();
    }

    @Override
    public TelegramBot initBot() {
        // Create an OkHttpClient instance
        OkHttpClient client = new OkHttpClient.Builder()
            .connectTimeout(timeoutInSeconds, TimeUnit.SECONDS)   // Set the connection timeout
            .readTimeout(timeoutInSeconds, TimeUnit.SECONDS)      // Set the read timeout
            .writeTimeout(timeoutInSeconds, TimeUnit.SECONDS)     // Set the write timeout
            .build();

        return new TelegramBot.Builder(botToken).okHttpClient(client).build();
    }
}