package com.ivanfranchin.orderapi.notification;
import com.ivanfranchin.orderapi.model.User;
import com.ivanfranchin.orderapi.rest.dto.GenericResponse;
import com.ivanfranchin.orderapi.utils.CareerCompassUtils;
import com.mailjet.client.MailjetClient;
import com.mailjet.client.MailjetRequest;
import com.mailjet.client.MailjetResponse;
import com.mailjet.client.ClientOptions;
import com.mailjet.client.resource.Emailv31;
import org.json.JSONArray;
import org.json.JSONObject;

public class EmailNotificationStrategy implements NotificationStrategy {
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
                                            .put("Email", "sagarswamirao@gmail.com")
                                            .put("Name", "CareerCompass"))
                                    .put(Emailv31.Message.TO, new JSONArray()
                                            .put(new JSONObject()
                                                    .put("Email", user.getEmail())
                                                    .put("Name", user.getFirstName()+ " "+ user.getLastName())))
                                    .put(Emailv31.Message.SUBJECT, "Greetings from Career-Compass")
                                    .put(Emailv31.Message.HTMLPART, "<h2>Dear "+user.getFirstName()+"</h2><br /><h3>Thanks for registering. Please click <a href='http://localhost:3000/verify/"+user.getEmail()+"/"+user.getVerifyHash()+"'>here</a> to verify your email.</h3>")
                                    .put(Emailv31.Message.CUSTOMID, "AppGettingStartedTest")));

            response = client.post(request);
            System.out.println(response.getStatus());
            System.out.println(response.getData());
            genericResponse.setStatus("Success");
            genericResponse.setMessage("Successfully sent email");
        }catch (Exception e){
            genericResponse.setStatus("Error");
            genericResponse.setMessage(e.getMessage());
            e.printStackTrace();
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