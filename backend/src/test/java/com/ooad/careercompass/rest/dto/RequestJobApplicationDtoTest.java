package com.ooad.careercompass.rest.dto;

import com.ooad.careercompass.utils.ApplicationStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

class RequestJobApplicationDtoTest {

    @Test
    void testRequestJobApplicationDto() {
        // Create a sample RequestJobApplicationDto instance
        RequestJobApplicationDto dto = new RequestJobApplicationDto();
        dto.setUserId(1);
        dto.setId(1);
        dto.setCompany("ABC Company");
        dto.setPosition("Software Engineer");
        dto.setStatus(ApplicationStatus.Applied);
        dto.setApplicationDate(LocalDate.of(2023, 4, 8));
        dto.setCompanyUrl("https://www.abccompany.com");
        dto.setNotes("Applied through company website");

        Set<Integer> jobTagIds = new HashSet<>();
        jobTagIds.add(1);
        jobTagIds.add(2);
        dto.setJobTagIds(jobTagIds);

        dto.setStarred(true);

        // Verify the values of the RequestJobApplicationDto instance
        Assertions.assertEquals(1, dto.getUserId());
        Assertions.assertEquals(1, dto.getId());
        Assertions.assertEquals("ABC Company", dto.getCompany());
        Assertions.assertEquals("Software Engineer", dto.getPosition());
        Assertions.assertEquals(ApplicationStatus.Applied, dto.getStatus());
        Assertions.assertEquals(LocalDate.of(2023, 4, 8), dto.getApplicationDate());
        Assertions.assertEquals("https://www.abccompany.com", dto.getCompanyUrl());
        Assertions.assertEquals("Applied through company website", dto.getNotes());
        Assertions.assertEquals(2, dto.getJobTagIds().size());
        Assertions.assertTrue(dto.getJobTagIds().contains(1));
        Assertions.assertTrue(dto.getJobTagIds().contains(2));
        Assertions.assertTrue(dto.getStarred());
    }
}