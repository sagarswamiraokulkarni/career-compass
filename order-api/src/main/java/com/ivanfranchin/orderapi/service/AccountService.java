package com.ivanfranchin.orderapi.service;

import com.ivanfranchin.orderapi.exception.DuplicatedUserInfoException;
import com.ivanfranchin.orderapi.factory.NotificationFactory;
import com.ivanfranchin.orderapi.strategy.NotificationStrategy;
import com.ivanfranchin.orderapi.repository.UserRepository;
import com.ivanfranchin.orderapi.rest.dto.GenericResponse;
import com.ivanfranchin.orderapi.rest.dto.VerificationRequest;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AccountService {
    private final UserRepository userRepository;
    private final UserService userService;
    public void checkIfRegistrationIsCompleted(VerificationRequest verificationRequest){
        if (userService.checkIfUserExistsAndRegistrationIsCompleted(verificationRequest.getEmail()).isAccountVerified()) {
            throw new DuplicatedUserInfoException(String.format("Email %s is already verified", verificationRequest.getEmail()));
        }
    }
    public GenericResponse sendVerificationChallenge(VerificationRequest verificationRequest){
        NotificationStrategy notificationStrategy = getNotificationStrategy(verificationRequest.getVerificationStrategyType().toLowerCase());
        VerificationService verificationService=new VerificationService(notificationStrategy);
        return verificationService.sendUserVerification(userRepository.findByEmail(verificationRequest.getEmail()));
    }
    private NotificationStrategy getNotificationStrategy(String strategyType){
        return switch (strategyType) {
            case "sms" -> NotificationFactory.getSMSNotificationStrategy();
            case "whatsapp" -> NotificationFactory.getWhatsAppNotificationStrategy();
            case "email" -> NotificationFactory.getEmailNotificationStrategy();
            default -> throw new RuntimeException("Invalid verification type");
        };
    }
    public GenericResponse validateVerificationChallenge(VerificationRequest verificationRequest){
        if(ObjectUtils.isEmpty(verificationRequest.getVerificationChallenge())){
            throw new RuntimeException("Invalid verification challenge");
        }
        NotificationStrategy notificationStrategy = getNotificationStrategy(verificationRequest.getVerificationStrategyType().toLowerCase());
        VerificationService verificationService=new VerificationService(notificationStrategy);
        return verificationService.validateVerification(userRepository,verificationRequest);
    }
}
