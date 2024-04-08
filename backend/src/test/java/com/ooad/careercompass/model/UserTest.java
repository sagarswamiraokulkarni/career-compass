package com.ooad.careercompass.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;

public class UserTest {

    @Test
    public void testUserCreation() {
        // Test creating a new user
        String firstName = "John";
        String lastName = "Doe";
        String email = "john.doe@example.com";
        String password = "password123";
        String role = "USER";

        User user = new User(firstName, lastName, email, password, role);

        Assertions.assertEquals(firstName, user.getFirstName());
        Assertions.assertEquals(lastName, user.getLastName());
        Assertions.assertEquals(email, user.getEmail());
        Assertions.assertEquals(password, user.getPassword());
        Assertions.assertNotNull(user.getCreatedAt());
        Assertions.assertNotNull(user.getVerifyHash());
        Assertions.assertTrue(user.isVerified());
        Assertions.assertEquals(role, user.getRole());
    }

    @Test
    public void testUserProperties() {
        // Test user properties
        User user = new User();
        user.setId(1);
        user.setFirstName("Jane");
        user.setLastName("Smith");
        user.setEmail("jane.smith@example.com");
        user.setPassword("password456");
        user.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        user.setVerifyHash("abcdefg");
        user.setVerified(false);
        user.setRole("ADMIN");
        user.setPhoneNumber("1234567890");

        Assertions.assertEquals(1, user.getId());
        Assertions.assertEquals("Jane", user.getFirstName());
        Assertions.assertEquals("Smith", user.getLastName());
        Assertions.assertEquals("jane.smith@example.com", user.getEmail());
        Assertions.assertEquals("password456", user.getPassword());
        Assertions.assertNotNull(user.getCreatedAt());
        Assertions.assertEquals("abcdefg", user.getVerifyHash());
        Assertions.assertFalse(user.isVerified());
        Assertions.assertEquals("ADMIN", user.getRole());
        Assertions.assertEquals("1234567890", user.getPhoneNumber());
    }
}