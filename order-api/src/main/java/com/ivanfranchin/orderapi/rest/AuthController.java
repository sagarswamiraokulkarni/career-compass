package com.ivanfranchin.orderapi.rest;

import com.ivanfranchin.orderapi.exception.DuplicatedUserInfoException;
import com.ivanfranchin.orderapi.model.User;
import com.ivanfranchin.orderapi.rest.dto.*;
import com.ivanfranchin.orderapi.security.TokenProvider;
import com.ivanfranchin.orderapi.security.WebSecurityConfig;
import com.ivanfranchin.orderapi.service.AccountService;
import com.ivanfranchin.orderapi.service.UserService;
import com.ivanfranchin.orderapi.utils.CareerCompassUtils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final TokenProvider tokenProvider;

    private final AccountService accountService;
    @PostMapping("/authenticate")
    public AuthResponse login(@Valid @RequestBody LoginRequest loginRequest) throws Exception {
        try {
            String token = authenticateAndGetToken(loginRequest.getUsername(), loginRequest.getPassword());
            return new AuthResponse(token);
        }catch (Exception e){
//            e.printStackTrace();
            throw new Exception(e.getMessage());
        }
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

    @PostMapping("/sendVerificationChallenge")
    public GenericResponse sendVerificationChallenge(@Valid @RequestBody VerificationRequest verificationRequest) {
         if (userService.checkIfUserExistsAndRegistrationIsCompleted(verificationRequest.getEmail()).isAccountVerified()) {
            throw new DuplicatedUserInfoException(String.format("Email %s is already verified", verificationRequest.getEmail()));
        }
//        TODO: FACADE PATTERN
        return accountService.sendVerificationChallenge(verificationRequest);
    }

    @PostMapping("/validateChallenge")
    public GenericResponse validateChallenge(@Valid @RequestBody VerificationRequest verificationRequest) {
        if (userService.checkIfUserExistsAndRegistrationIsCompleted(verificationRequest.getEmail()).isAccountVerified()) {
            throw new DuplicatedUserInfoException(String.format("Email %s is already verified", verificationRequest.getEmail()));
        }
//        TODO: FACADE PATTERN
        return accountService.validateVerificationChallenge(verificationRequest);
    }

    private String authenticateAndGetToken(String username, String password) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        return tokenProvider.generate(authentication);
    }

    private User mapSignUpRequestToUser(SignUpRequest signUpRequest) {
        User user = new User();
        user.setPassword(CareerCompassUtils.getInstance().encodeString(signUpRequest.getPassword()));
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
