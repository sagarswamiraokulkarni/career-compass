package com.ivanfranchin.orderapi.service;

import com.ivanfranchin.orderapi.model.JobApplication;
import com.ivanfranchin.orderapi.model.JobApplicationJobTag;
import com.ivanfranchin.orderapi.model.JobTag;
import com.ivanfranchin.orderapi.model.User;
import com.ivanfranchin.orderapi.repository.JobApplicationJobTagRepository;
import com.ivanfranchin.orderapi.repository.JobApplicationRepository;
import com.ivanfranchin.orderapi.repository.JobTagRepository;
import com.ivanfranchin.orderapi.repository.UserRepository;
import com.ivanfranchin.orderapi.rest.dto.GenericResponse;
import com.ivanfranchin.orderapi.rest.dto.JobApplicationsDto;
import com.ivanfranchin.orderapi.rest.dto.JobTagDto;
import com.ivanfranchin.orderapi.rest.dto.RequestJobApplicationDto;
import com.ivanfranchin.orderapi.utils.ApplicationStatus;
import com.ivanfranchin.orderapi.utils.CareerCompassUtils;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

@RequiredArgsConstructor
@Service
public class JobApplicationService {
    private final UserRepository userRepository;
    private final JobApplicationRepository jobApplicationRepository;
    private final JobTagRepository jobTagRepository;
    private final JobApplicationJobTagRepository jobApplicationJobTagRepository;
    private final UserService userService;
    public List<JobApplicationsDto> getAllJobApplicationsByUserId(Integer userId){
        System.out.println(ApplicationStatus.YetToApply.toString());
        ArrayList<JobApplication> jobApplicationsList=jobApplicationRepository.getAllCurrentApplicationsByUserId(userId);
//        ArrayList<JobApplicationsDto> jobApplicationsDtoList=new ArrayList<>();
//        for(JobApplication jobApplication:jobApplicationsList){
//            jobApplicationsDtoList.add(CareerCompassUtils.gsonMapper(jobApplication, JobApplicationsDto.class));
//        }
        List<JobApplicationsDto> jobApplicationsDtoList = CareerCompassUtils.gsonMapperList(jobApplicationsList, JobApplicationsDto.class);

// Map the jobTags set for each JobApplicationDto
        for (int i = 0; i < jobApplicationsList.size(); i++) {
            JobApplication jobApplication = jobApplicationsList.get(i);
            JobApplicationsDto jobApplicationDto = jobApplicationsDtoList.get(i);

            Set<JobTagDto> jobTagDtos = CareerCompassUtils.gsonMapperSet(jobApplication.getJobTags(), JobTagDto.class);
            jobApplicationDto.setJobTags(jobTagDtos);
        }
//        System.out.println(jobApplicationsList.size());
        return jobApplicationsDtoList;
    }

    public void deleteByUserIdAndJobApplicationId(Integer userId,Integer jobApplicationId) throws Exception {
        // Retrieve the job application by ID
        JobApplication jobApplication = jobApplicationRepository.findById(jobApplicationId)
                .orElseThrow(() -> new Exception("Job application not found with ID: " + jobApplicationId));
        if(!jobApplication.getUser().getId().equals(userId)){
            throw new Exception("Unauthorized Access");
        }
        jobApplication.setDeleted(true);
        jobApplicationRepository.save(jobApplication);
    }
    public JobApplicationsDto getByUserIdAndJobApplicationId(Integer userId, Integer jobApplicationId) throws Exception {
        // Retrieve the job application by ID
        JobApplication jobApplication = jobApplicationRepository.findById(jobApplicationId)
                .orElseThrow(() -> new Exception("Job application not found with ID: " + jobApplicationId));
        if(!jobApplication.getUser().getId().equals(userId)){
            throw new Exception("Unauthorized Access");
        }
        JobApplicationsDto jobApplicationDto = CareerCompassUtils.gsonMapper(jobApplication, JobApplicationsDto.class);

        Set<JobTagDto> jobTagDtos = CareerCompassUtils.gsonMapperSet(jobApplication.getJobTags(), JobTagDto.class);
        jobApplicationDto.setJobTags(jobTagDtos);
        return jobApplicationDto;
    }

