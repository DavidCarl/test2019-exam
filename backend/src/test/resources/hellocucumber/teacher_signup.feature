Feature: Teacher signup
  I would like to be able to signup as a teacher

  Scenario: I sign up as new teacher
    Given My name is "test", email is "test@test.com" and my background is "Datamatiker"
    When When I check my course list
    Then I should have 0 courses