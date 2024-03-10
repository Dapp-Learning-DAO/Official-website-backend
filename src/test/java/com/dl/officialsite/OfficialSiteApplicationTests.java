package com.dl.officialsite;

import com.dl.officialsite.bot.constant.ChannelEnum;
import com.dl.officialsite.bot.constant.GroupNameEnum;
import com.dl.officialsite.bot.model.Message;
import com.dl.officialsite.bot.telegram.TelegramBotService;
import com.dl.officialsite.mail.EmailService;
import com.dl.officialsite.member.MemberController;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class OfficialSiteApplicationTests {
    public static final Logger logger = LoggerFactory.getLogger(MemberController.class);

    @Autowired
    EmailService emailService;
    @Autowired
    TelegramBotService telegramBotService;

    @Test
    void contextLoads() {
    }


    @Test
    public void mailTest() {

        //Get the mailer instance


        //Send a composed mail
        logger.info("---------");
        emailService.sendSimpleMessage("411497616@qq.com", "Test Subject1", "Testing body11");
        logger.info("--------- message send");
        //Send a pre-configured mail
        //emailService.sendPreConfiguredMail("Exception occurred everywhere.. where are you ????");
    }


    @Test
    public void telegramBotTest() {
        Pair<Boolean, String> result = null;
        result = telegramBotService.sendMessage(GroupNameEnum.DAPP_LEARNING,
            ChannelEnum.of("general"), Message.build("Unit Test: send message to Hiring topic"));
        assertTrue(result.getKey());
        result = telegramBotService.sendMessage(GroupNameEnum.DAPP_LEARNING,
            ChannelEnum.of("Sharing"), Message.build("Unit Test: send message to Hiring topic"));

        assertTrue(result.getKey());
    }

}
