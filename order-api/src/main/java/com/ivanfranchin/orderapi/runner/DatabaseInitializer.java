package com.ivanfranchin.orderapi.runner;

import com.ivanfranchin.orderapi.model.User;
import com.ivanfranchin.orderapi.security.WebSecurityConfig;
import com.ivanfranchin.orderapi.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Component
public class DatabaseInitializer implements CommandLineRunner {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        if (!userService.getUsers().isEmpty()) {
            return;
        }
        USERS.forEach(user -> {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userService.saveUser(user);
        });
        log.info("Database initialized");
    }

    private static final List<User> USERS = Arrays.asList(
            new User("AdminFN", "AdminLN", "fireflies186@gmail.com", "Admin@123", WebSecurityConfig.ADMIN),
            new User("UserFN", "UserLN", "cranberries186@gmail.com", "User@123", WebSecurityConfig.USER)
    );
}
