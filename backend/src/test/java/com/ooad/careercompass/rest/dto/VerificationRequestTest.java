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
        VerificationRequest request = new VerificationRequest();
        request.setEmail("user@example.com");
        request.setVerificationStrategyType("sms");
        request.setVerificationChallenge("654321");

        Set<ConstraintViolation<VerificationRequest>> violations = validator.validate(request);

        Assertions.assertTrue(violations.isEmpty());
    }

    @Test
    void testInvalidEmail() {
        VerificationRequest request = new VerificationRequest();
        request.setEmail("invalid-email");
        request.setVerificationStrategyType("sms");
        request.setVerificationChallenge("654321");

        Set<ConstraintViolation<VerificationRequest>> violations = validator.validate(request);

        Assertions.assertEquals(1, violations.size());
        ConstraintViolation<VerificationRequest> violation = violations.iterator().next();
        Assertions.assertEquals("email", violation.getPropertyPath().toString());
    }

    @Test
    void testMissingVerificationStrategyType() {
        VerificationRequest request = new VerificationRequest();
        request.setEmail("user@example.com");
        request.setVerificationChallenge("654321");

        Set<ConstraintViolation<VerificationRequest>> violations = validator.validate(request);

        Assertions.assertEquals(1, violations.size());
        ConstraintViolation<VerificationRequest> violation = violations.iterator().next();
        Assertions.assertEquals("verificationStrategyType", violation.getPropertyPath().toString());
    }
}