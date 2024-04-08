package com.ooad.careercompass.rest.controller;

import com.ooad.careercompass.rest.dto.GenericResponse;
import com.ooad.careercompass.rest.dto.JobTagDto;
import com.ooad.careercompass.rest.dto.RequestJobTagDto;
import com.ooad.careercompass.service.JobTagService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class JobTagsControllerTest {

    @Mock
    private JobTagService jobTagService;

    @InjectMocks
    private JobTagsController jobTagsController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllTags_shouldReturnListOfJobTagDto() throws Exception {
        // Arrange
        Integer userId = 1;
        List<JobTagDto> expectedTags = Arrays.asList(
                new JobTagDto(),
                new JobTagDto()
        );
        when(jobTagService.getAllTagsByUserId(userId)).thenReturn(expectedTags);

        // Act
        List<JobTagDto> result = jobTagsController.getAllTags(userId);

        // Assert
        assertEquals(expectedTags.size(), result.size());
        for (int i = 0; i < expectedTags.size(); i++) {
            assertEquals(expectedTags.get(i).getId(), result.get(i).getId());
            assertEquals(expectedTags.get(i).getName(), result.get(i).getName());
        }
        verify(jobTagService, times(1)).getAllTagsByUserId(userId);
    }

    @Test
    void createTag_shouldReturnGenericResponse() throws Exception {
        // Arrange
        RequestJobTagDto requestJobTagDto = new RequestJobTagDto();
        GenericResponse expectedResponse = new GenericResponse();
        expectedResponse.setStatus("SUCCESS");
        expectedResponse.setMessage("Tag created successfully");
        expectedResponse.setAccountVerified(true);
        expectedResponse.setUserAccountPresent(true);
        when(jobTagService.createNewTagByUserIdAndTagName(requestJobTagDto)).thenReturn(expectedResponse);

        // Act
        GenericResponse result = jobTagsController.createTag(requestJobTagDto);

        // Assert
        assertEquals(expectedResponse.getStatus(), result.getStatus());
        assertEquals(expectedResponse.getMessage(), result.getMessage());
        assertEquals(expectedResponse.isAccountVerified(), result.isAccountVerified());
        assertEquals(expectedResponse.isUserAccountPresent(), result.isUserAccountPresent());
        verify(jobTagService, times(1)).createNewTagByUserIdAndTagName(requestJobTagDto);
    }

    @Test
    void updateTag_shouldReturnGenericResponse() throws Exception {
        // Arrange
        RequestJobTagDto requestJobTagDto = new RequestJobTagDto();
        GenericResponse expectedResponse = new GenericResponse();
        expectedResponse.setStatus("SUCCESS");
        expectedResponse.setMessage("Tag updated successfully");
        expectedResponse.setAccountVerified(true);
        expectedResponse.setUserAccountPresent(true);
        when(jobTagService.updateTagByUserIdAndTagId(requestJobTagDto)).thenReturn(expectedResponse);

        // Act
        GenericResponse result = jobTagsController.updateTag(requestJobTagDto);

        // Assert
        assertEquals(expectedResponse.getStatus(), result.getStatus());
        assertEquals(expectedResponse.getMessage(), result.getMessage());
        assertEquals(expectedResponse.isAccountVerified(), result.isAccountVerified());
        assertEquals(expectedResponse.isUserAccountPresent(), result.isUserAccountPresent());
        verify(jobTagService, times(1)).updateTagByUserIdAndTagId(requestJobTagDto);
    }
}