@first
Feature: FirstCry Search and Filter

  Scenario: Search for a product and apply filters
    Given User is on the FirstCry home page
    When User searches for "Boys t shirt"
    And User clicks on the search button
    Then User should be redirected to the search results page
    And User applies brand filter and prints them
