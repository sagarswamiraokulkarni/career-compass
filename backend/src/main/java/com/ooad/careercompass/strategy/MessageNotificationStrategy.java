package com.ooad.careercompass.strategy;

import com.ooad.careercompass.model.User;
import com.ooad.careercompass.rest.dto.GenericResponse;
import com.ooad.careercompass.utils.CareerCompassUtils;
import com.twilio.Twilio;
import com.twilio.rest.verify.v2.service.Verification;
import com.twilio.rest.verify.v2.service.VerificationCheck;

public class MessageNotificationStrategy implements NotificationStrategy{

    private final String strategyType;

    public MessageNotificationStrategy(String strategyType) {
        this.strategyType = strategyType;
    }

    @Override
    public GenericResponse sendNotification(User user) {
        GenericResponse genericResponse=new GenericResponse();
        Twilio.init(CareerCompassUtils.getInstance().TWILIO_ACCOUNT_SID, CareerCompassUtils.getInstance().TWILIO_AUTH_TOKEN);
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
            Twilio.init(CareerCompassUtils.getInstance().TWILIO_ACCOUNT_SID, CareerCompassUtils.getInstance().TWILIO_AUTH_TOKEN);
            VerificationCheck verificationCheck = VerificationCheck.creator(
                            CareerCompassUtils.getInstance().TWILIO_AUTH_SERVICE_SID)
                    .setTo(user.getPhoneNumber())
                    .setCode(authSecret)
                    .create();
            if(verificationCheck.getStatus().equals("approved")){
                user.setVerifyHash(CareerCompassUtils.getInstance().generateUniqueHash());
                user.setVerified(true);
                genericResponse.setStatus("Success");
                genericResponse.setMessage("Hurray! Account Verified");
            }else {
                throw new Exception(verificationCheck.getStatus());
            }
        } catch (Exception e) {
            genericResponse.setStatus("Error");
            genericResponse.setMessage("Oops! Wrong OTP entered!");
        }
        return genericResponse;
    }
}