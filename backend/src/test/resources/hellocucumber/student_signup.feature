Feature: Student signup
  I would like to be able to signup as a Student


#  dd-MM-yyyy
  Scenario: I sign up as new Student
    Given My first name is "test", last name is "test". My email is "test@test.com" and my birthday is "04-10-1996"
    When I check my attending courses
    Then I should have 0 attending courses