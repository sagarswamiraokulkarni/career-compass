package com.ooad.careercompass.rest.controller;
//
//import com.ooad.careercompass.rest.dto.GenericResponse;
//import com.ooad.careercompass.rest.dto.JobApplicationsDto;
//import com.ooad.careercompass.rest.dto.RequestJobApplicationDto;
//import com.ooad.careercompass.service.JobApplicationService;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertNull;
//import static org.mockito.Mockito.*;
//
//class JobApplicationControllerTest {
//
//    @Mock
//    private JobApplicationService jobApplicationService;
//
//    @InjectMocks
//    private JobApplicationController jobApplicationController;
//
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//    }
//
//    @Test
//    void testGetAllUnarchivedJobApplications() {
//        // Arrange
//        Integer userId = 1;
//        List<JobApplicationsDto> expectedJobApplications = new ArrayList<>();
//        // Add expected job applications to the list
//
//        when(jobApplicationService.getAllUnarchivedJobApplications(userId)).thenReturn(expectedJobApplications);
//
//        // Act
//        List<JobApplicationsDto> result = jobApplicationController.getAllUnarchivedJobApplications(userId);
//
//        // Assert
//        assertEquals(expectedJobApplications, result);
//        verify(jobApplicationService, times(1)).getAllUnarchivedJobApplications(userId);
//    }
//
//    @Test
//    void testGetAllArchivedJobApplications() {
//        // Arrange
//        Integer userId = 1;
//        List<JobApplicationsDto> expectedJobApplications = new ArrayList<>();
//        // Add expected job applications to the list
//
//        when(jobApplicationService.getAllArchivedJobApplications(userId)).thenReturn(expectedJobApplications);
//
//        // Act
//        List<JobApplicationsDto> result = jobApplicationController.getAllArchivedJobApplications(userId);
//
//        // Assert
//        assertEquals(expectedJobApplications, result);
//        verify(jobApplicationService, times(1)).getAllArchivedJobApplications(userId);
//    }
//
//    @Test
//    void testCreateJobApplication() throws Exception {
//        // Arrange
//        RequestJobApplicationDto requestDto = new RequestJobApplicationDto();
//        // Set up the request DTO
//
//        GenericResponse expectedResponse = new GenericResponse();
//        // Set up the expected response
//
//        when(jobApplicationService.addJobApplication(requestDto)).thenReturn(expectedResponse);
//
//        // Act
//        GenericResponse result = jobApplicationController.createJobApplication(requestDto);
//
//        // Assert
//        assertEquals(expectedResponse, result);
//        verify(jobApplicationService, times(1)).addJobApplication(requestDto);
//    }
//
//    // Similarly, write test cases for other methods
//
//    @Test
//    void testArchiveJobApplication() throws Exception {
//        // Arrange
//        Integer userId = 1;
//        Integer jobApplicationId = 1;
//
//        // Act
//        ResponseEntity<Void> result = jobApplicationController.archiveJobApplication(userId, jobApplicationId);
//
//        // Assert
//        assertEquals(HttpStatus.NO_CONTENT, result.getStatusCode());
//        verify(jobApplicationService, times(1)).archiveByUserIdAndJobApplicationId(userId, jobApplicationId);
//    }
//
//    // ...
//}


