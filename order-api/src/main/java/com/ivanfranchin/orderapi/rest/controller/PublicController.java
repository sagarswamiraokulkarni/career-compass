package com.ivanfranchin.orderapi.rest.controller;

import com.ivanfranchin.orderapi.rest.dto.GenericResponse;
import com.ivanfranchin.orderapi.rest.dto.JobApplicationsDto;
import com.ivanfranchin.orderapi.rest.dto.RequestJobApplicationDto;
import com.ivanfranchin.orderapi.service.JobApplicationService;
import com.ivanfranchin.orderapi.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.ivanfranchin.orderapi.config.SwaggerConfig.BEARER_KEY_SECURITY_SCHEME;

@RequiredArgsConstructor
@RestController
@RequestMapping("/public")
public class PublicController {

    private final UserService userService;
    private final JobApplicationService jobApplicationService;

    @GetMapping("/numberOfUsers")
    public Integer getNumberOfUsers() {
        jobApplicationService.getAllJobApplicationsByUserId(3);
        return userService.getUsers().size();
    }

    @GetMapping("/checkUserRegistrationStatus/{email}")
    public GenericResponse getNumberOfUsers(@PathVariable("email") String email) {
        return userService.checkIfUserExistsAndRegistrationIsCompleted(email);
    }
}
