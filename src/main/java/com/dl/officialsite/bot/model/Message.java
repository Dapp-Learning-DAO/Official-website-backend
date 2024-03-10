package com.dl.officialsite.bot.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Message {
    private String message;
    private Boolean disableWebPagePreview;

    private Message() {
    }

    public static Message build(String msg) {
        return build(msg, true);

    }

    public static Message build(String msg, boolean disableWebPagePreview) {
        Message message = new Message();
        message.message = msg;
        message.disableWebPagePreview = disableWebPagePreview;
        return message;
    }
}