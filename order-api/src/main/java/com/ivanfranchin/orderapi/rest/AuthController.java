package com.ivanfranchin.orderapi.rest;

import com.ivanfranchin.orderapi.exception.DuplicatedUserInfoException;
import com.ivanfranchin.orderapi.model.User;
import com.ivanfranchin.orderapi.rest.dto.AuthResponse;
import com.ivanfranchin.orderapi.rest.dto.GenericResponse;
import com.ivanfranchin.orderapi.rest.dto.LoginRequest;
import com.ivanfranchin.orderapi.rest.dto.SignUpRequest;
import com.ivanfranchin.orderapi.security.TokenProvider;
import com.ivanfranchin.orderapi.security.WebSecurityConfig;
import com.ivanfranchin.orderapi.service.UserService;
import com.ivanfranchin.orderapi.utils.CareerCompassUtils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final TokenProvider tokenProvider;

    @PostMapping("/authenticate")
    public AuthResponse login(@Valid @RequestBody LoginRequest loginRequest) {
        String token = authenticateAndGetToken(loginRequest.getUsername(), loginRequest.getPassword());
        return new AuthResponse(token);
    }

//    @ResponseStatus(HttpStatus.CREATED)
//    @PostMapping("/signup")
//    public AuthResponse signUp(@Valid @RequestBody SignUpRequest signUpRequest) {
////        if (userService.hasUserWithUsername(signUpRequest.getEmail())) {
////            throw new DuplicatedUserInfoException(String.format("Username %s already been used", signUpRequest.getEmail()));
////        }
//        if (userService.hasUserWithEmail(signUpRequest.getEmail())) {
//            throw new DuplicatedUserInfoException(String.format("Email %s is already registered", signUpRequest.getEmail()));
//        }
//
//        userService.saveUser(mapSignUpRequestToUser(signUpRequest));
//
//        String token = authenticateAndGetToken(signUpRequest.getEmail(), signUpRequest.getPassword());
//        return new AuthResponse(token);
//    }
@ResponseStatus(HttpStatus.CREATED)
@PostMapping("/signup")
public GenericResponse signUp(@Valid @RequestBody SignUpRequest signUpRequest) {
//        if (userService.hasUserWithUsername(signUpRequest.getEmail())) {
//            throw new DuplicatedUserInfoException(String.format("Username %s already been used", signUpRequest.getEmail()));
//        }
    GenericResponse genericResponse=new GenericResponse();
    if (userService.hasUserWithEmail(signUpRequest.getEmail())) {
        throw new DuplicatedUserInfoException(String.format("Email %s is already registered", signUpRequest.getEmail()));
    }

    userService.saveUser(mapSignUpRequestToUser(signUpRequest));

//    String token = authenticateAndGetToken(signUpRequest.getEmail(), signUpRequest.getPassword());
    genericResponse.setStatus("Success");
    genericResponse.setMessage("Account Registered");
    return genericResponse;
}
    private String authenticateAndGetToken(String username, String password) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        return tokenProvider.generate(authentication);
    }

    private User mapSignUpRequestToUser(SignUpRequest signUpRequest) {
        User user = new User();
        user.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
        user.setFirstName(signUpRequest.getFirstName());
        user.setLastName(signUpRequest.getLastName());
        user.setEmail(signUpRequest.getEmail());
        user.setRole(WebSecurityConfig.USER);
        user.setVerifyHash(CareerCompassUtils.getInstance().generateUniqueHash());
        user.setPhoneNumber(signUpRequest.getPhoneNumber());
        user.setVerified(false);
        return user;
    }
}
