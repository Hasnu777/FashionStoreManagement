Feature: Create and delete orders
  As a customer, I want to create orders so that I can buy items.
  As a customer, I want to delete orders if I no longer intend to make the purchase.

  Background:
    Given the following employees exist in the system
      | username | password | name           | phone          |
      | alice    | alice123 | Alice Allisson | (514) 555-1111 |
      | bob      | password | Bob Robertson  | (514) 555-2222 |
    And the following customers exist in the system
      | username  | password         | name             | phone          | address                | points |
      | obiwan212 | highground       | Obi-Wan Kenobi   | (438) 555-1234 | Jedi Temple, Coruscant | 212    |
      | anakin501 | i-dont-like-sand | Anakin Skywalker | (514) 555-9876 | Jedi Temple, Coruscant | 501    |
      | alice     | ---              | ---              | ---            | 123 Alice Avenue       | 2      |
    And the following items exist in the system
      | name        | price  | size | quantityInInventory | points |
      | T-Shirt     | 29.99  | M    | 50                  | 4      |
      | Winter Coat | 199.99 | L    | 10                  | 5      |
    And the following orders exist in the system
      # There's no way to set the autounique order number, so refer to orders here using a separate ID.
      # The controller should still identify orders by their order number.
      # You'll need to create a map from IDs to order numbers.
      # Also, please convert the string "NULL" to null (here and in the tests below).
      | id | datePlaced | deadline    | customer  |
      | 1  | NULL       | SameDay     | alice     |
      | 2  | 2025-02-24 | InOneDay    | obiwan212 |
      | 3  | NULL       | InTwoDays   | anakin501 |
      | 4  | 2025-02-24 | InThreeDays | alice     |
    And the following items are part of orders
      | order | item        | size | quantity |
      | 1     | T-Shirt     | M    | 2        |
      | 2     | T-Shirt     | M    | 1        |
      | 2     | Winter Coat | L    | 3        |
      | 4     | Winter Coat | L    | 5        |

  Scenario Outline: Successfully create an order
    When "<user>" attempts to create an order with deadline "<deadline>"
    Then the system shall not raise any errors
    And "<user>" shall have a new order
    And the newly-created order shall have deadline "<deadline>"
    And the newly-created order shall have 0 items
    And the newly-created order shall not have been placed
    And the total number of orders shall be 5

    Examples:
      | user      | deadline    |
      | obiwan212 | SameDay     |
      | obiwan212 | InOneDay    |
      | anakin501 | InTwoDays   |
      | alice     | InThreeDays |

  Scenario Outline: Try to create an invalid order
    When "<user>" attempts to create an order with deadline "<deadline>"
    Then the system shall raise the error "<error>"
    And the total number of orders shall be 4

    Examples:
      | user        | deadline | error                                            |
      | nonexistent | SameDay  | there is no user with username \\"nonexistent\\" |
      | ghost       | SameDay  | there is no user with username \\"ghost\\"       |
      | bob         | SameDay  | \\"bob\\" is not a customer                      |
      | alice       | NULL     | delivery deadline is required                    |

  Scenario Outline: Successfully delete an order
    When the user attempts to delete the order with ID "<id>"
    Then the system shall not raise any errors
    And no order shall exist with ID "<id>"
    And the total number of orders shall be 3

    Examples:
      | id |
      | 1  |
      | 3  |

  Scenario Outline: Try to delete an order that doesn't exist
    When the user attempts to delete the non-existent order with ID "<id>"
    Then the system shall raise the error "there is no order with number \"<id>\""
    And no order shall exist with ID "<id>"
    And the total number of orders shall be 4

    Examples:
      | id        |
      | 9999999   |
      | 123456789 |

  Scenario Outline: Try to delete an order that's already been placed
    When the user attempts to delete the order with ID "<id>"
    Then the system shall raise the error "cannot delete an order which has already been placed"
    And an order shall exist with ID "<id>"
    And the total number of orders shall be 4

    Examples:
      | id |
      | 2  |
      | 4  |