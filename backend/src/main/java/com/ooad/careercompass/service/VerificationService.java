package com.ooad.careercompass.service;

import com.ooad.careercompass.model.User;
import com.ooad.careercompass.rest.dto.VerificationRequest;
import com.ooad.careercompass.strategy.NotificationStrategy;
import com.ooad.careercompass.repository.UserRepository;
import com.ooad.careercompass.rest.dto.GenericResponse;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class VerificationService {
    private NotificationStrategy notificationStrategy;

    public VerificationService(NotificationStrategy notificationStrategy){
        this.notificationStrategy=notificationStrategy;
    }
    private VerificationService(){

    }

    public GenericResponse sendUserVerification(Optional<User> user){
        if(user.isPresent()) {
            if(user.get().isVerified()){
                throw new RuntimeException("User Account is already verified");
            }
            return notificationStrategy.sendNotification(user.get());
        }else{
            throw new RuntimeException("User Not Found");
        }
    }
    public GenericResponse validateVerification(UserRepository userRepository, VerificationRequest verificationRequest){
        Optional<User> optionalUser=userRepository.findByEmail(verificationRequest.getEmail());
        if(optionalUser.isPresent()) {
            User user=optionalUser.get();
            if(user.isVerified()){
                throw new RuntimeException("User Account is already verified");
            }
            GenericResponse genericResponse = notificationStrategy.validateAccount(user,verificationRequest.getVerificationChallenge());
            if(genericResponse.getStatus().equals("Success")){
                userRepository.save(user);
            }
            return genericResponse;
        }else{
            throw new RuntimeException("User Not Found");
        }
    }
}
