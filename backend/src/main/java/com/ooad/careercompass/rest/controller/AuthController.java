package com.ooad.careercompass.rest.controller;

import com.ooad.careercompass.exception.DuplicatedUserInfoException;
import com.ooad.careercompass.model.User;
import com.ooad.careercompass.rest.dto.*;
import com.ooad.careercompass.security.TokenProvider;
import com.ooad.careercompass.security.WebSecurityConfig;
import com.ooad.careercompass.service.AccountService;
import com.ooad.careercompass.service.AuthService;
import com.ooad.careercompass.service.UserService;
import com.ooad.careercompass.utils.CareerCompassUtils;
import com.ooad.careercompass.rest.dto.*;
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
    private final AuthService authService;
    private final UserService userService;

    private final AccountService accountService;
    @PostMapping("/authenticate")
    public AuthResponse login(@Valid @RequestBody LoginRequest loginRequest) throws Exception {
        try {
            String token = authService.authenticateAndGetToken(loginRequest.getUsername(), loginRequest.getPassword());
            return userService.getUserLoginAuth(loginRequest.getUsername(),token);
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

@ResponseStatus(HttpStatus.CREATED)
@PostMapping("/signup")
public GenericResponse signUp(@Valid @RequestBody SignUpRequest signUpRequest) {
    return authService.signUp(signUpRequest);
}

    @PostMapping("/sendVerificationChallenge")
    public GenericResponse sendVerificationChallenge(@Valid @RequestBody VerificationRequest verificationRequest) {
        //        TODO: FACADE PATTERN
        accountService.checkIfRegistrationIsCompleted(verificationRequest);
        return accountService.sendVerificationChallenge(verificationRequest);
    }


    @PostMapping("/validateChallenge")
    public GenericResponse validateChallenge(@Valid @RequestBody VerificationRequest verificationRequest) {
        //        TODO: FACADE PATTERN
        accountService.checkIfRegistrationIsCompleted(verificationRequest);
        return accountService.validateVerificationChallenge(verificationRequest);
    }
}
