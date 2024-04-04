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
@RequestMapping("/tracker")
public class JobApplicationController {

    private final JobApplicationService jobApplicationService;
    @Operation(security = {@SecurityRequirement(name = BEARER_KEY_SECURITY_SCHEME)})
    @GetMapping("/getAllJobApplications/{userId}")
    public List<JobApplicationsDto> getAllJobApplications(@PathVariable("userId") Integer userId) {
        return jobApplicationService.getAllJobApplicationsByUserId(userId);
    }
    @Operation(security = {@SecurityRequirement(name = BEARER_KEY_SECURITY_SCHEME)})
    @PostMapping("/createJobApplication")
    public GenericResponse createJobApplication(@RequestBody RequestJobApplicationDto requestJobApplicationDto) throws Exception {
        return jobApplicationService.addJobApplication(requestJobApplicationDto);
    }
    @Operation(security = {@SecurityRequirement(name = BEARER_KEY_SECURITY_SCHEME)})
    @GetMapping("/getJobApplication/{userId}/{jobApplicationId}")
    public JobApplicationsDto getJobApplication(@PathVariable("userId") Integer userId, @PathVariable("jobApplicationId") Integer jobApplicationId) throws Exception {
        return jobApplicationService.getByUserIdAndJobApplicationId(userId,jobApplicationId);
    }

    @Operation(security = {@SecurityRequirement(name = BEARER_KEY_SECURITY_SCHEME)})
    @PutMapping("/updateJobApplication")
    public GenericResponse updateJobApplication(@RequestBody RequestJobApplicationDto requestJobApplicationDto) throws Exception {
        return jobApplicationService.updateJobApplication(requestJobApplicationDto);
    }

    @Operation(security = {@SecurityRequirement(name = BEARER_KEY_SECURITY_SCHEME)})
    @DeleteMapping("/deleteJobApplication/{userId}/{jobApplicationId}")
    public ResponseEntity<Void> deleteJobApplication(@PathVariable("userId") Integer userId, @PathVariable("jobApplicationId") Integer jobApplicationId) throws Exception {
        jobApplicationService.deleteByUserIdAndJobApplicationId(userId,jobApplicationId);
        return ResponseEntity.noContent().build();
    }
    @Operation(security = {@SecurityRequirement(name = BEARER_KEY_SECURITY_SCHEME)})
    @PatchMapping("/updateJobApplicationStarStatus/{userId}/{jobApplicationId}")
    public GenericResponse starJobApplication(@PathVariable("userId") Integer userId, @PathVariable("jobApplicationId") Integer jobApplicationId) throws Exception {
        return jobApplicationService.updateStarredStatusByUserIdAndJobApplicationId(userId,jobApplicationId);
    }
}
