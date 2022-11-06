package com.tcc.easyjobgo.util.email;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class EmailService  implements IEmailSender{

    private final static Logger LOGGER = LoggerFactory.getLogger(EmailService.class);
    
    @Autowired
    private JavaMailSender mailSender;
    
    private String supportEmail = "easyjobgo.help@gmail.com";

    @Override
    @Async
    public void send(String to, String subject, String email) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
            helper.setText(email, true);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setFrom(supportEmail);
            mailSender.send(mimeMessage);
        } catch (MessagingException m) {
            LOGGER.error("error to send email", m);
            throw new IllegalStateException("Fail to send Email");
        }
    }
}
