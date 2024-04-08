package com.ooad.careercompass.rest.controller;

import com.ooad.careercompass.rest.dto.GenericResponse;
import com.ooad.careercompass.rest.dto.JobApplicationsDto;
import com.ooad.careercompass.rest.dto.RequestJobApplicationDto;
import com.ooad.careercompass.service.JobApplicationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class JobApplicationControllerTest {

    @Mock
    private JobApplicationService jobApplicationService;

    @InjectMocks
    private JobApplicationController jobApplicationController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllUnarchivedJobApplications() {
        // Arrange
        Integer userId = 1;
        List<JobApplicationsDto> expectedJobApplications = new ArrayList<>();
        // Add expected job applications to the list

        when(jobApplicationService.getAllUnarchivedJobApplications(userId)).thenReturn(expectedJobApplications);

        // Act
        List<JobApplicationsDto> result = jobApplicationController.getAllUnarchivedJobApplications(userId);

        // Assert
        assertEquals(expectedJobApplications, result);
        verify(jobApplicationService, times(1)).getAllUnarchivedJobApplications(userId);
    }

    @Test
    void testGetAllArchivedJobApplications() {
        // Arrange
        Integer userId = 1;
        List<JobApplicationsDto> expectedJobApplications = new ArrayList<>();
        // Add expected job applications to the list

        when(jobApplicationService.getAllArchivedJobApplications(userId)).thenReturn(expectedJobApplications);

        // Act
        List<JobApplicationsDto> result = jobApplicationController.getAllArchivedJobApplications(userId);

        // Assert
        assertEquals(expectedJobApplications, result);
        verify(jobApplicationService, times(1)).getAllArchivedJobApplications(userId);
    }

    @Test
    void testCreateJobApplication() throws Exception {
        // Arrange
        RequestJobApplicationDto requestDto = new RequestJobApplicationDto();
        // Set up the request DTO

        GenericResponse expectedResponse = new GenericResponse();
        // Set up the expected response

        when(jobApplicationService.addJobApplication(requestDto)).thenReturn(expectedResponse);

        // Act
        GenericResponse result = jobApplicationController.createJobApplication(requestDto);

        // Assert
        assertEquals(expectedResponse, result);
        verify(jobApplicationService, times(1)).addJobApplication(requestDto);
    }

    // Similarly, write test cases for other methods

    @Test
    void testArchiveJobApplication() throws Exception {
        // Arrange
        Integer userId = 1;
        Integer jobApplicationId = 1;

        // Act
        ResponseEntity<Void> result = jobApplicationController.archiveJobApplication(userId, jobApplicationId);

        // Assert
        assertEquals(HttpStatus.NO_CONTENT, result.getStatusCode());
        verify(jobApplicationService, times(1)).archiveByUserIdAndJobApplicationId(userId, jobApplicationId);
    }

    // ...
}