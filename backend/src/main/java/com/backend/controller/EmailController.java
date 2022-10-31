package com.backend.controller;

import com.backend.request.EmailRequest;
import com.backend.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;

//Purpose:
//    - Create URL to send email from application
//    - This URL is created for testing purpose
//
//Restrictions:
//    - Only the logged-in user can have access to these URLs
//
//Endpoints:
//        - /dev/v1/email/sendmail
//
//Author:
//    - Liu Fang
@RestController
@RequestMapping("dev/v1/email")
public class EmailController {

    @Autowired
    private EmailService emailService;

    @PostMapping("/sendmail")
    public ResponseEntity<?> sendMailWithAttachment(@RequestBody EmailRequest emailRequest) throws MessagingException {
        Boolean result = emailService.sendMail(emailRequest);

        return ResponseEntity.ok(result);
    }
}
