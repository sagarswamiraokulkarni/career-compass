package com.ivanfranchin.orderapi.service;

import com.ivanfranchin.orderapi.repository.UserRepository;
import com.ivanfranchin.orderapi.exception.UserNotFoundException;
import com.ivanfranchin.orderapi.model.User;
import com.ivanfranchin.orderapi.rest.dto.GenericResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;


    public GenericResponse checkIfUserExistsAndRegistrationIsCompleted(String email){
        Optional<User> user=userRepository.findByEmail(email);

        GenericResponse genericResponse=new GenericResponse();
        if(user.isPresent()){
            genericResponse.setAccountVerified(user.get().isVerified());
            genericResponse.setUserAccountPresent(true);
        }else{
            genericResponse.setAccountVerified(false);
            genericResponse.setUserAccountPresent(false);
        }
        genericResponse.setStatus("Success");
        genericResponse.setMessage("Successfully executed");
        return genericResponse;
    }
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }
    public boolean hasUserWithEmail(String email) {
        return userRepository.existsByEmail(email);
    }


    public User validateAndGetUserByEmail(String email) {
        return getUserByEmail(email)
                .orElseThrow(() -> new UserNotFoundException(String.format("User with email %s not found", email)));
    }


    public User saveUser(User user) {
        return userRepository.save(user);
    }


    public void deleteUser(User user) {
        userRepository.delete(user);
    }
}
