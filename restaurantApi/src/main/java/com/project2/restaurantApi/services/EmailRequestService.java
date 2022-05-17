package com.project2.restaurantApi.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailRequestService {

    private JavaMailSender emailSender;

    public void sendEmail(String to, String subject, String text){
        SimpleMailMessage email = new SimpleMailMessage();
        email.setFrom("zali.projects22@gmail.com");
        email.setTo(to);
        email.setSubject(subject);
        email.setText(text);
        emailSender.send(email);
    }

}
