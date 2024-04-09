
package com.ooad.careercompass.security;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

class WebSecurityConfigTest {

    private WebSecurityConfig webSecurityConfig;

    @Mock
    private TokenAuthenticationFilter tokenAuthenticationFilter;

    @Mock
    private AuthenticationConfiguration authenticationConfiguration;

    @Mock
    private HttpSecurity httpSecurity;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        webSecurityConfig = new WebSecurityConfig(tokenAuthenticationFilter);
    }

    @Test
    void authenticationManager_ShouldReturnAuthenticationManager() throws Exception {
        // Arrange
        when(authenticationConfiguration.getAuthenticationManager()).thenReturn(mock(AuthenticationManager.class));

        // Act
        AuthenticationManager authenticationManager = webSecurityConfig.authenticationManager(authenticationConfiguration);

        // Assert
        assertNotNull(authenticationManager);
    }

//    @Test
//    void securityFilterChain_ShouldConfigureHttpSecurity() throws Exception {
//        // Act
//        SecurityFilterChain securityFilterChain = webSecurityConfig.securityFilterChain(httpSecurity);
//
//        // Assert
//        assertNotNull(securityFilterChain);
//        verify(httpSecurity).authorizeHttpRequests(any());
//        verify(httpSecurity).addFilterBefore(eq(tokenAuthenticationFilter), eq(UsernamePasswordAuthenticationFilter.class));
//        verify(httpSecurity).exceptionHandling(any());
//        verify(httpSecurity).sessionManagement(any());
//        verify(httpSecurity).cors(any());
//        verify(httpSecurity).csrf(any());
//    }

    @Test
    void passwordEncoder_ShouldReturnBCryptPasswordEncoder() {
        // Act
        PasswordEncoder passwordEncoder = webSecurityConfig.passwordEncoder();

        // Assert
        assertNotNull(passwordEncoder);
        assertTrue(passwordEncoder instanceof BCryptPasswordEncoder);
    }
}