Feature: Booking endpoint
  Background: Booking endpoints should allow to get and create bookings

  Scenario: /booking/{id} must return a booking with a valid id
    Given I perform a GET call to the booking endpoint with id "1"
    Then I verify that the status code is 200
    And I verify that the body does not have size 0

  Scenario: /booking/{id} must return error 404 with non existent id
    Given I perform a GET call to the booking endpoint with id "23498530"
    Then I verify that the status code is 404

  Scenario: post request with /booking endpoint creates an entry
    Given I perform a POST call to the booking endpoint with the following data
      | "Jim"      |  "Brown"   | 111  |  true        | "2018-01-01"  | "2019-01-01"  | "Breakfast" |
    Then I verify that the status code is 200
    And I verify that the body does not have size 0
    And I verify the following data in the body response
      | "Jim"      |  "Brown"   | 111  |  true        | "2018-01-01"  | "2019-01-01"  | "Breakfast" |

  Scenario Outline: post request with /booking endpoint and faulty data must not create an entry
    Given I perform a POST call to the booking endpoint with the following data
      | <first_name> | <last_name>  | <total_price>  | <deposit_paid> | <check_in> | <check_out>  | <additional_needs> |
    Then I verify that the status code is 500
    Examples:
      | first_name | last_name  | total_price           | deposit_paid | check_in      | check_out     | additional_needs  |
      | "Jim"      |  2839      | 111                   |  true        | "2018-01-01"  | "2019-01-01"  | "Breakfast"       |
      | "Jim"      |  "Brown"   | "hundred and eleven"  |  true        | "2018-01-01"  | "2019-01-01"  | "Breakfast"       |