package com.ooad.careercompass.rest.dto;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Set;

class VerificationRequestTest {

    private static Validator validator;

    @BeforeAll
    static void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void testValidVerificationRequest() {
        // Create a valid VerificationRequest instance
        VerificationRequest request = new VerificationRequest();
        request.setEmail("user@example.com");
        request.setVerificationStrategyType("sms");
        request.setVerificationChallenge("654321");

        // Validate the instance
        Set<ConstraintViolation<VerificationRequest>> violations = validator.validate(request);

        // Assert that there are no violations
        Assertions.assertTrue(violations.isEmpty());
    }

    @Test
    void testInvalidEmail() {
        // Create a VerificationRequest instance with an invalid email
        VerificationRequest request = new VerificationRequest();
        request.setEmail("invalid-email");
        request.setVerificationStrategyType("sms");
        request.setVerificationChallenge("654321");

        // Validate the instance
        Set<ConstraintViolation<VerificationRequest>> violations = validator.validate(request);

        // Assert that there is a violation for the email field
        Assertions.assertEquals(1, violations.size());
        ConstraintViolation<VerificationRequest> violation = violations.iterator().next();
        Assertions.assertEquals("email", violation.getPropertyPath().toString());
    }

    @Test
    void testMissingVerificationStrategyType() {
        // Create a VerificationRequest instance with a missing verificationStrategyType
        VerificationRequest request = new VerificationRequest();
        request.setEmail("user@example.com");
        request.setVerificationChallenge("654321");

        // Validate the instance
        Set<ConstraintViolation<VerificationRequest>> violations = validator.validate(request);

        // Assert that there is a violation for the verificationStrategyType field
        Assertions.assertEquals(1, violations.size());
        ConstraintViolation<VerificationRequest> violation = violations.iterator().next();
        Assertions.assertEquals("verificationStrategyType", violation.getPropertyPath().toString());
    }
}