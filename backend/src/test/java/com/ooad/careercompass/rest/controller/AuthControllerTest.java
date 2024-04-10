package com.ooad.careercompass.rest.controller;
//
//import com.ooad.careercompass.exception.DuplicatedUserInfoException;
//import com.ooad.careercompass.model.User;
//import com.ooad.careercompass.repository.UserRepository;
//import com.ooad.careercompass.rest.dto.*;
//import com.ooad.careercompass.security.TokenProvider;
//import com.ooad.careercompass.service.AccountService;
//import com.ooad.careercompass.service.UserService;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import org.mockito.MockitoAnnotations;
//import org.springframework.security.authentication.AuthenticationManager;
//
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;
//
//class AuthControllerTest {
//
//    @Mock
//    private UserService userService;
//
//    @Mock
//    private AuthenticationManager authenticationManager;
//
//    @Mock
//    private TokenProvider tokenProvider;
//
//    @Mock
//    private AccountService accountService;
//
//    @InjectMocks
//    private AuthController authController;
//    @Mock
//    private UserRepository userRepository;
//
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//    }
//
//    @Test
//    void testSuccessfulLogin() throws Exception {
//        //{
//        //    "username": "fireflies186@gmail.com",
//        //    "password": "Admin@123"
//        //}
//        // Arrange
//        LoginRequest loginRequest = new LoginRequest();
//        loginRequest.setUsername("fireflies186@gmail.com");
//        loginRequest.setPassword("Admin@123");
//        User user = new User();
//        user.setEmail(loginRequest.getUsername());
//        user.setVerified(true);
//        Mockito.when(userRepository.findByEmail(loginRequest.getUsername())).thenReturn(Optional.of(user));
//
//        String token = "generated-token";
//        Integer userId = 1;
////        String email = "test@example.com";
////        String firstName = "John";
////        String lastName = "Doe";
////        String verifyHash = "hash";
////        String role = "USER";
//        AuthResponse authResponse = new AuthResponse(token, userId, loginRequest.getUsername(), firstName, lastName, verifyHash, role);
////
//        when(tokenProvider.generate(any())).thenReturn(token);
//        when(userService.getUserLoginAuth(loginRequest.getUsername(), token)).thenReturn(authResponse);
//
//        // Act
//        AuthResponse response = authController.login(loginRequest);
//        System.out.println(response);
//        // Assert
////        assertEquals(token, response.accessToken());
////        assertEquals(userId, response.userId());
//        assertEquals(loginRequest.getUsername(), response.email());
////        assertEquals(firstName, response.firstName());
////        assertEquals(lastName, response.lastName());
////        assertEquals(verifyHash, response.verifyHash());
////        assertEquals(role, response.role());
//        assertNotNull(response.accessToken());
//
////        verify(userService, times(1)).getUserLoginAuth(loginRequest.getUsername(), token);
//    }
//
//    @Test
//    void testDuplicateEmailSignup() {
//        // Arrange
//        SignUpRequest signUpRequest = new SignUpRequest();
//        signUpRequest.setEmail("test@example.com");
//        signUpRequest.setPassword("password");
//        signUpRequest.setFirstName("John");
//        signUpRequest.setLastName("Doe");
//        signUpRequest.setPhoneNumber("1234567890");
//
//        when(userService.hasUserWithEmail(signUpRequest.getEmail())).thenReturn(true);
//
//        // Act & Assert
//        assertThrows(DuplicatedUserInfoException.class, () -> authController.signUp(signUpRequest));
//        verify(userService, times(1)).hasUserWithEmail(signUpRequest.getEmail());
//        verify(userService, never()).saveUser(any(User.class));
//    }
//
//    @Test
//    void testSuccessfulSignup() {
//        // Arrange
//        SignUpRequest signUpRequest = new SignUpRequest();
//        signUpRequest.setEmail("test@example.com");
//        signUpRequest.setPassword("password");
//        signUpRequest.setFirstName("John");
//        signUpRequest.setLastName("Doe");
//        signUpRequest.setPhoneNumber("1234567890");
//
//        when(userService.hasUserWithEmail(signUpRequest.getEmail())).thenReturn(false);
//
//        // Act
//        GenericResponse response = authController.signUp(signUpRequest);
//
//        // Assert
//        assertEquals("Success", response.getStatus());
//        assertEquals("Account Registered", response.getMessage());
//        verify(userService, times(1)).hasUserWithEmail(signUpRequest.getEmail());
//        verify(userService, times(1)).saveUser(any(User.class));
//    }
//
//    // Add more test cases for other methods...
//}