    public GenericResponse updateStarredStatusByUserIdAndJobApplicationId(Integer userId, Integer jobApplicationId) throws Exception {
        GenericResponse genericResponse=new GenericResponse();
        JobApplication jobApplication = jobApplicationRepository.findById(jobApplicationId)
                .orElseThrow(() -> new Exception("Job application not found with ID: " + jobApplicationId));
        if(!jobApplication.getUser().getId().equals(userId)){
            throw new Exception("Unauthorized Access");
        }
        jobApplication.setStarred(!jobApplication.isStarred());
        jobApplicationRepository.save(jobApplication);
        genericResponse.setStatus("Success");
        genericResponse.setMessage("Updated Starred Status Successfully");
        return genericResponse;
    }

    @Transactional
    public GenericResponse addJobApplication(RequestJobApplicationDto requestJobApplicationDto) throws Exception {
        GenericResponse genericResponse=new GenericResponse();
        requestJobApplicationDto.setId(null);
        JobApplication jobApplication = CareerCompassUtils.gsonMapper(requestJobApplicationDto, JobApplication.class);
        User user=userRepository.findById(requestJobApplicationDto.getUserId()).orElseThrow(()-> new Exception("User not found"));
//        TODO: Builder Pattern
        JobApplication.builder().applicationDate(new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDate())
                .company(requestJobApplicationDto.getCompany()).createdAt(new Timestamp(System.currentTimeMillis())).isDeleted(false)
                .companyUrl(requestJobApplicationDto.getCompanyUrl()).starred(false).position(requestJobApplicationDto.getPosition())
                .user(user).position(requestJobApplicationDto.getPosition()).status(requestJobApplicationDto.getStatus()).build();
        jobApplication=jobApplicationRepository.save(jobApplication);
        List<JobTag> jobTagsList=jobTagRepository.findAllById(requestJobApplicationDto.getJobTagIds());
        for(JobTag jobTag:jobTagsList){
            JobApplicationJobTag jobApplicationJobTag=new JobApplicationJobTag();
            jobApplicationJobTag.setJobApplication(jobApplication);
            jobApplicationJobTag.setJobTag(jobTag);
            jobApplicationJobTagRepository.save(jobApplicationJobTag);
        }

        genericResponse.setStatus("Success");
        genericResponse.setMessage("Added Job Application Successfully");
        return genericResponse;
    }

    @Transactional
    public GenericResponse updateJobApplication(RequestJobApplicationDto requestJobApplicationDto) throws Exception {
        GenericResponse genericResponse=new GenericResponse();
        User user=userRepository.findById(requestJobApplicationDto.getUserId()).orElseThrow(()-> new Exception("User not found"));
        JobApplication jobApplication = jobApplicationRepository.findById(requestJobApplicationDto.getId()).orElseThrow(()-> new Exception("Job Application not found"));
        if(!user.getId().equals(jobApplication.getUser().getId())){
            throw new Exception("Unauthorized");
        }
        jobApplication.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
        jobApplication.setCompany(requestJobApplicationDto.getCompany());
        jobApplication.setCompanyUrl(requestJobApplicationDto.getCompanyUrl());
        jobApplication.setNotes(requestJobApplicationDto.getNotes());
        jobApplication.setStatus(requestJobApplicationDto.getStatus());
        jobApplication.setPosition(requestJobApplicationDto.getPosition());
        jobApplication=jobApplicationRepository.save(jobApplication);

        List<JobApplicationJobTag> jobApplicationJobTagsList=jobApplicationJobTagRepository.getAllAssociatedTagsByJobApplicationId(jobApplication.getId());

        for(JobApplicationJobTag jobApplicationJobTag:jobApplicationJobTagsList){
            if(!requestJobApplicationDto.getJobTagIds().contains(jobApplicationJobTag.getJobTag().getId())){
                jobApplicationJobTagRepository.delete(jobApplicationJobTag);
            }else{
                requestJobApplicationDto.getJobTagIds().remove(jobApplicationJobTag.getJobTag().getId());
            }
        }

        List<JobTag> jobTagsList=jobTagRepository.findAllById(requestJobApplicationDto.getJobTagIds());
        for(JobTag jobTag:jobTagsList){
            JobApplicationJobTag jobApplicationJobTag=new JobApplicationJobTag();
            jobApplicationJobTag.setJobApplication(jobApplication);
            jobApplicationJobTag.setJobTag(jobTag);
            jobApplicationJobTagRepository.save(jobApplicationJobTag);
        }

        genericResponse.setStatus("Success");
        genericResponse.setMessage("Updated Job Application Successfully");
        return genericResponse;
    }

}
