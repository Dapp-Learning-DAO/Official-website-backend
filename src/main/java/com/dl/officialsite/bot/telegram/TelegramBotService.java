package com.dl.officialsite.bot.telegram;

import com.dl.officialsite.bot.BaseBotService;
import com.dl.officialsite.bot.constant.ChannelEnum;
import com.dl.officialsite.bot.constant.GroupNameEnum;
import com.dl.officialsite.bot.model.Message;
import lombok.Data;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


@Service
public class TelegramBotService implements BaseBotService {
    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private TelegramBot telegramBot;

    public static final String IS_USER_IN_CHANNEL_API =
        "https://api.telegram.org/bot%s/getChatMember?chat_id=%s&user_id=%s";


    @Override
    public boolean isBotInitialized() {
        return this.telegramBot.getBot() != null;
    }

    @Override
    public Pair<Boolean, String> sendMessage(GroupNameEnum groupNameEnum, ChannelEnum channelEnum, Message msg) {
        Pair<String, String> groupIdAndChannelId = this.telegramBot.getGroupIdAndChannelIdByName(groupNameEnum, channelEnum);
        return TelegramBotUtil.sendMarkdownV2MessageToTopic(this.telegramBot.getBot(), NumberUtils.toLong(groupIdAndChannelId.getKey()),
            msg, NumberUtils.toInt(groupIdAndChannelId.getValue()));
    }

    @Override
    public boolean isUserInChannel(String channelId, String userId) {
        ResponseEntity<TelegramApiResponse> responseEntity =
            restTemplate.getForEntity(String.format(IS_USER_IN_CHANNEL_API, this.telegramBot.getBotServerConfig().getBotToken(), channelId, userId),
                TelegramApiResponse.class);

        if (responseEntity.getStatusCode().is2xxSuccessful() && responseEntity.getBody() != null) {
            return Boolean.parseBoolean(responseEntity.getBody().getOk());
        }
        return false;
    }

    @Data
    public static class TelegramApiResponse {
        private String ok;
    }
}