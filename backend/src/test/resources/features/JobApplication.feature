Feature: Job Application Management

  Scenario: Create a new job application
    Given an user with registered email fireflies186@gmail.com
    When the user submits a request to create a new job application
    Then the application should add the job application successfully
    And the application should return a success message

  Scenario: Retrieve all the job applications
    Given an user with registered email fireflies186@gmail.com
    When the user requests to view all unarchived job applications
    Then the application should return a list of unarchived job applications

  Scenario: Retrieve a specific job application
    Given an user with registered email fireflies186@gmail.com
    And a existing job application with ID 2
    When the user requests to view the job application with ID 2
    Then the application should return the details of the job application

  Scenario: Update an existing job application
    Given an user with registered email fireflies186@gmail.com
    And an existing job application with ID 2
    When the user submits a request to update the job application with ID 2
    Then the application should update the job application successfully
    And the application should return a success message

  Scenario: Delete/Archive a job application
    Given a user with registered email fireflies186@gmail.com
    And an existing job application with ID 2
    When the user requests to archive/delete the job application with ID 2
    Then the application should archive the job application successfully
    And the application should return a success message

  Scenario: Star a job application
    Given a user with registered email fireflies186@gmail.com
    And an existing job application with ID 2
    When the user requests to star the job application with ID 2
    Then the application should mark the job application as starred
    And the application should return a success message
