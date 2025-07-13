Feature: Core Product Tests - NBA Warriors Site

  @Jackets
  Scenario: Extract all Men’s Jackets details from the Shop
    Given I am on the Core Product Home Page
    When I navigate to the Shop Menu and select Men's section
    And I scrape all Jacket items across all pages
    Then I store Jacket price, title, and Top Seller message in a text file and attach it to the report

  @Videos
  Scenario: Count total Video Feeds and 3D videos under New & Features
    Given I am on the Core Product Home Page
    When I hover on the menu and click on New & Features
    Then I count the total number of Video Feeds
    And I count how many Video Feeds are marked as 3D or above
