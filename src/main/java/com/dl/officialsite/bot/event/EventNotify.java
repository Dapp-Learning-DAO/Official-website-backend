package com.dl.officialsite.bot.event;

import com.dl.officialsite.bot.constant.BotEnum;
import com.dl.officialsite.bot.constant.ChannelEnum;
import org.springframework.context.ApplicationEvent;

public class EventNotify extends ApplicationEvent {
    private BotEnum botEnum;

    private ChannelEnum channelEnum;

    private String msg ;

    public EventNotify(Object source, BotEnum botEnum, ChannelEnum channelEnum, String msg) {
        super(source);
        this.botEnum = botEnum;
        this.channelEnum = channelEnum;
        this.msg = msg;
    }

    public ChannelEnum getChannelEnum() {
        return channelEnum;
    }

    public EventNotify setChannelEnum(ChannelEnum channelEnum) {
        this.channelEnum = channelEnum;
        return this;
    }

    public BotEnum getBotEnum() {
        return botEnum;
    }

    public EventNotify setBotEnum(BotEnum botEnum) {
        this.botEnum = botEnum;
        return this;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
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
