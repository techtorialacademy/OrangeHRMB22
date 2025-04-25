Feature: OrangeHRM Login Functionality

  Background: # This step is going to be executed before every scenario
    Given I am on the OrangeHRM login page


  Scenario: Login with valid credentials
    When User enters valid credentials
      | username | password |
      | Admin    | admin123 |
    Then User should be redirected to dashboard page


  Scenario Outline: Login with invalid credentials
    When User enters invalid credentials "<username>" "<password>"
    Then User should see the "Invalid credentials" error message
    Examples:
      | username | password |
      | Admin    | adm12    |
      | Adm      | admin123 |
      | Adm      | adm      |
      | admin123 | admin123 |
      | Admin    | Admin    |

    Scenario Outline: Login with missing credentials
      When User misses a credential field "<username>" or "<password>"
      Then User should see a "Required" message for the "<expected>" field
      Examples:
        | username | password | expected |
        |          | some     | username |
        | Admin    |          | password |
        |          |          | both     |




