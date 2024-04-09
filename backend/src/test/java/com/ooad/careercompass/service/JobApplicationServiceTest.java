package com.ooad.careercompass.service;

import com.ooad.careercompass.model.JobApplication;
import com.ooad.careercompass.model.JobTag;
import com.ooad.careercompass.model.User;
import com.ooad.careercompass.repository.JobApplicationJobTagRepository;
import com.ooad.careercompass.repository.JobApplicationRepository;
import com.ooad.careercompass.repository.JobTagRepository;
import com.ooad.careercompass.repository.UserRepository;
import com.ooad.careercompass.rest.dto.GenericResponse;
import com.ooad.careercompass.rest.dto.JobApplicationsDto;
import com.ooad.careercompass.rest.dto.RequestJobApplicationDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class JobApplicationServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private JobApplicationRepository jobApplicationRepository;

    @Mock
    private JobTagRepository jobTagRepository;

    @Mock
    private JobApplicationJobTagRepository jobApplicationJobTagRepository;

    @InjectMocks
    private JobApplicationService jobApplicationService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllArchivedJobApplications() {
        // Arrange
        Integer userId = 1;
        List<JobApplication> jobApplications = new ArrayList<>();
        when(jobApplicationRepository.getAllCurrentApplicationsByUserIdAndStatus(userId, true))
                .thenReturn((ArrayList<JobApplication>) jobApplications);

        // Act
        List<JobApplicationsDto> result = jobApplicationService.getAllArchivedJobApplications(userId);

        // Assert
        assertEquals(jobApplications.size(), result.size());
        verify(jobApplicationRepository, times(1)).getAllCurrentApplicationsByUserIdAndStatus(userId, true);
    }

    @Test
    public void testGetAllUnarchivedJobApplications() {
        // Arrange
        Integer userId = 1;
        List<JobApplication> jobApplications = new ArrayList<>();
        when(jobApplicationRepository.getAllCurrentApplicationsByUserIdAndStatus(userId, false))
                .thenReturn((ArrayList<JobApplication>) jobApplications);

        // Act
        List<JobApplicationsDto> result = jobApplicationService.getAllUnarchivedJobApplications(userId);

        // Assert
        assertEquals(jobApplications.size(), result.size());
        verify(jobApplicationRepository, times(1)).getAllCurrentApplicationsByUserIdAndStatus(userId, false);
    }

    @Test
    public void testArchiveByUserIdAndJobApplicationId() throws Exception {
        // Arrange
        Integer userId = 1;
        Integer jobApplicationId = 1;
        JobApplication jobApplication = new JobApplication();
        User user = new User();
        user.setId(userId);
        jobApplication.setUser(user);
        when(jobApplicationRepository.findById(jobApplicationId)).thenReturn(Optional.of(jobApplication));

        // Act
        jobApplicationService.archiveByUserIdAndJobApplicationId(userId, jobApplicationId);

        // Assert
        verify(jobApplicationRepository, times(1)).findById(jobApplicationId);
        verify(jobApplicationRepository, times(1)).save(jobApplication);
        assertTrue(jobApplication.isDeleted());
    }

    @Test
    public void testUnarchiveByUserIdAndJobApplicationId() throws Exception {
        // Arrange
        Integer userId = 1;
        Integer jobApplicationId = 1;
        JobApplication jobApplication = new JobApplication();
        User user = new User();
        user.setId(userId);
        jobApplication.setUser(user);
        when(jobApplicationRepository.findById(jobApplicationId)).thenReturn(Optional.of(jobApplication));

        // Act
        jobApplicationService.unarchiveByUserIdAndJobApplicationId(userId, jobApplicationId);

        // Assert
        verify(jobApplicationRepository, times(1)).findById(jobApplicationId);
        verify(jobApplicationRepository, times(1)).save(jobApplication);
        assertFalse(jobApplication.isDeleted());
    }

    @Test
    public void testGetByUserIdAndJobApplicationId() throws Exception {
        // Arrange
        Integer userId = 1;
        Integer jobApplicationId = 1;
        JobApplication jobApplication = new JobApplication();
        User user = new User();
        user.setId(userId);
        jobApplication.setUser(user);
        when(jobApplicationRepository.findById(jobApplicationId)).thenReturn(Optional.of(jobApplication));

        // Act
        JobApplicationsDto result = jobApplicationService.getByUserIdAndJobApplicationId(userId, jobApplicationId);

        // Assert
        verify(jobApplicationRepository, times(1)).findById(jobApplicationId);
        assertNotNull(result);
    }

    @Test
    public void testUpdateStarredStatusByUserIdAndJobApplicationId() throws Exception {
        // Arrange
        Integer userId = 1;
        Integer jobApplicationId = 1;
        JobApplication jobApplication = new JobApplication();
        User user = new User();
        user.setId(userId);
        jobApplication.setUser(user);
        when(jobApplicationRepository.findById(jobApplicationId)).thenReturn(Optional.of(jobApplication));

        // Act
        GenericResponse result = jobApplicationService.updateStarredStatusByUserIdAndJobApplicationId(userId, jobApplicationId);

        // Assert
        verify(jobApplicationRepository, times(1)).findById(jobApplicationId);
        verify(jobApplicationRepository, times(1)).save(jobApplication);
        assertEquals("Success", result.getStatus());
        assertEquals("Updated Starred Status Successfully", result.getMessage());
    }

    @Test
    public void testAddJobApplication() throws Exception {
        // Arrange
        Integer userId = 1;
        RequestJobApplicationDto requestJobApplicationDto = new RequestJobApplicationDto();
        requestJobApplicationDto.setUserId(userId);
        requestJobApplicationDto.setStarred(false);
        // Set other necessary fields
        User user = new User();
        user.setId(userId);
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(jobApplicationRepository.save(any(JobApplication.class))).thenReturn(new JobApplication());

        // Act
        GenericResponse result = jobApplicationService.addJobApplication(requestJobApplicationDto);

        // Assert
        verify(userRepository, times(1)).findById(userId);
        verify(jobApplicationRepository, times(1)).save(any(JobApplication.class));
        assertEquals("Success", result.getStatus());
        assertEquals("Added Job Application Successfully", result.getMessage());
    }

    @Test
    public void testUpdateJobApplication() throws Exception {
        // Arrange
        Integer userId = 1;
        Integer jobApplicationId = 1;
        RequestJobApplicationDto requestJobApplicationDto = new RequestJobApplicationDto();
        requestJobApplicationDto.setUserId(userId);
        requestJobApplicationDto.setId(jobApplicationId);
        requestJobApplicationDto.setStarred(false);
        // Set other necessary fields
        User user = new User();
        user.setId(userId);
        JobApplication jobApplication = new JobApplication();
        jobApplication.setId(jobApplicationId);
        jobApplication.setUser(user);
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(jobApplicationRepository.findById(jobApplicationId)).thenReturn(Optional.of(jobApplication));
        when(jobApplicationRepository.save(any(JobApplication.class))).thenReturn(jobApplication);

        // Act
        GenericResponse result = jobApplicationService.updateJobApplication(requestJobApplicationDto);

        // Assert
        verify(userRepository, times(1)).findById(userId);
        verify(jobApplicationRepository, times(1)).findById(jobApplicationId);
        verify(jobApplicationRepository, times(1)).save(any(JobApplication.class));
        assertEquals("Success", result.getStatus());
        assertEquals("Updated Job Application Successfully", result.getMessage());
    }
}