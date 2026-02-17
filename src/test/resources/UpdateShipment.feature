Feature: Update shipment
  As a manager, I want to be able to add an item to a shipment so that I can buy the item.
  As a manager, I want to be able to change the quantity of an item in a shipment.

  Background:
    Given the following items exist in the system
      | name         | price | size | quantityInInventory | points |
      | T-Shirt      | 29.99 | M    |                  50 |     10 |
      | T-Shirt      | 29.99 | L    |                  30 |     10 |
      | Jeans        | 79.99 | M    |                  20 |     25 |
      | Winter Coat  | 199.99| L    |                  10 |     50 |
    And the following shipments exist
      # The shipmentId will be auto-generated shipment numbers.
      # Also, please convert the string "NULL" to null.
      | shipmentId | dateOrdered |
      | 1          | NULL        |
      | 2          | 2025-02-24  |
      | 3          | NULL        |
      | 4          | 2025-02-24  |
    And the following sized items are part of shipments
      | shipmentId | item         | size | quantity |
      | 1          | T-Shirt      | M    |      200 |
      | 2          | T-Shirt      | L    |      100 |
      | 2          | Jeans        | M    |      150 |
      | 4          | Winter Coat  | L    |       50 |

  Scenario Outline: Successfully add a new sized item to a shipment
    When the manager attempts to add sized item "<item>" of size "<size>" to the shipment with ID <shipmentId>
    Then the system shall not raise any errors
    And the shipment with ID <shipmentId> shall include 1 "<item>" of size "<size>"
    And the shipment with ID <shipmentId> shall include <numItems> distinct sized items

    Examples:
      | item         | size | shipmentId | numItems |
      | Jeans        | M    |          1 |        2 |
      | T-Shirt      | M    |          3 |        1 |
      | Winter Coat  | L    |          3 |        1 |

  Scenario Outline: Try to add sized item to a shipment that doesn't exist
    When the manager attempts to add sized item "<item>" of size "<size>" to the shipment with ID <shipmentId>
    Then the system shall raise the error "there is no shipment with number \"<shipmentId>\""
    And no shipment shall exist with number <shipmentId>
    And the total number of shipments shall be 4

    Examples:
      | item         | size | shipmentId |
      | T-Shirt      | M    |    9999999 |
      | Jeans        | M    |  123456789 |

  Scenario Outline: Try to add sized item that doesn't exist to a shipment
    When the manager attempts to add sized item "<item>" of size "<size>" to the shipment with ID <shipmentId>
    Then the system shall raise the error "there is no sized item called \"<item>\" of size \"<size>\""
    And no sized item shall exist with name "<item>" and size "<size>"
    And the shipment with ID <shipmentId> shall not include any sized items called "<item>" of size "<size>"
    And the shipment with ID <shipmentId> shall include <numItems> distinct sized items

    Examples:
      | item          | size | shipmentId | numItems |
      | Unicorn Hat   | XL   |          1 |        1 |
      | Dragon Boots  | S    |          3 |        0 |

  Scenario: Try to add a sized item to a shipment that already contains that sized item
    When the manager attempts to add sized item "T-Shirt" of size "M" to the shipment with ID 1
    Then the system shall raise the error "shipment already includes sized item \"T-Shirt\" of size \"M\""
    And the shipment with ID 1 shall include 200 "T-Shirt" of size "M"

  Scenario: Try to add a sized item to a shipment that's already been ordered
    When the manager attempts to add sized item "T-Shirt" of size "M" to the shipment with ID 4
    Then the system shall raise the error "shipment has already been ordered"
    And the shipment with ID 4 shall not include any sized items called "T-Shirt" of size "M"
    And the shipment with ID 4 shall include 1 distinct sized item

  Scenario Outline: Successfully update quantity of sized item in shipment
    When the manager attempts to set the quantity of sized item "<item>" of size "<size>" in the shipment with ID <shipmentId> to <newQty>
    Then the system shall not raise any errors
    And the shipment with ID <shipmentId> shall include <newQty> "<item>" of size "<size>"
    And the shipment with ID <shipmentId> shall include 1 distinct sized item

    Examples:
      | item    | size | shipmentId | newQty |
      | T-Shirt | M    |          1 |      1 |
      | T-Shirt | M    |          1 |      3 |
      | T-Shirt | M    |          1 |     42 |

  Scenario: Successfully remove sized item from shipment by setting its quantity to zero
    When the manager attempts to set the quantity of sized item "T-Shirt" of size "M" in the shipment with ID 1 to 0
    Then the system shall not raise any errors
    And the shipment with ID 1 shall not include any sized items called "T-Shirt" of size "M"
    And the shipment with ID 1 shall include 0 distinct sized items

  Scenario Outline: Try to update quantity of sized item in a shipment that doesn't exist
    When the manager attempts to set the quantity of sized item "<item>" of size "<size>" in the shipment with ID <shipmentId> to <newQty>
    Then the system shall raise the error "there is no shipment with number \"<shipmentId>\""
    And no shipment shall exist with number <shipmentId>
    And the total number of shipments shall be 4

    Examples:
      | item         | size | shipmentId | newQty |
      | T-Shirt      | M    |    9999999 |      2 |
      | Winter Coat  | L    |  123456789 |      3 |

  Scenario Outline: Update quantity of sized item when shipment doesn't contain that sized item
    When the manager attempts to set the quantity of sized item "<item>" of size "<size>" in the shipment with ID <shipmentId> to <newQty>
    Then the system shall raise the error "<error>"
    And the shipment with ID <shipmentId> shall not include any sized items called "<item>" of size "<size>"
    And the shipment with ID <shipmentId> shall include <numItems> distinct sized items

    Examples:
      | item         | size | shipmentId | newQty | numItems | error                                                              |
      | Dragon Boots | S    |          1 |      2 |        1 | there is no sized item called \\"Dragon Boots\\" of size \\"S\\"   |
      | T-Shirt      | L    |          3 |      1 |        0 | shipment does not include sized item \\"T-Shirt\\" of size \\"L\\" |

  Scenario Outline: Unsuccessfully update quantity of sized item in shipment
    When the manager attempts to set the quantity of sized item "<item>" of size "<size>" in the shipment with ID <shipmentId> to <newQty>
    Then the system shall raise the error "<error>"
    And the shipment with ID <shipmentId> shall include <oldQty> "<item>" of size "<size>"
    And the shipment with ID <shipmentId> shall include <numItems> distinct sized items

    Examples:
      | item         | size | shipmentId | newQty | oldQty | numItems | error                             |
      | T-Shirt      | M    |          1 |     -1 |    200 |        1 | quantity must be non-negative     |
      | T-Shirt      | M    |          1 |     -2 |    200 |        1 | quantity must be non-negative     |
      | T-Shirt      | L    |          2 |     10 |    100 |        2 | shipment has already been ordered |
      | Jeans        | M    |          2 |      7 |    150 |        2 | shipment has already been ordered |