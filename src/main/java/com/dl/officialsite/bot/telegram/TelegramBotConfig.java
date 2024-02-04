package com.dl.officialsite.bot.telegram;

import com.pengrad.telegrambot.TelegramBot;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.context.properties.ConfigurationProperties;

import javax.annotation.PostConstruct;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

@Slf4j
@Data
@ConfigurationProperties(prefix = "bot.telegram", ignoreInvalidFields = true)
public class TelegramBotConfig {
    private String botToken;
    private Long groupId;
    private int timeoutInSeconds = 10;
    private Map<String, Integer> topicNameToMessageThreadIdMap = new ConcurrentHashMap<>();

    private TelegramBot tgBot = null;

    @PostConstruct
    public void init() {
        log.info("Start to init Telegram bot ...");
        // Create an OkHttpClient instance
        OkHttpClient client = new OkHttpClient.Builder()
            .connectTimeout(timeoutInSeconds, TimeUnit.SECONDS)   // Set the connection timeout
            .readTimeout(timeoutInSeconds, TimeUnit.SECONDS)      // Set the read timeout
            .writeTimeout(timeoutInSeconds, TimeUnit.SECONDS)     // Set the write timeout
            .build();

        tgBot = new TelegramBot.Builder(botToken).okHttpClient(client).build();
        log.info("The Telegram Bot is initialized with detail:[{}]!!!", this.toString());
    }

    public Integer getFirstMessageThreadIdByTopicName(String topicName) {
        if (topicName == null) {
            return null;
        }

        String topicNameLowerCase = Optional.of(topicName)
            .map(topic -> StringUtils.replaceAll(topic, " ", "_"))
            .map(topicWithUnderscore -> StringUtils.lowerCase(topicWithUnderscore))
            .filter(StringUtils::isNotBlank)
            .orElse(null);

        return this.topicNameToMessageThreadIdMap.entrySet().stream()
            .filter(entry -> StringUtils.containsIgnoreCase(entry.getKey().toLowerCase(), topicNameLowerCase))
            .map(Map.Entry::getValue)
            .findFirst().orElse(null);
    }
}