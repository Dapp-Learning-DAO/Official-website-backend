package com.dl.officialsite.bot.event;

import com.dl.officialsite.bot.constant.BotEnum;
import com.dl.officialsite.bot.constant.ChannelEnum;
import com.dl.officialsite.bot.constant.GroupNameEnum;
import com.dl.officialsite.bot.model.Message;
import lombok.Data;
import org.springframework.context.ApplicationEvent;

@Data
public class EventNotify extends ApplicationEvent {
    private BotEnum botEnum;
    private GroupNameEnum groupEnum;

    private ChannelEnum channelEnum;

    private Message msg;

    public EventNotify(Object source, BotEnum botEnum, GroupNameEnum groupEnum, ChannelEnum channelEnum, Message msg) {
        super(source);
        this.botEnum = botEnum;
        this.groupEnum = groupEnum;
        this.channelEnum = channelEnum;
        this.msg = msg;
    }

    public EventNotify(Object source, BotEnum botEnum, ChannelEnum channelEnum, Message msg) {
        this(source, botEnum, GroupNameEnum.DAPP_LEARNING, channelEnum, msg);
    }


    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("EventNotify{");
        sb.append("botEnum=").append(botEnum);
        sb.append(", channelEnum=").append(channelEnum);
        sb.append(", msg='").append(msg).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
