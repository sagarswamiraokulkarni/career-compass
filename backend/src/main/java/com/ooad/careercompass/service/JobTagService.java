package com.ooad.careercompass.service;

import com.ooad.careercompass.model.JobApplication;
import com.ooad.careercompass.model.JobTag;
import com.ooad.careercompass.model.User;
import com.ooad.careercompass.repository.JobApplicationJobTagRepository;
import com.ooad.careercompass.repository.JobApplicationRepository;
import com.ooad.careercompass.repository.JobTagRepository;
import com.ooad.careercompass.repository.UserRepository;
import com.ooad.careercompass.rest.dto.*;
import com.ooad.careercompass.rest.dto.JobApplicationsDto;
import com.ooad.careercompass.utils.CareerCompassUtils;
import com.ooad.careercompass.rest.dto.GenericResponse;
import com.ooad.careercompass.rest.dto.JobTagDto;
import com.ooad.careercompass.rest.dto.RequestJobTagDto;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
@Service
public class JobTagService {
    private final UserRepository userRepository;
    private final JobApplicationRepository jobApplicationRepository;
    private final JobTagRepository jobTagRepository;
//    private final JobApplicationJobTagRepository jobApplicationJobTagRepository;
//    private final UserService userService;
    public List<JobTagDto> getAllTagsByUserId(Integer userId) throws Exception {
        List<JobTag> jobTagList=jobTagRepository.findByUserId(userId);
        List<JobTagDto> jobTagDtoList=new ArrayList<>();
        if(!jobTagList.isEmpty()) {
            jobTagDtoList = CareerCompassUtils.gsonMapperList(jobTagList, JobTagDto.class);
        }
        return jobTagDtoList;
    }

    @Transactional
    public GenericResponse createNewTagByUserIdAndTagName(RequestJobTagDto requestJobTagDto) throws Exception {
        GenericResponse genericResponse=new GenericResponse();
        User user=userRepository.findById(requestJobTagDto.getUserId()).orElseThrow(()-> new Exception("User not found"));
        JobTag jobTag=jobTagRepository.findByUserIdAndTagName(requestJobTagDto.getUserId(),requestJobTagDto.getName());
        if(jobTag==null){
            jobTag=new JobTag();
            jobTag.setUser(user);
            jobTag.setName(requestJobTagDto.getName());
            jobTagRepository.save(jobTag);
        }else{
            throw new Exception("Tag Already Exists");
        }
        genericResponse.setStatus("Success");
        genericResponse.setMessage("Added Job Tag Successfully");
        return genericResponse;
    }

