@filterByPrice
Feature: Validating price filter functionality

  Scenario: Validating price by filter
    Given User is on the FirstCry home page for price filter
    And User searches for "Boys t shirt" and waits
    And User clicks the search button
    And User applies 3 brand filters
    When User selects price range "0 to 250"
    Then Price filter results are displayed for "0 to 250"
    And User selects price range "250 to 500" after 3 seconds
    Then Price filter results are displayed for "250 to 500"
    And User selects price range "500 to 1000" after 3 seconds
    Then Price filter results are displayed for "500 to 1000"
