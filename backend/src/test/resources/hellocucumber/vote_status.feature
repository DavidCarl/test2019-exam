Feature: Am i eligible to vote?
  Figuring out if I am eligible to vote for next semester courses.

  Scenario Outline: I have worked "<duration>" hours
    Given I worked "<duration>" hours
    When I ask whether I can vote
    Then I should be told "<answer>"
    Examples:
      | duration   | answer |
      | 20         | Yes    |
      | 19         | No     |
      | 5          | No     |
      | 42         | Yes    |