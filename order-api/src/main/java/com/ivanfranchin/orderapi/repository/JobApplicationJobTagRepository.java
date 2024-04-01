package com.ivanfranchin.orderapi.repository;

import com.ivanfranchin.orderapi.model.JobApplicationJobTag;
import com.ivanfranchin.orderapi.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface JobApplicationJobTagRepository extends JpaRepository<JobApplicationJobTag, Long> {
}
