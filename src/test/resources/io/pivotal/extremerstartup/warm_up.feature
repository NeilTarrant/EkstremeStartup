Feature: Warm up

  Background:
    Given the server is running in warm-up mode

  Scenario: The answer bob is always correct for the player bob
    Given a player "bob" who replies "bob" with a status of "200":
    When the player is entered
    And the game is played for 5 second
    Then the scores should be:
      | player | score |
      | bob    | 1pts  |
    And the log for bob should show:
      | question          | response | points |
      | what is your name | bob      | 1 pt   |

  Scenario: The answer Sébastian is always correct for the player Sébastian
    Given a player "Sébastian" who replies "Sébastian" with a status of "200":
    When the player is entered
    And the game is played for 5 second
    Then the scores should be:
      | player    | score |
      | Sébastian | 1pts  |
    And the log for Sébastian should show:
      | question          | response  | points |
      | what is your name | Sébastian | 1 pt   |
