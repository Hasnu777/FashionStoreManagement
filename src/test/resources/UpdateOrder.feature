Feature: Update order
  As a customer, I want to be able to add an item to an order so that I can buy the item.
  As a customer, I want to be able to change the quantity of an item in my order.

  Background:
    Given the following customers exist in the system
      | username  | password         | name             | phone          | address                | points |
      | obiwan212 | highground       | Obi-Wan Kenobi   | (438) 555-1234 | Jedi Temple, Coruscant | 212    |
      | anakin501 | i-dont-like-sand | Anakin Skywalker | (514) 555-9876 | Jedi Temple, Coruscant | 501    |
      | alice     | ---              | ---              | ---            | 123 Alice Avenue       | 2      |
    And the following items exist in the system
      | name        | price  | size | quantityInInventory | points |
      | T-Shirt     | 29.99  | M    | 50                  | 4      |
      | Winter Coat | 199.99 | L    | 10                  | 5      |
      | Jeans       | 59.99  | M    | 30                  | 3      |
    And the following orders exist in the system
      # There's no way to set the autounique order number, so refer to orders here using a separate ID.
      # The controller should still identify orders by their order number.
      # You'll need to create a map from IDs to order numbers.
      # Also, please convert the string "NULL" to null.
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

  Scenario Outline: Successfully add a new item to an order
    When the user attempts to add item "<item>" in size "<size>" to the order with ID <orderId>
    Then the system shall not raise any errors
    And the order with ID "<orderId>" shall include 1 "<item>" in size "<size>"
    And the order with ID "<orderId>" shall include <numItems> distinct items

    Examples:
      | item        | size | orderId | numItems |
      | Winter Coat | L    | 1       | 2        |
      | T-Shirt     | M    | 3       | 1        |
      | Jeans       | M    | 3       | 1        |

  Scenario Outline: Try to add item to an order that doesn't exist
    When the user attempts to add item "<item>" in size "<size>" to the non-existent order with order number <id>
    Then the system shall raise the error "there is no order with number \"<id>\""
    And no order shall exist with ID "<id>"
    And the total number of orders shall be 4

    Examples:
      | item        | size | id        |
      | T-Shirt     | M    | 9999999   |
      | Winter Coat | L    | 123456789 |

  Scenario Outline: Try to add item that doesn't exist to an order
    When the user attempts to add item "<item>" in size "<size>" to the order with ID <orderId>
    Then the system shall raise the error "there is no item called \"<item>\""
    And no item shall exist with name "<item>"
    And the order with ID "<orderId>" shall not include any items called "<item>"
    And the order with ID "<orderId>" shall include <numItems> distinct items

    Examples:
      | item          | size | orderId | numItems |
      | Unicorn shirt | M    | 1       | 1        |
      | Dragon pants  | L    | 3       | 0        |

  Scenario: Try to add an item to an order that already contains that item
    When the user attempts to add item "T-Shirt" in size "M" to the order with ID 1
    Then the system shall raise the error "order already includes item \"T-Shirt\" in size \"M\""
    And the order with ID "1" shall include 2 "T-Shirt" in size "M"
    And the order with ID "1" shall include 1 distinct item

  Scenario: Try to add an item to an order that's already been placed
    When the user attempts to add item "T-Shirt" in size "M" to the order with ID 4
    Then the system shall raise the error "order has already been placed"
    And the order with ID "4" shall not include any items called "T-Shirt"
    And the order with ID "4" shall include 1 distinct item

  Scenario Outline: Successfully update quantity of item in order
    When the user attempts to set the quantity of item "<item>" in size "<size>" in the order with ID "<id>" to <newQty>
    Then the system shall not raise any errors
    And the order with ID "<id>" shall include <newQty> "<item>" in size "<size>"
    And the order with ID "<id>" shall include 1 distinct item

    Examples:
      | item    | size | id | newQty |
      | T-Shirt | M    | 1  | 1      |
      | T-Shirt | M    | 1  | 3      |
      | T-Shirt | M    | 1  | 42     |

  Scenario: Successfully remove item from order by setting its quantity to zero
    When the user attempts to set the quantity of item "T-Shirt" in size "M" in the order with ID "1" to 0
    Then the system shall not raise any errors
    And the order with ID "1" shall not include any items called "T-Shirt"
    And the order with ID "1" shall include 0 distinct items

  Scenario Outline: Try to update quantity of item in an order that doesn't exist
    When the user attempts to set the quantity of item "<item>" in size "<size>" in the non-existent order <id> to <newQty>
    Then the system shall raise the error "there is no order with number \"<id>\""
    And no order shall exist with ID "<id>"
    And the total number of orders shall be 4

    Examples:
      | item        | size | id        | newQty |
      | T-Shirt     | M    | 9999999   | 2      |
      | Winter Coat | L    | 123456789 | 3      |

  Scenario Outline: Update quantity of item when order doesn't contain that item
    When the user attempts to set the quantity of item "<item>" in size "<size>" in the order with ID "<id>" to <newQty>
    Then the system shall raise the error "<error>"
    And the order with ID "<id>" shall not include any items called "<item>"
    And the order with ID "<id>" shall include <numItems> distinct items

    Examples:
      | item        | size | id | newQty | numItems | error                                                 |
      | Dragon suit | M    | 1  | 2      | 1        | there is no item called \"Dragon suit\"               |
      | T-Shirt     | M    | 3  | 1      | 0        | order does not include item \"T-Shirt\" in size \"M\" |

  Scenario Outline: Unsuccessfully update quantity of item in order
    When the user attempts to set the quantity of item "<item>" in size "<size>" in the order with ID "<id>" to <newQty>
    Then the system shall raise the error "<error>"
    And the order with ID "<id>" shall include <oldQty> "<item>" in size "<size>"
    And the order with ID "<id>" shall include <numItems> distinct items

    Examples:
      | item        | size | id | newQty | oldQty | numItems | error                         |
      | T-Shirt     | M    | 1  | -1     | 2      | 1        | quantity must be non-negative |
      | T-Shirt     | M    | 1  | -2     | 2      | 1        | quantity must be non-negative |
      | T-Shirt     | M    | 2  | 10     | 1      | 2        | order has already been placed |
      | Winter Coat | L    | 2  | 7      | 3      | 2        | order has already been placed |