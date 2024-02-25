package com.dl.officialsite.bot.event;

import com.dl.officialsite.bot.telegram.TelegramBotService;
import com.dl.officialsite.member.Member;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;


@Slf4j
@Component
public class NotifyEventListener implements ApplicationListener<EventNotify> {

    @Autowired
    private TelegramBotService telegramBotService;

    public static String  socialMedia = "website: https://dapplearning.org/\n" +
            "github: https://github.com/Dapp-Learning-DAO/Dapp-Learning-DAO\n" +
            "twitter: https://twitter.com/Dapp_Learning\n" +
            "youtube: https://www.youtube.com/@DappLearning\n" ;

    @Override
    public void onApplicationEvent(EventNotify event) {
        String className = event.getSource().getClass().getName();
        log.info("event class："+className);
        if(className.contains("member")) {

          Member member =  (Member) event.getSource();
            telegramBotService.sendMarkdownV2MessageToGeneral("Welcome " + member.getTelegramId()+ " " + member.getNickName() + "\n" + socialMedia);
        }

    }
}
