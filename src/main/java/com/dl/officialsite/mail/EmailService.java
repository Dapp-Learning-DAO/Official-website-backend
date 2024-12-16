package com.dl.officialsite.mail;

import java.io.File;
import java.util.List;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;


@Component
@Slf4j
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
        message.setCc("dapplearning@gmail.com");
        emailSender.send(message);
    }

    public void sendMail(List<String> to, String subject, String text, List<String> cc) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);

        // 将 List<String> 转换为 String[] 并设置多个收件人
        message.setTo(to.toArray(new String[0]));

        // 如果 cc 不为空，将其转换为 String[] 并设置多个抄送人
        if (cc != null && !cc.isEmpty()) {
            message.setCc(cc.toArray(new String[0]));
        }

        message.setSubject(subject);
        message.setText(text);

        // 发送邮件
        log.info("Sending email to: {}, tile: {}, content: {}", to, subject, text);
        emailSender.send(message);
    }


    public void sendMailWithFile(String to, String subject, String text, MultipartFile file)
        throws Exception {
        //1.创建一封邮件的实例对象
        MimeMessage msg = emailSender.createMimeMessage();
        //2.设置发件人地址
        msg.setFrom("15503679582@163.com");
        msg.setRecipient(MimeMessage.RecipientType.TO,new InternetAddress(to));
        //4.设置邮件主题
        msg.setSubject(subject);

        //下面是设置邮件正文
        //msg.setContent("简单的纯文本邮件！", "text/html;charset=UTF-8");

        // 9. 创建附件"节点"
        MimeBodyPart attachment = new MimeBodyPart();
        // 读取本地文件
        //mutilpart to file

        DataSource ds2 = new FileDataSource(MultipartFileToFile(file));
        DataHandler dh2 = new DataHandler(ds2);
        // 将附件数据添加到"节点"
        attachment.setDataHandler(dh2);
        // 设置附件的文件名（需要编码）
        attachment.setFileName(file.getName());

        // 10. 设置（文本+图片）和 附件 的关系（合成一个大的混合"节点" / Multipart ）
        MimeMultipart mm = new MimeMultipart();
        mm.addBodyPart(attachment);     // 如果有多个附件，可以创建多个多次添加
        mm.setSubType("mixed");         // 混合关系

        // 11. 设置整个邮件的关系（将最终的混合"节点"作为邮件的内容添加到邮件对象）
        msg.setContent(mm);

        emailSender.send(msg);
    }

    //将MultipartFile转换为File
    public static File MultipartFileToFile(MultipartFile multiFile) {
        // 获取文件名
        String fileName = multiFile.getOriginalFilename();
        if (fileName == null){
            return null;
        }
        // 获取文件后缀
        String prefix = fileName.substring(fileName.lastIndexOf("."));
        // 若须要防止生成的临时文件重复,能够在文件名后添加随机码
        try {
            File file = File.createTempFile(fileName, prefix);
            multiFile.transferTo(file);
            return file;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
