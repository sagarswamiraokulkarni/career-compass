package com.ooad.careercompass.security;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

class CustomUserDetailsTest {

    private CustomUserDetails userDetails;

    @BeforeEach
    void setUp() {
        userDetails = new CustomUserDetails();
        userDetails.setId(1);
        userDetails.setUsername("john");
        userDetails.setPassword("password");
        userDetails.setName("John Doe");
        userDetails.setEmail("john@example.com");
        userDetails.setIsAccountNonLocked(true);
        Collection<SimpleGrantedAuthority> authorities = Arrays.asList(
                new SimpleGrantedAuthority("ROLE_USER"),
                new SimpleGrantedAuthority("ROLE_ADMIN")
        );
        userDetails.setAuthorities(authorities);
    }

    @Test
    void testUserDetails() {
        assertEquals(1, userDetails.getId());
        assertEquals("john", userDetails.getUsername());
        assertEquals("password", userDetails.getPassword());
        assertEquals("John Doe", userDetails.getName());
        assertEquals("john@example.com", userDetails.getEmail());
        assertTrue(userDetails.isAccountNonExpired());
        assertTrue(userDetails.isAccountNonLocked());
        assertTrue(userDetails.isCredentialsNonExpired());
        assertTrue(userDetails.isEnabled());

        Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();
        assertEquals(2, authorities.size());
        assertTrue(authorities.contains(new SimpleGrantedAuthority("ROLE_USER")));
        assertTrue(authorities.contains(new SimpleGrantedAuthority("ROLE_ADMIN")));
    }

    @Test
    void testAccountNonLocked() {
        userDetails.setIsAccountNonLocked(false);
        assertFalse(userDetails.isAccountNonLocked());
    }
}