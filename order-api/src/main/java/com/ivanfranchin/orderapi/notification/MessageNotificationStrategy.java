package com.ivanfranchin.orderapi.notification;

import com.ivanfranchin.orderapi.model.User;
import com.ivanfranchin.orderapi.rest.dto.GenericResponse;
import com.ivanfranchin.orderapi.utils.CareerCompassUtils;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.rest.verify.v2.service.Verification;
import com.twilio.rest.verify.v2.service.VerificationCheck;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;

public class MessageNotificationStrategy implements NotificationStrategy{

    private String strategyType;
    public MessageNotificationStrategy(String strategyType) {
        Twilio.init(CareerCompassUtils.getInstance().TWILIO_ACCOUNT_SID, CareerCompassUtils.getInstance().TWILIO_AUTH_TOKEN);
        this.strategyType = strategyType;
    }

    private MessageNotificationStrategy(){

    }

    @Override
    public GenericResponse sendNotification(User user) {
        GenericResponse genericResponse=new GenericResponse();
        Verification verification = Verification.creator(
                        CareerCompassUtils.getInstance().TWILIO_AUTH_SERVICE_SID,
                        user.getPhoneNumber(),
                        this.strategyType)
                .create();

        System.out.println(verification.getStatus());
        genericResponse.setStatus("Success");
        genericResponse.setMessage("OTP has been successfully sent, and awaits your verification");
        return genericResponse;
    }
    @Override
    public GenericResponse validateAccount(User user,String authSecret) {
        GenericResponse genericResponse=new GenericResponse();
        try {
            VerificationCheck verificationCheck = VerificationCheck.creator(
                            CareerCompassUtils.getInstance().TWILIO_AUTH_SERVICE_SID)
                    .setTo(user.getPhoneNumber())
                    .setCode(authSecret)
                    .create();

            System.out.println(verificationCheck.getStatus());
            user.setVerifyHash(CareerCompassUtils.getInstance().generateUniqueHash());
            user.setVerified(true);
            genericResponse.setStatus("Success");
            genericResponse.setMessage("Hurray! Account Verified");
        } catch (Exception e) {
            genericResponse.setStatus("Error");
            genericResponse.setMessage("Oops! Wrong OTP entered!");
        }
        return genericResponse;
    }
}