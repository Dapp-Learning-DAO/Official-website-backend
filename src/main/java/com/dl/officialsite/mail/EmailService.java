package com.dl.officialsite.mail;

import java.util.List;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;


@Component
public class EmailService {


    @Autowired
    private JavaMailSender emailSender;

    @Value("${spring.mail.username}")
    private String from;

    public void sendSimpleMessage(
            String to, String subject, String text) {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        emailSender.send(message);
    }

    public void memberJoinTeam(List<String> mailAddresss, String subject, String text) {
        for (String mailAddress : mailAddresss) {
            this.sendMail(mailAddress, subject, text);
        }
    }

    public void memberExitTeam(List<String> mailAddresss, String subject, String text) {
        for (String mailAddress : mailAddresss) {
            this.sendMail(mailAddress, subject, text);
        }
    }

    public void sendMail(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        emailSender.send(message);
    }

}
