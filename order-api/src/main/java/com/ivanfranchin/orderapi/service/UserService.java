package com.ivanfranchin.orderapi.service;

import com.ivanfranchin.orderapi.model.User;
import com.ivanfranchin.orderapi.rest.dto.GenericResponse;

import java.util.List;
import java.util.Optional;

public interface UserService {

    GenericResponse checkIfUserExistsAndRegistrationIsCompleted(String email);
    List<User> getUsers();

    Optional<User> getUserByEmail(String username);

//    boolean hasUserWithUsername(String username);

    boolean hasUserWithEmail(String email);

    User validateAndGetUserByEmail(String email);

    User saveUser(User user);

    void deleteUser(User user);
}
