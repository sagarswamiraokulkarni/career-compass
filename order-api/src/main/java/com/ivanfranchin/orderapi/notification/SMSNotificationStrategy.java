package com.ivanfranchin.orderapi.notification;

import com.ivanfranchin.orderapi.model.JobApplication;
import com.ivanfranchin.orderapi.model.User;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class SMSNotificationStrategy implements NotificationStrategy{
    private String phoneNumber;
    private final String TWILIO_ACCOUNT_SID = "";
    private final String TWILIO_AUTH_TOKEN = "";
    private final String TWILIO_PHONE_NUMBER = "";

//    public SMSNotificationStrategy(String phoneNumber) {
//        this.phoneNumber = phoneNumber;
//    }

    @Override
    public void sendNotification(User user) {
        Twilio.init(TWILIO_ACCOUNT_SID, TWILIO_AUTH_TOKEN);
        Message message = Message.creator(
                new com.twilio.type.PhoneNumber(phoneNumber),
                new com.twilio.type.PhoneNumber(TWILIO_PHONE_NUMBER),
                "Click on the link to verify your account: "
        ).create();
        System.out.println("SMS notification sent. Message SID: " + message.getSid());
    }

}