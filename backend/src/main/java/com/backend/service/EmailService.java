package com.backend.service;


import com.backend.request.EmailRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String sender;

    public Boolean sendMail(EmailRequest emailRequest) throws MessagingException {
        // Creating a mime message
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper;


        // Setting multipart as true for attachments to
        // be send
        mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
        mimeMessageHelper.setFrom(sender);
        mimeMessageHelper.setTo(emailRequest.getRecipient());
        mimeMessageHelper.setText(emailRequest.getMsgBody(),true);
        mimeMessageHelper.setSubject(emailRequest.getSubject());
//        mimeMessage.setContent(emailRequest.getSubject(),"text/html");

        // Adding the attachment
//        FileSystemResource file = new FileSystemResource(new File(details.getAttachment()));
//
//        mimeMessageHelper.addAttachment(file.getFilename(), file);

        // Sending the mail
        javaMailSender.send(mimeMessage);
        return true;
    }
}

