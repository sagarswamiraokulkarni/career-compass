package com.ivanfranchin.orderapi.notification;

import com.ivanfranchin.orderapi.model.JobApplication;
import com.ivanfranchin.orderapi.model.User;
import com.ivanfranchin.orderapi.utils.CareerCompassUtils;
import com.twilio.Twilio;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;
import com.mailjet.client.errors.MailjetException;
import com.mailjet.client.errors.MailjetSocketTimeoutException;
import com.mailjet.client.MailjetClient;
import com.mailjet.client.MailjetRequest;
import com.mailjet.client.MailjetResponse;
import com.mailjet.client.ClientOptions;
import com.mailjet.client.resource.Emailv31;
import org.json.JSONArray;
import org.json.JSONObject;
public class EmailNotificationStrategy implements NotificationStrategy {
//    private String emailAddress;
    private final String SMTP_HOST = "smtp.gmail.com";
    private final int SMTP_PORT = 587;
    private final String SMTP_USERNAME = "appari.pavan99@gmail.com";
    private final String SMTP_PASSWORD = "0423Pavan1813";
    private final String SMTP_FROM_EMAIL = "appari.pavan99@gmail.com";

//    private final String API_KEY="6fdbcf97cbfa49af7e981801fe6dd4f6";
//    private final String SECRET_KEY="605f500551a070c148f1a34763e292da";

    private final String API_KEY="23d9f53040a4b4c4eebb28a8e5c22afd";
    private final String SECRET_KEY="a753c76a9a9efb048ebc1b1ba768a63d";

    public EmailNotificationStrategy(){

    }

    public void sendNotification(User user) {
        MailjetClient client;
        MailjetRequest request;
        MailjetResponse response;
        try {
            client = new MailjetClient(API_KEY,SECRET_KEY, new ClientOptions("v3.1"));
            request = new MailjetRequest(Emailv31.resource)
                    .property(Emailv31.MESSAGES, new JSONArray()
                            .put(new JSONObject()
                                    .put(Emailv31.Message.FROM, new JSONObject()
                                            .put("Email", "sagarswamirao@gmail.com")
                                            .put("Name", "CareerCompass"))
                                    .put(Emailv31.Message.TO, new JSONArray()
                                            .put(new JSONObject()
                                                    .put("Email", user.getEmail())
                                                    .put("Name", user.getFirstName()+ " "+ user.getLastName())))
                                    .put(Emailv31.Message.SUBJECT, "Greetings from Career-Compass")
//                                    .put(Emailv31.Message.TEXTPART, "Dear "+user.getFirstName())
                                    .put(Emailv31.Message.HTMLPART, "<h2>Dear "+user.getFirstName()+"</h2><br /><h3>Thanks for registering. Please click <a href='http://localhost:3000/verify/"+user.getEmail()+"/"+CareerCompassUtils.getInstance().encodeString(user.getVerifyHash())+"'>here</a> to verify your email.</h3>")
                                    .put(Emailv31.Message.CUSTOMID, "AppGettingStartedTest")));

            response = client.post(request);
            System.out.println(response.getStatus());
            System.out.println(response.getData());
        }catch (Exception e){
            e.printStackTrace();
        }

//        Properties properties = new Properties();
//        properties.put("mail.smtp.host", SMTP_HOST);
//        properties.put("mail.smtp.port", SMTP_PORT);
//        properties.put("mail.smtp.auth", "true");
//        properties.put("mail.smtp.starttls.enable", "true");
//
//        Session session = Session.getInstance(properties, new Authenticator() {
//            protected PasswordAuthentication getPasswordAuthentication() {
//                return new PasswordAuthentication(SMTP_USERNAME, SMTP_PASSWORD);
//            }
//        });
//
//        try {
//            Message message = new MimeMessage(session);
//            message.setFrom(new InternetAddress(SMTP_FROM_EMAIL));
//            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(user.getEmail()));
//            message.setSubject("Career Compass: Account Registration");
//            String verificationLink = CareerCompassUtils.getInstance().generateVerificationLink(user);
//
//            message.setText("Dear " + user.getFirstName() + ",\n\n" +
//                    "Thank you for creating an account with Career Compass. To complete your registration, please click on the following link to verify your account:\n\n" +
//                    verificationLink + "\n\n" +
//                    "If you did not create an account with us, please ignore this email.\n\n" +
//                    "Best regards,\n" +
//                    "The Career Compass Team");
//
//            Transport.send(message);
//            System.out.println("Email notification sent to: " + user.getEmail());
//        } catch (MessagingException e) {
//            e.printStackTrace();
//        }
    }
}