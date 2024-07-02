/**
 *
 */


package com.dl.officialsite.config.constant;

import com.dl.officialsite.activity.config.ActivityConfig;
import com.dl.officialsite.nft.config.EcdsaKeyConfigService;
import com.dl.officialsite.nft.config.FileLoadConfig;
import com.dl.officialsite.bot.discord.DiscordBot;
import com.dl.officialsite.bot.telegram.TelegramBot;
import com.dl.officialsite.config.bean.Refreshable;
import com.dl.officialsite.oauth2.config.DiscordOAuthConfig;
import com.dl.officialsite.oauth2.config.GitHubOAuthConfig;
import com.dl.officialsite.oauth2.config.TelegramOAuthConfig;
import com.dl.officialsite.oauth2.config.TwitterOAuthConfig;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public enum ConfigEnum {
    ANNUAL_ACTIVITY_3ND("ANNUAL_ACTIVITY_3ND", ActivityConfig.class),

    TELEGRAM_BOT_CONFIG("TELEGRAM_BOT_CONFIG", TelegramBot.class),
    DISCORD_BOT_CONFIG("DISCORD_BOT_CONFIG", DiscordBot.class),

    GITHUB_OAUTH_CONFIG("GITHUB_OAUTH_CONFIG", GitHubOAuthConfig.class),
    TWITTER_OAUTH_CONFIG("TWITTER_OAUTH_CONFIG", TwitterOAuthConfig.class),
    TELEGRAM_OAUTH_CONFIG("TELEGRAM_OAUTH_CONFIG", TelegramOAuthConfig.class),
    DISCORD_OAUTH_CONFIG("DISCORD_OAUTH_CONFIG", DiscordOAuthConfig.class),

    FILE_PATH_CONFIG("FILE_PATH_CONFIG", FileLoadConfig.class),

    ECDSA_PRIVATE_KEY("ECDSA_PRIVATE_KEY", EcdsaKeyConfigService.class),

    CONTRACT_ADDRESS("CONTRACT_ADDRESS", EcdsaKeyConfigService.class),
    RED_PACKET_API_KEY("RED_PACKET_API_KEY", EcdsaKeyConfigService.class),
    MERKLE_DISTRIBUTION_API_KEY("MERKLE_DISTRIBUTION_API_KEY", EcdsaKeyConfigService.class);

    private String configName;
    private Class<? extends Refreshable> refreshClass;

}