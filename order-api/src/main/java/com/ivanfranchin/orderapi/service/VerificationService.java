package com.ivanfranchin.orderapi.service;

import com.ivanfranchin.orderapi.model.User;
import com.ivanfranchin.orderapi.notification.NotificationStrategy;
import com.ivanfranchin.orderapi.repository.UserRepository;
import com.ivanfranchin.orderapi.rest.dto.GenericResponse;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class VerificationService {
    private NotificationStrategy notificationStrategy;

    private UserRepository userRepository;
    public VerificationService(NotificationStrategy notificationStrategy){
        this.notificationStrategy=notificationStrategy;
    }
    private VerificationService(){

    }

    public GenericResponse sendUserVerification(String email){
        Optional<User> user= userRepository.findByEmail(email);
        if(user.isPresent()) {
            return notificationStrategy.sendNotification(user.get());
        }else{
            throw new RuntimeException("User Not Found");
        }
    }

    public GenericResponse validateVerification(String email, String authSecret){
        Optional<User> user= userRepository.findByEmail(email);
        if(user.isPresent()) {
            return notificationStrategy.validateAccount(user.get(),authSecret);
        }else{
            throw new RuntimeException("User Not Found");
        }
    }
}
