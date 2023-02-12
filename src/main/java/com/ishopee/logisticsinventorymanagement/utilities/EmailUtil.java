package com.ishopee.logisticsinventorymanagement.utilities;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Component
public class EmailUtil {

    @Autowired
    private JavaMailSender mailSender;

    public boolean send(String to, String subject, String text) {
        boolean sent;
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
        try {
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(text);
            mailSender.send(mimeMessage);
            sent = true;
        } catch (MessagingException e) {
            sent = false;
            e.printStackTrace();
        }
        return sent;
    }
}
