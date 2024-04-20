package com.dl.officialsite.bot.discord;

import com.dl.officialsite.bot.BaseBotService;
import com.dl.officialsite.bot.constant.ChannelEnum;
import com.dl.officialsite.bot.constant.GroupNameEnum;
import com.dl.officialsite.bot.model.Message;
import com.dl.officialsite.common.utils.HttpUtil;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.util.EntityUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import java.io.IOException;


@Service
public class DiscordBotService extends BaseBotService<DiscordBotConfig> {
    private static final String IS_USER_IN_CHANNEL_API = "https://discord.com/api/v9/guilds/%s/members/%s";

    @Override
    public Pair<Boolean, String> sendMessage(GroupNameEnum groupNameEnum, ChannelEnum channelEnum, Message msg) {
        Pair<String, String> channelIdByName = this.getBotConfig().getGroupIdAndChannelIdByName(groupNameEnum, channelEnum);
        return DiscordBotUtil.sendMessageToChannel(this.getBotConfig().getBot(), channelIdByName.getValue(), msg);
    }

    @Override
    public boolean isUserInChannel(String channelId, String userId) {

        HttpGet httpGet = new HttpGet(String.format(IS_USER_IN_CHANNEL_API, channelId, userId));
        httpGet.setHeader(HttpHeaders.AUTHORIZATION, "Bot " + this.getBotConfig().getBotToken());

        try {
            HttpResponse response = HttpUtil.client().execute(httpGet);

            // Handle response
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                String responseBody = EntityUtils.toString(entity);
                return responseBody.contains(userId);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}