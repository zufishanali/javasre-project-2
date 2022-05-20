package com.project2.restaurantApi.services;

import com.twilio.Twilio;

import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class MessageRequestService {

    final Logger logger = LoggerFactory.getLogger(MessageRequestService.class);

    public static final String ACCOUNT_SID = System.getenv("TWILIO_ACCOUNT_SID") ;
    public static final String AUTH_TOKEN = System.getenv("TWILIO_AUTH_TOKEN");
    public static final String TWILIO_NUMBER = System.getenv("TWILIO_NUMBER");

    public void sendSMS(String phoneTo, String messageBody){
        logger.info("Sending SMS to customer");

        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        Message message = Message.creator(
                        new PhoneNumber(phoneTo),
                        new PhoneNumber(TWILIO_NUMBER),
                        messageBody)
                .create();

        logger.info("SMS sent: "+message.getSid());
    }

}
