package com.dl.officialsite.config.service;

import com.dl.officialsite.activity.constant.TaskTypeEnum;
import com.dl.officialsite.bot.constant.ChannelEnum;
import com.dl.officialsite.bot.constant.GroupNameEnum;
import com.dl.officialsite.config.bean.Configurable;
import com.dl.officialsite.config.bean.ServerConfig;
import com.dl.officialsite.config.bean.ServerConfigRepository;
import com.dl.officialsite.config.constant.ConfigEnum;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Optional;

@Slf4j
@Service
public class ServerConfigCacheService {
    private static Gson gson = null;

    @Autowired
    private ServerConfigRepository serverConfigRepository;

    static {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(TaskTypeEnum.class,
            (JsonDeserializer<TaskTypeEnum>) (jsonElement, type, jsonDeserializationContext) -> TaskTypeEnum.fromValue(jsonElement.getAsString()));
        gsonBuilder.registerTypeAdapter(GroupNameEnum.class,
            (JsonDeserializer<GroupNameEnum>) (jsonElement, type, jsonDeserializationContext) -> GroupNameEnum.valueOf(jsonElement.getAsString()));
        gsonBuilder.registerTypeAdapter(ChannelEnum.class,
            (JsonDeserializer<ChannelEnum>) (jsonElement, type, jsonDeserializationContext) -> ChannelEnum.of(jsonElement.getAsString()));
        gson = gsonBuilder.create();
    }

    private static final Cache<ConfigEnum, ServerConfig> SERVER_CONFIG_CACHE = Caffeine.newBuilder()
        .maximumSize(512)
        .expireAfterWrite(Duration.ofHours(4))
        .build();

    public <T extends Configurable> T get(ConfigEnum configEnum, Class<T> clazz) {
        ServerConfig serverConfig = SERVER_CONFIG_CACHE.get(configEnum, config ->
            this.serverConfigRepository.findOneByConfigName(config.getConfigName()).orElse(null)
        );
        return Optional.ofNullable(serverConfig).map(config -> gson.fromJson(config.getConfigValue(), clazz)).orElse(null);
    }

    public void remove(ConfigEnum configEnum) {
        SERVER_CONFIG_CACHE.invalidate(configEnum);
    }

    public void removeAll() {
        SERVER_CONFIG_CACHE.invalidateAll();
    }
}