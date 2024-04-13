package com.ooad.careercompass.rest.controller;
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
    private List<JobApplicationsDto> allJobApplications;
    @BeforeEach
    void setUp() {
        userId = 1;
        jobApplicationId = 1;
        requestJobApplicationDto = new RequestJobApplicationDto();
        allJobApplications = new ArrayList<>();
        JobTagDto jobTag=new JobTagDto();
        jobTag.setId(1);
        jobTag.setName("tag1");
        Set<JobTagDto> jobTags = new HashSet<>();
        jobTags.add(jobTag);
        JobApplicationsDto jobApplicationDto = new JobApplicationsDto();
        jobApplicationDto.setId(2);
        jobApplicationDto.setCompany("Google");
        jobApplicationDto.setPosition("SDE");
        jobApplicationDto.setStatus(ApplicationStatus.Accepted);
        jobApplicationDto.setApplicationDate(LocalDate.now());
        jobApplicationDto.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
        jobApplicationDto.setCompanyUrl("https://www.google.com/");
        jobApplicationDto.setStarred(false);
        jobApplicationDto.setArchived(true);
        jobApplicationDto.setNotes("notes");
        jobApplicationDto.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        jobApplicationDto.setJobTags(jobTags);
        allJobApplications.add(jobApplicationDto);
    }

    @Test
    void getAllJobApplications_ReturnsListOfJobApplicationsDto() {
        List<JobApplicationsDto> expectedJobApplications = allJobApplications;

        when(jobApplicationService.getAllJobApplications(userId)).thenReturn(expectedJobApplications);

        List<JobApplicationsDto> result = jobApplicationController.getAllJobApplications(userId);

        assertEquals(expectedJobApplications, result);
        verify(jobApplicationService).getAllJobApplications(userId);
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
    void starJobApplication_ValidUserIdAndJobApplicationId_ReturnsGenericResponse() throws Exception {
        GenericResponse expectedResponse = new GenericResponse();

        when(jobApplicationService.updateStarredStatusByUserIdAndJobApplicationId(userId, jobApplicationId)).thenReturn(expectedResponse);

        GenericResponse result = jobApplicationController.updateJobApplicationStarredStatus(userId, jobApplicationId);

        assertEquals(expectedResponse, result);
        verify(jobApplicationService).updateStarredStatusByUserIdAndJobApplicationId(userId, jobApplicationId);
    }
}




