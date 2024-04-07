```feature
Feature: User Login
  As a registered user
  I want to log in to my account
  So that I can access the features of the application

  Scenario: Successful login
    Given I am on the login page
    When I enter valid login credentials
      | Email                | Password  |
      | fireflies186@gmail.com | Admin@123 |
    And I submit the login form
    Then I receive a response containing an access token and my profile details

  Scenario: Unsuccessful login
    Given I am on the login page
    When I enter invalid login credentials
      | Email                | Password  |
      | fireflies186@gmail.com | Apple@123 |
    And I submit the login form
    Then I should see an error message