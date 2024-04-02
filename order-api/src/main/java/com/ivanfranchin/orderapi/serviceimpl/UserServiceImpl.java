package com.ivanfranchin.orderapi.serviceimpl;

import com.ivanfranchin.orderapi.repository.UserRepository;
import com.ivanfranchin.orderapi.exception.UserNotFoundException;
import com.ivanfranchin.orderapi.model.User;
import com.ivanfranchin.orderapi.rest.dto.GenericResponse;
import com.ivanfranchin.orderapi.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
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
    @Override
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

//    @Override
//    public boolean hasUserWithUsername(String username) {
//        return false;
//    }

    @Override
    public boolean hasUserWithEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public User validateAndGetUserByEmail(String email) {
        return getUserByEmail(email)
                .orElseThrow(() -> new UserNotFoundException(String.format("User with email %s not found", email)));
    }

    @Override
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public void deleteUser(User user) {
        userRepository.delete(user);
    }
}
