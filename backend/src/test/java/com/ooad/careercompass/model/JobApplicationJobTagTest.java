package com.ooad.careercompass.model;

//import com.ooad.careercompass.model.JobApplication;
//import com.ooad.careercompass.model.JobApplicationJobTag;
//import com.ooad.careercompass.model.JobTag;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class JobApplicationJobTagTest {

    @Test
    public void testCreateJobApplicationJobTag() {
        // Create a new JobApplicationJobTag instance
        JobApplicationJobTag jobAppTag = new JobApplicationJobTag();

        // Create a mock JobApplication and JobTag
        JobApplication jobApplication = new JobApplication();
        JobTag jobTag = new JobTag();

        // Set the JobApplication and JobTag for the JobApplicationJobTag
        jobAppTag.setJobApplication(jobApplication);
        jobAppTag.setJobTag(jobTag);

        // Assert that the JobApplication and JobTag are set correctly
        Assertions.assertEquals(jobApplication, jobAppTag.getJobApplication());
        Assertions.assertEquals(jobTag, jobAppTag.getJobTag());
    }

    @Test
    public void testJobApplicationJobTagId() {
        // Create a new JobApplicationJobTag instance
        JobApplicationJobTag jobAppTag = new JobApplicationJobTag();

        // Set the ID for the JobApplicationJobTag
        jobAppTag.setId(1);

        // Assert that the ID is set correctly
        Assertions.assertEquals(1, jobAppTag.getId());
    }


}