    @Transactional
    public GenericResponse updateTagByUserIdAndTagId(RequestJobTagDto requestJobTagDto) throws Exception {
        GenericResponse genericResponse=new GenericResponse();
        JobTag jobTag=jobTagRepository.findById(requestJobTagDto.getId()).orElseThrow(()-> new Exception("Tag not found"));
        if(!jobTag.getUser().getId().equals(requestJobTagDto.getUserId())){
            throw new Exception("Unauthorized Access");
        }
        jobTag.setName(requestJobTagDto.getName());
        jobTagRepository.save(jobTag);
        genericResponse.setStatus("Success");
        genericResponse.setMessage("Updated Job Tag Successfully");
        return genericResponse;
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
//
//    public GenericResponse updateStarredStatusByUserIdAndJobApplicationId(Integer userId, Integer jobApplicationId) throws Exception {
//        GenericResponse genericResponse=new GenericResponse();
//        JobApplication jobApplication = jobApplicationRepository.findById(jobApplicationId)
//                .orElseThrow(() -> new Exception("Job application not found with ID: " + jobApplicationId));
//        if(!jobApplication.getUser().getId().equals(userId)){
//            throw new Exception("Unauthorized Access");
//        }
//        jobApplication.setStarred(!jobApplication.isStarred());
//        jobApplicationRepository.save(jobApplication);
//        genericResponse.setStatus("Success");
//        genericResponse.setMessage("Updated Starred Status Successfully");
//        return genericResponse;
//    }
//
//    @Transactional
//    public GenericResponse addJobApplication(RequestJobApplicationDto requestJobApplicationDto) throws Exception {
//        GenericResponse genericResponse=new GenericResponse();
//        requestJobApplicationDto.setId(null);
//        JobApplication jobApplication = CareerCompassUtils.gsonMapper(requestJobApplicationDto, JobApplication.class);
//        User user=userRepository.findById(requestJobApplicationDto.getUserId()).orElseThrow(()-> new Exception("User not found"));
//        jobApplication.setUser(user);
//        jobApplication.setCreatedAt(new Timestamp(System.currentTimeMillis()));
//        jobApplication.setStarred(false);
//
//        Date date = new Date();
//        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
//
//        jobApplication.setApplicationDate(localDate);
//        jobApplication.setDeleted(false);
//        jobApplication=jobApplicationRepository.save(jobApplication);
//        List<JobTag> jobTagsList=jobTagRepository.findAllById(requestJobApplicationDto.getJobTagIds());
//        for(JobTag jobTag:jobTagsList){
//            JobApplicationJobTag jobApplicationJobTag=new JobApplicationJobTag();
//            jobApplicationJobTag.setJobApplication(jobApplication);
//            jobApplicationJobTag.setJobTag(jobTag);
//            jobApplicationJobTagRepository.save(jobApplicationJobTag);
//        }
//
//        genericResponse.setStatus("Success");
//        genericResponse.setMessage("Added Job Application Successfully");
//        return genericResponse;
//    }
//
//    @Transactional
//    public GenericResponse updateJobApplication(RequestJobApplicationDto requestJobApplicationDto) throws Exception {
//        GenericResponse genericResponse=new GenericResponse();
//        User user=userRepository.findById(requestJobApplicationDto.getUserId()).orElseThrow(()-> new Exception("User not found"));
//        JobApplication jobApplication = jobApplicationRepository.findById(requestJobApplicationDto.getId()).orElseThrow(()-> new Exception("Job Application not found"));
//        if(!user.getId().equals(jobApplication.getUser().getId())){
//            throw new Exception("Unauthorized");
//        }
//        jobApplication.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
//        jobApplication.setCompany(requestJobApplicationDto.getCompany());
//        jobApplication.setCompanyUrl(requestJobApplicationDto.getCompanyUrl());
//        jobApplication.setNotes(requestJobApplicationDto.getNotes());
//        jobApplication.setStatus(requestJobApplicationDto.getStatus());
//        jobApplication.setPosition(requestJobApplicationDto.getPosition());
//        jobApplication=jobApplicationRepository.save(jobApplication);
//
//        List<JobApplicationJobTag> jobApplicationJobTagsList=jobApplicationJobTagRepository.getAllAssociatedTagsByJobApplicationId(jobApplication.getId());
//
//        for(JobApplicationJobTag jobApplicationJobTag:jobApplicationJobTagsList){
//            if(!requestJobApplicationDto.getJobTagIds().contains(jobApplicationJobTag.getJobTag().getId())){
//                jobApplicationJobTagRepository.delete(jobApplicationJobTag);
//            }else{
//                requestJobApplicationDto.getJobTagIds().remove(jobApplicationJobTag.getJobTag().getId());
//            }
//        }
//
//        List<JobTag> jobTagsList=jobTagRepository.findAllById(requestJobApplicationDto.getJobTagIds());
//        for(JobTag jobTag:jobTagsList){
//            JobApplicationJobTag jobApplicationJobTag=new JobApplicationJobTag();
//            jobApplicationJobTag.setJobApplication(jobApplication);
//            jobApplicationJobTag.setJobTag(jobTag);
//            jobApplicationJobTagRepository.save(jobApplicationJobTag);
//        }
//
//        genericResponse.setStatus("Success");
//        genericResponse.setMessage("Updated Job Application Successfully");
//        return genericResponse;
//    }

}
