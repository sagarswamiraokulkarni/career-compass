Feature: User Registration
  As a new user
  I want to register for an account
  So that I can access the features of the application

  Scenario: Successful registration
    Given I am on the registration page
    When I enter valid registration details
    And I submit the registration form
    Then I should see a success message
    And I should be redirected to the login page

  Scenario: Unsuccessful registration
    Given I am on the registration page
    When I enter invalid registration details
    And I submit the registration form
    Then I should see an error message
    And I should remain on the registration page
