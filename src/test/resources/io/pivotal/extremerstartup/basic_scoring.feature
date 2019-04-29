Feature: Basic Scoring

  Scenario: Player is correct
    Given a player "bob" who replies "4" with a status of "200":
    And the correct answer to every question is '4' worth 10 points
    When the player is entered
    And the game is played for 5 second
    Then the scores should be:
      | player | score |
      | bob    | 10pts |

  Scenario: Player is wrong
    Given a player "charlie" who replies "2" with a status of "200":
    And the correct answer to every question is '4' worth 10 points
    When the player is entered
    And the game is played for 5 second
    Then the scores should be:
      | player  | score  |
      | charlie | -10pts |

  Scenario: Player replies with server error
    Given a player "ernie" who replies "0" with a status of "500":
    And the correct answer to every question is '4'
    When the player is entered
    And the game is played for 5 second
    Then the scores should be:
      | player | score  |
      | ernie  | -50pts |

  Scenario: Player replies with client error
    Given a player "freddy" who replies "0" with a status of "404":
    And the correct answer to every question is '4'
    When the player is entered
    And the game is played for 5 second
    Then the scores should be:
      | player | score  |
      | freddy | -40pts |