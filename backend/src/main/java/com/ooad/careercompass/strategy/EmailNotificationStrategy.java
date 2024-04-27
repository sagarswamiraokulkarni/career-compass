package com.ooad.careercompass.strategy;
import com.ooad.careercompass.CareerCompassApplication;
import com.ooad.careercompass.model.User;
import com.ooad.careercompass.rest.dto.GenericResponse;
import com.ooad.careercompass.utils.CareerCompassUtils;
import com.mailjet.client.MailjetClient;
import com.mailjet.client.MailjetRequest;
import com.mailjet.client.MailjetResponse;
import com.mailjet.client.ClientOptions;
import com.mailjet.client.resource.Emailv31;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class EmailNotificationStrategy implements NotificationStrategy {
    private static final Logger logger = LoggerFactory.getLogger(CareerCompassApplication.class);
    public EmailNotificationStrategy(){

    }

    @Override
    public GenericResponse sendNotification(User user) {
        MailjetClient client;
        MailjetRequest request;
        MailjetResponse response;
        GenericResponse genericResponse=new GenericResponse();
        try {
            client = new MailjetClient(CareerCompassUtils.getInstance().MAILJET_API_KEY,CareerCompassUtils.getInstance().MAILJET_SECRET_KEY, new ClientOptions("v3.1"));
            request = new MailjetRequest(Emailv31.resource)
                    .property(Emailv31.MESSAGES, new JSONArray()
                            .put(new JSONObject()
                                    .put(Emailv31.Message.FROM, new JSONObject()
                                            .put("Email", "appari.pavan99@gmail.com")
                                            .put("Name", "CareerCompass"))
                                    .put(Emailv31.Message.TO, new JSONArray()
                                            .put(new JSONObject()
                                                    .put("Email", user.getEmail())
                                                    .put("Name", user.getFirstName()+ " "+ user.getLastName())))
                                    .put(Emailv31.Message.SUBJECT, "Greetings from Career-Compass")
                                    .put(Emailv31.Message.HTMLPART, "<h2>Dear "+user.getFirstName()+"</h2><br /><h3>Thanks for registering. Please click <a href='http://localhost:3000/verify/"+user.getEmail()+"/"+user.getVerifyHash()+"'>here</a> to verify your email.</h3>")
                                    .put(Emailv31.Message.CUSTOMID, "AppGettingStartedTest")));

            response = client.post(request);
            genericResponse.setStatus("Success");
            genericResponse.setMessage("Successfully sent email");
        }catch (Exception e){
            genericResponse.setStatus("Error");
            genericResponse.setMessage(e.getMessage());
            logger.error(e.getMessage());
        }
        return genericResponse;
    }
    @Override
    public GenericResponse validateAccount(User user,String authSecret) {
        GenericResponse genericResponse=new GenericResponse();
        if(user.getVerifyHash().equals(authSecret)){
            user.setVerifyHash(CareerCompassUtils.getInstance().generateUniqueHash());
            user.setVerified(true);
            genericResponse.setStatus("Success");
            genericResponse.setMessage("Hurray! Account Verified");
        }else{
            genericResponse.setStatus("Error");
            genericResponse.setMessage("Oops! Link seems to be broken!");
        }
        return genericResponse;
    }
}