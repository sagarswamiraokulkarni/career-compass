package com.ooad.careercompass.rest.controller;

import com.ooad.careercompass.exception.DuplicatedUserInfoException;
import com.ooad.careercompass.model.User;
import com.ooad.careercompass.rest.dto.*;
import com.ooad.careercompass.security.TokenProvider;
import com.ooad.careercompass.service.AccountService;
import com.ooad.careercompass.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

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

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSuccessfulLogin() throws Exception {
        // Arrange
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("test@example.com");
        loginRequest.setPassword("password");
        String token = "generated-token";
        Integer userId = 1;
        String email = "test@example.com";
        String firstName = "John";
        String lastName = "Doe";
        String verifyHash = "hash";
        String role = "USER";
        AuthResponse authResponse = new AuthResponse(token, userId, email, firstName, lastName, verifyHash, role);

        when(tokenProvider.generate(any())).thenReturn(token);
        when(userService.getUserLoginAuth(loginRequest.getUsername(), token)).thenReturn(authResponse);

        // Act
        AuthResponse response = authController.login(loginRequest);

        // Assert
        assertEquals(token, response.accessToken());
        assertEquals(userId, response.userId());
        assertEquals(email, response.email());
        assertEquals(firstName, response.firstName());
        assertEquals(lastName, response.lastName());
        assertEquals(verifyHash, response.verifyHash());
        assertEquals(role, response.role());
        verify(userService, times(1)).getUserLoginAuth(loginRequest.getUsername(), token);
    }

    @Test
    void testDuplicateEmailSignup() {
        // Arrange
        SignUpRequest signUpRequest = new SignUpRequest();
        signUpRequest.setEmail("test@example.com");
        signUpRequest.setPassword("password");
        signUpRequest.setFirstName("John");
        signUpRequest.setLastName("Doe");
        signUpRequest.setPhoneNumber("1234567890");

        when(userService.hasUserWithEmail(signUpRequest.getEmail())).thenReturn(true);

        // Act & Assert
        assertThrows(DuplicatedUserInfoException.class, () -> authController.signUp(signUpRequest));
        verify(userService, times(1)).hasUserWithEmail(signUpRequest.getEmail());
        verify(userService, never()).saveUser(any(User.class));
    }

    @Test
    void testSuccessfulSignup() {
        // Arrange
        SignUpRequest signUpRequest = new SignUpRequest();
        signUpRequest.setEmail("test@example.com");
        signUpRequest.setPassword("password");
        signUpRequest.setFirstName("John");
        signUpRequest.setLastName("Doe");
        signUpRequest.setPhoneNumber("1234567890");

        when(userService.hasUserWithEmail(signUpRequest.getEmail())).thenReturn(false);

        // Act
        GenericResponse response = authController.signUp(signUpRequest);

        // Assert
        assertEquals("Success", response.getStatus());
        assertEquals("Account Registered", response.getMessage());
        verify(userService, times(1)).hasUserWithEmail(signUpRequest.getEmail());
        verify(userService, times(1)).saveUser(any(User.class));
    }

    // Add more test cases for other methods...
}