package com.ivanfranchin.orderapi.rest.dto;

import com.ivanfranchin.orderapi.model.JobTag;
import com.ivanfranchin.orderapi.model.User;
import com.ivanfranchin.orderapi.utils.ApplicationStatus;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
@Getter
@Setter
public class JobApplicationsDto {
        private Integer id;

        private String company;

        private String position;

        private ApplicationStatus status;

        private LocalDate applicationDate;

        private Timestamp updatedAt;

        private String companyUrl;

        private boolean starred;

        private boolean isDeleted;

        private String notes;

        private Timestamp createdAt;

        private Set<JobTagDto> jobTags = new HashSet<>();

}
