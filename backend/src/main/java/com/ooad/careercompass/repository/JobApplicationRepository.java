package com.ooad.careercompass.repository;

import com.ooad.careercompass.model.JobApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface JobApplicationRepository extends JpaRepository<JobApplication, Integer> {
    @Query(value = "SELECT * FROM job_applications WHERE user_id=?1 AND is_deleted=FALSE", nativeQuery = true)
    ArrayList<JobApplication> getAllCurrentApplicationsByUserId(Integer userId);

//    @Query(value = "SELECT * FROM job_applications WHERE user_id=?1 AND id=?2", nativeQuery = true)
//    ArrayList<JobApplication> getAllCurrentApplicationsByUserIdAndJobApplicationId(Integer userId,Integer id);
}