import com.ooad.careercompass.rest.dto.GenericResponse;
import com.ooad.careercompass.rest.dto.JobApplicationsDto;
import com.ooad.careercompass.rest.dto.JobTagDto;
import com.ooad.careercompass.rest.dto.RequestJobApplicationDto;
import com.ooad.careercompass.service.JobApplicationService;
import com.ooad.careercompass.utils.ApplicationStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class JobApplicationControllerTest {

    @Mock
    private JobApplicationService jobApplicationService;

    @InjectMocks
    private JobApplicationController jobApplicationController;

    private Integer userId;
    private Integer jobApplicationId;
    private RequestJobApplicationDto requestJobApplicationDto;
    private List<JobApplicationsDto> unarchivedJobApplications;
    private List<JobApplicationsDto> archivedJobApplications;
    @BeforeEach
    void setUp() {
        userId = 1;
        jobApplicationId = 1;
        requestJobApplicationDto = new RequestJobApplicationDto();
        unarchivedJobApplications = new ArrayList<>();
        archivedJobApplications = new ArrayList<>();
        JobApplicationsDto archivedJobApplicationDto = new JobApplicationsDto();
        archivedJobApplicationDto.setId(1);
        archivedJobApplicationDto.setCompany("AWS");
        archivedJobApplicationDto.setPosition("SDE");
        archivedJobApplicationDto.setStatus(ApplicationStatus.Applied);
        archivedJobApplicationDto.setApplicationDate(LocalDate.now());
        archivedJobApplicationDto.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
        archivedJobApplicationDto.setCompanyUrl("https://aws.amazon.com/console/");
        archivedJobApplicationDto.setStarred(false);
        archivedJobApplicationDto.setArchived(false);
        archivedJobApplicationDto.setNotes("notes");
        archivedJobApplicationDto.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        JobTagDto jobTag=new JobTagDto();
        jobTag.setId(1);
        jobTag.setName("tag1");
        Set<JobTagDto> jobTags = new HashSet<>();
        jobTags.add(jobTag);
        archivedJobApplicationDto.setJobTags(jobTags);
        archivedJobApplications.add(archivedJobApplicationDto);
        JobApplicationsDto unarchivedJobApplicationDto = new JobApplicationsDto();
        unarchivedJobApplicationDto.setId(2);
        unarchivedJobApplicationDto.setCompany("Google");
        unarchivedJobApplicationDto.setPosition("SDE");
        unarchivedJobApplicationDto.setStatus(ApplicationStatus.Accepted);
        unarchivedJobApplicationDto.setApplicationDate(LocalDate.now());
        unarchivedJobApplicationDto.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
        unarchivedJobApplicationDto.setCompanyUrl("https://www.google.com/");
        unarchivedJobApplicationDto.setStarred(false);
        unarchivedJobApplicationDto.setArchived(true);
        unarchivedJobApplicationDto.setNotes("notes");
        unarchivedJobApplicationDto.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        unarchivedJobApplicationDto.setJobTags(jobTags);
        unarchivedJobApplications.add(unarchivedJobApplicationDto);
    }

    @Test
    void getAllUnarchivedJobApplications_ReturnsListOfJobApplicationsDto() {
        List<JobApplicationsDto> expectedJobApplications = unarchivedJobApplications;
        when(jobApplicationService.getAllUnarchivedJobApplications(userId)).thenReturn(expectedJobApplications);

        List<JobApplicationsDto> result = jobApplicationController.getAllUnarchivedJobApplications(userId);

        assertEquals(expectedJobApplications, result);
        verify(jobApplicationService).getAllUnarchivedJobApplications(userId);
    }

    @Test
    void getAllArchivedJobApplications_ReturnsListOfJobApplicationsDto() {
        List<JobApplicationsDto> expectedJobApplications = archivedJobApplications;

        when(jobApplicationService.getAllArchivedJobApplications(userId)).thenReturn(expectedJobApplications);

        List<JobApplicationsDto> result = jobApplicationController.getAllArchivedJobApplications(userId);

        assertEquals(expectedJobApplications, result);
        verify(jobApplicationService).getAllArchivedJobApplications(userId);
    }

    @Test
    void createJobApplication_ValidRequest_ReturnsGenericResponse() throws Exception {
        GenericResponse expectedResponse = new GenericResponse();

        when(jobApplicationService.addJobApplication(requestJobApplicationDto)).thenReturn(expectedResponse);

        GenericResponse result = jobApplicationController.createJobApplication(requestJobApplicationDto);

        assertEquals(expectedResponse, result);
        verify(jobApplicationService).addJobApplication(requestJobApplicationDto);
    }

    @Test
    void getJobApplication_ValidUserIdAndJobApplicationId_ReturnsJobApplicationsDto() throws Exception {
        JobApplicationsDto expectedJobApplication = new JobApplicationsDto();

        when(jobApplicationService.getByUserIdAndJobApplicationId(userId, jobApplicationId)).thenReturn(expectedJobApplication);

        JobApplicationsDto result = jobApplicationController.getJobApplication(userId, jobApplicationId);

        assertEquals(expectedJobApplication, result);
        verify(jobApplicationService).getByUserIdAndJobApplicationId(userId, jobApplicationId);
    }

    @Test
    void updateJobApplication_ValidRequest_ReturnsGenericResponse() throws Exception {
        GenericResponse expectedResponse = new GenericResponse();

        when(jobApplicationService.updateJobApplication(requestJobApplicationDto)).thenReturn(expectedResponse);

        GenericResponse result = jobApplicationController.updateJobApplication(requestJobApplicationDto);

        assertEquals(expectedResponse, result);
        verify(jobApplicationService).updateJobApplication(requestJobApplicationDto);
    }

    @Test
    void archiveJobApplication_ValidUserIdAndJobApplicationId_ReturnsNoContent() throws Exception {
        ResponseEntity<Void> result = jobApplicationController.archiveJobApplication(userId, jobApplicationId);

        assertEquals(HttpStatus.NO_CONTENT, result.getStatusCode());
        verify(jobApplicationService).archiveByUserIdAndJobApplicationId(userId, jobApplicationId);
    }

    @Test
    void unarchiveJobApplication_ValidUserIdAndJobApplicationId_ReturnsNoContent() throws Exception {
        ResponseEntity<Void> result = jobApplicationController.unarchiveJobApplication(userId, jobApplicationId);

        assertEquals(HttpStatus.NO_CONTENT, result.getStatusCode());
        verify(jobApplicationService).unarchiveByUserIdAndJobApplicationId(userId, jobApplicationId);
    }

    @Test
    void starJobApplication_ValidUserIdAndJobApplicationId_ReturnsGenericResponse() throws Exception {
        GenericResponse expectedResponse = new GenericResponse();

        when(jobApplicationService.updateStarredStatusByUserIdAndJobApplicationId(userId, jobApplicationId)).thenReturn(expectedResponse);

        GenericResponse result = jobApplicationController.starJobApplication(userId, jobApplicationId);

        assertEquals(expectedResponse, result);
        verify(jobApplicationService).updateStarredStatusByUserIdAndJobApplicationId(userId, jobApplicationId);
    }
}




