package com.ivanfranchin.orderapi.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@Entity
@Table(name = "job_application_job_tags")
public class JobApplicationJobTag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "job_app_tag_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "job_application_id", nullable = false)
    private JobApplication jobApplication;

    @ManyToOne
    @JoinColumn(name = "job_tag_id", nullable = false)
    private JobTag jobTag;

}
