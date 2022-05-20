package com.project2.restaurantApi.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailRequestService {

    final Logger logger = LoggerFactory.getLogger(EmailRequestService.class);

    @Autowired
    private JavaMailSender emailSender;

    public void sendEmail(String to, String messageBody) throws RuntimeException{
        logger.info("Sending email to customer");

        SimpleMailMessage email = new SimpleMailMessage();

        String subject = "ROOF - Order updated";

        email.setFrom("zali.projects14@gmail.com");
        email.setTo(to);
        email.setSubject(subject);
        email.setText(messageBody);
        emailSender.send(email);
        logger.info("Email sent");
    }

}