import com.ooad.careercompass.exception.DuplicatedUserInfoException;
import com.ooad.careercompass.model.User;
import com.ooad.careercompass.rest.dto.*;
import com.ooad.careercompass.security.TokenProvider;
import com.ooad.careercompass.security.WebSecurityConfig;
import com.ooad.careercompass.service.AccountService;
import com.ooad.careercompass.service.AuthService;
import com.ooad.careercompass.service.UserService;
import com.ooad.careercompass.utils.CareerCompassUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthControllerTest {

    @Mock
    private UserService userService;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private TokenProvider tokenProvider;

    @Mock
    private AccountService accountService;

    @InjectMocks
    private AuthController authController;

    @Mock
    private AuthService authService;

    private LoginRequest loginRequest;
    private SignUpRequest signUpRequest;
    private User registeredUser;

    @BeforeEach
    void setUp() {
        String firstName="Pavan";
        String lastName="Sai";
        String password="Admin@123";
        String email="fireflies186@gmail.com";
        String phoneNumber="7222222222";

        loginRequest = new LoginRequest();
        loginRequest.setUsername(email);
        loginRequest.setPassword(password);

        signUpRequest = new SignUpRequest();
        signUpRequest.setFirstName(firstName);
        signUpRequest.setLastName(lastName);
        signUpRequest.setEmail(email);
        signUpRequest.setPassword(password);
        signUpRequest.setPhoneNumber(phoneNumber);


    }
//Registration
    @Test
    void signUp_NewUser_ReturnsGenericResponse() {
        when(userService.hasUserWithEmail(signUpRequest.getEmail())).thenReturn(false);
        GenericResponse result = authController.signUp(signUpRequest);
        registeredUser=authService.mapSignUpRequestToUser(signUpRequest);
        registeredUser.setId(1);
        verify(userService).saveUser(any(User.class));
        assertEquals("Success", result.getStatus());
        verify(userService).hasUserWithEmail(signUpRequest.getEmail());
        assertEquals(registeredUser.getEmail(),signUpRequest.getEmail());

    }
//Login
    @Test
    void login_ValidCredentials_ReturnsAuthResponse() throws Exception {
        String token = "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJleHAiOjE3MTI2MjQzMzYsImlhdCI6MTcxMjYyMTYzNiwianRpIjoiMzRhMWVhMjktMDE4ZS00YzM3LTgzOTAtYTJmNDhjMTMwMmExIiwiaXNzIjoiY2FyZWVyY29tcGFzcy1hcGkiLCJhdWQiOlsiY2FyZWVyY29tcGFzcy1hcHAiXSwic3ViIjoiZmlyZWZsaWVzMTg2QGdtYWlsLmNvbSIsInJvbCI6WyJBRE1JTiJdLCJuYW1lIjoiQWRtaW5GTiIsInByZWZlcnJlZF91c2VybmFtZSI6ImZpcmVmbGllczE4NkBnbWFpbC5jb20iLCJlbWFpbCI6ImZpcmVmbGllczE4NkBnbWFpbC5jb20ifQ.wKEmyegr5MVlYln2WB3DvBBlATvik41PieuYO0nfvpLdbEr_fzKChi3TsPuxF07VIXu0YrL74nu96K5AiPOGnw";
        Integer userId = 1;
        AuthResponse authResponse = new AuthResponse(token, userId, loginRequest.getUsername(), signUpRequest.getFirstName(), signUpRequest.getLastName(), CareerCompassUtils.getInstance().generateUniqueHash(), "USER");
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(mock(Authentication.class));
        when(tokenProvider.generate(any(Authentication.class))).thenReturn(token);
        when(userService.getUserLoginAuth(loginRequest.getUsername(), token)).thenReturn(authResponse);

        AuthResponse result = authController.login(loginRequest);

        assertEquals(authResponse, result);
        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(tokenProvider).generate(any(Authentication.class));
        verify(userService).getUserLoginAuth(loginRequest.getUsername(), token);
    }


//Already Registered
    @Test
    void signUp_DuplicateEmail_ThrowsDuplicatedUserInfoException() {
        when(userService.hasUserWithEmail(signUpRequest.getEmail())).thenReturn(true);
        assertThrows(DuplicatedUserInfoException.class, () -> authController.signUp(signUpRequest));
        verify(userService).hasUserWithEmail(signUpRequest.getEmail());
        verify(userService, never()).saveUser(any(User.class));
    }


    @Test
    void sendVerificationChallenge_ValidRequest_ReturnsGenericResponse() {
        GenericResponse expectedResponse = new GenericResponse();
        VerificationRequest verificationRequest = new VerificationRequest();
        String email="fireflies186@gmail.com";
        String otp="123456";
        String verificationStrategyType="sms";
        verificationRequest.setEmail(email);
        verificationRequest.setVerificationStrategyType(verificationStrategyType);
        verificationRequest.setVerificationChallenge(otp);
        when(accountService.sendVerificationChallenge(verificationRequest)).thenReturn(expectedResponse);

        GenericResponse result = authController.sendVerificationChallenge(verificationRequest);
        assertEquals(expectedResponse, result);
        verify(accountService).checkIfRegistrationIsCompleted(verificationRequest);
        verify(accountService).sendVerificationChallenge(verificationRequest);
    }

    @Test
    void validateChallenge_ValidRequest_ReturnsGenericResponse() {
        GenericResponse expectedResponse = new GenericResponse();
        VerificationRequest verificationRequest = new VerificationRequest();
        String email="fireflies186@gmail.com";
        String otp="123456";
        String verificationStrategyType="sms";
        verificationRequest.setEmail(email);
        verificationRequest.setVerificationStrategyType(verificationStrategyType);
        verificationRequest.setVerificationChallenge(otp);
        when(accountService.validateVerificationChallenge(verificationRequest)).thenReturn(expectedResponse);

        GenericResponse result = authController.validateChallenge(verificationRequest);

        assertEquals(expectedResponse, result);
        verify(accountService).checkIfRegistrationIsCompleted(verificationRequest);
        verify(accountService).validateVerificationChallenge(verificationRequest);
    }
}