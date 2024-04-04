package com.ivanfranchin.orderapi.repository;

import com.ivanfranchin.orderapi.model.JobApplication;
import com.ivanfranchin.orderapi.model.JobApplicationJobTag;
import com.ivanfranchin.orderapi.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Optional;

@Repository
public interface JobApplicationJobTagRepository extends JpaRepository<JobApplicationJobTag, Integer> {

    @Query(value = "SELECT * FROM job_application_job_tags WHERE job_application_id=?1 AND job_tag_id=?2", nativeQuery = true)
    JobApplicationJobTag getAllAssociatedTagsByJobApplicationIdAndJobTagId(Integer jobApplicationId,Integer jobTagId);

    @Query(value = "SELECT * FROM job_application_job_tags WHERE job_application_id=?1", nativeQuery = true)
    ArrayList<JobApplicationJobTag> getAllAssociatedTagsByJobApplicationId(Integer jobApplicationId);
}
