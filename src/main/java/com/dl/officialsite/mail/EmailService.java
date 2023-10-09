package com.dl.officialsite.mail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;


@Component
public class EmailService {


    @Autowired
    private JavaMailSender emailSender;

    @Autowired
    public SimpleMailMessage template;

    public void sendSimpleMessage(
            String to, String subject, String text) {

        SimpleMailMessage message = new SimpleMailMessage();
        //message.setFrom("411497616@qq.com");
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        emailSender.send(message);
    }





}
