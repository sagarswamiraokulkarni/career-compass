package com.ooad.careercompass.exception;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UserNotFoundExceptionTest {

    @Test
    void userNotFoundException_shouldHaveNotFoundStatusCode() {
        // Arrange
        String message = "User not found";

        // Act
        UserNotFoundException exception = new UserNotFoundException(message);

        // Assert
        ResponseStatus responseStatus = exception.getClass().getAnnotation(ResponseStatus.class);
        assertEquals(HttpStatus.NOT_FOUND, responseStatus.value());
        assertEquals(message, exception.getMessage());
    }
}
