package com.dl.officialsite.bot.config;

import com.dl.officialsite.config.bean.Configurable;
import lombok.Data;

import java.util.List;

@Data
public class BotServerConfig implements Configurable {
    List<BotGroup> groupList;
    String botToken;
    String timeoutInSeconds;
}
