package com.ivanfranchin.orderapi.service;

import com.ivanfranchin.orderapi.factory.NotificationFactory;
import com.ivanfranchin.orderapi.notification.NotificationStrategy;
import com.ivanfranchin.orderapi.repository.UserRepository;
import com.ivanfranchin.orderapi.rest.dto.GenericResponse;
import com.ivanfranchin.orderapi.rest.dto.VerificationRequest;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AccountService {

    public GenericResponse sendVerificationChallenge(VerificationRequest verificationRequest){
        NotificationStrategy notificationStrategy = switch (verificationRequest.getVerificationStrategyType().toLowerCase()) {
            case "sms" -> NotificationFactory.getSMSNotificationStrategy();
            case "whatsapp" -> NotificationFactory.getWhatsAppNotificationStrategy();
            case "email" -> NotificationFactory.getEmailNotificationStrategy();
            default -> throw new RuntimeException("Invalid verification type");
        };
        VerificationService verificationService=new VerificationService(notificationStrategy);
        return verificationService.sendUserVerification(verificationRequest.getEmail());
    }

    public GenericResponse validateVerificationChallenge(VerificationRequest verificationRequest){
        if(ObjectUtils.isEmpty(verificationRequest.getVerificationChallenge())){
            throw new RuntimeException("Invalid verification challenge");
        }
        NotificationStrategy notificationStrategy = switch (verificationRequest.getVerificationStrategyType()) {
            case "sms" -> NotificationFactory.getSMSNotificationStrategy();
            case "whatsapp" -> NotificationFactory.getWhatsAppNotificationStrategy();
            case "email" -> NotificationFactory.getEmailNotificationStrategy();
            default -> throw new RuntimeException("Invalid verification type");
        };
        VerificationService verificationService=new VerificationService(notificationStrategy);
        return verificationService.validateVerification(verificationRequest.getEmail(), verificationRequest.getVerificationChallenge());
    }
}
