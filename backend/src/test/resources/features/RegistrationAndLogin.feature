Feature: User Authentication and Registration

  Scenario: User login with valid credentials
    Given a user with email "fireflies186@gmail.com" and password "Admin@123"
    When the user submits valid login credentials
    Then the application should authenticate the user successfully
    And the application should generate a JWT token
    And the application should return the authentication response with the token

  Scenario: User login with invalid credentials
    Given a user with email "fireflies186@gmail.com" and password "wrongpassword"
    When the user submits invalid login credentials
    Then the application should reject the authentication attempt
    And the application should return an error message

  Scenario: User sign up with existing email
    Given a user with email "fireflies186@gmail.com" already exists
    When a new user attempts to sign up with the same email
    Then the application should reject the sign-up request
    And the application should return an error message
