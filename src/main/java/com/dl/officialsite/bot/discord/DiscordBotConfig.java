package com.dl.officialsite.bot.discord;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.JDA;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;

@Slf4j
@Data
@ConfigurationProperties(prefix = "bot.discord", ignoreInvalidFields = true)
public class DiscordBotConfig {
    private String botToken;
    private Map<String, String> channelNameIdMap = new ConcurrentHashMap<>();
    private JDA jdaBot = null;

/*    @PostConstruct
    public void init() throws InterruptedException {
        log.info("Start to init Discord bot ...");
        if (StringUtils.containsIgnoreCase(botToken,"DISCORD_BOT_TOKEN")){
            log.warn("Discord is not configured properly!!!");
        }else{
            jdaBot = JDABuilder.createDefault(botToken).build().awaitReady();
        }
        log.info("The Discord Bot is initialized and ready with detail:[{}]!!!", this.toString());
    }*/

    public String getChannelIdByName(String channelName) {
        if (StringUtils.isBlank(channelName)) {
            return null;
        }

        String channelNameLowerCase = Optional.of(channelName)
            .map(name -> StringUtils.replaceAll(name, " ", "_"))
            .map(nameWithUnderscore -> StringUtils.lowerCase(nameWithUnderscore))
            .filter(StringUtils::isNotBlank)
            .orElse(null);

        return this.channelNameIdMap.entrySet().stream()
            .filter(entry -> StringUtils.containsIgnoreCase(entry.getKey().toLowerCase(), channelNameLowerCase))
            .map(Map.Entry::getValue)
            .findFirst().orElse(null);
    }

    @Bean
    public DiscordBotService discordBotService(){
        return new DiscordBotService(this);
    }
}