package com.dl.officialsite.bot.event;

import org.springframework.context.ApplicationEvent;

public class EventNotify extends ApplicationEvent {

    private String msg ;

    public EventNotify(Object source, String msg) {
        super(source);
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
