Feature: Create, update, and delete items
  As a manager, I want to add new items.
  As a manager, I want to update the price and point value of items.
  As a manager, I want to delete items that are no longer offered at my store.

  Background:
    Given the following items exist in the system
      | name         | price | size | quantityInInventory | points |
      | T-Shirt      | 29.99 | M    |                  50 |      4 |
      | Winter Coat  | 199.99| L    |                  10 |      5 |

  Scenario Outline: Successfully add a new item
    When the manager attempts to add a new item with name "<name>", price <price>, points <points>, size "<size>", and quantity <quantity>
    Then the system shall not raise any errors
    And an item shall exist with name "<name>"
    And the item shall cost <price>
    And the item shall be worth <points> points
    And a sized item of size "<size>" shall exist for item "<name>"
    And the quantity of the sized item "<name>" of size "<size>" shall be <quantity>
    And the total number of items shall be 3

    Examples:
      | name        | price | points | size | quantity |
      | Jeans       | 79.99 |      3 | M    |       20 |
      | Socks       | 9.99  |      2 | S    |      100 |
      | Hoodie      | 59.99 |      4 | L    |       30 |

  Scenario Outline: Try to add an item with a duplicate name
    When the manager attempts to add a new item with name "<name>", price <newPrice>, points <newPoints>, size "<newSize>", and quantity <newQuantity>
    Then the system shall raise the error "an item called \"<name>\" already exists"
    And an item shall exist with name "<name>"
    And the item shall cost <oldPrice>
    And the item shall be worth <oldPoints> points
    And a sized item of size "<oldSize>" shall exist for item "<name>"
    And the quantity of the sized item "<name>" of size "<oldSize>" shall be <oldQuantity>
    And the total number of items shall be 2

    Examples:
      | name        | oldPrice | newPrice | oldPoints | newPoints | oldSize | newSize | oldQuantity | newQuantity |
      | T-Shirt     | 29.99    | 35.99    |       4   |      5    | M       | L       |          50 |          10 |
      | Winter Coat | 199.99   | 219.99   |       5   |      4    | L       | XL      |          10 |           5 |

  Scenario Outline: Try to add an invalid item
    When the manager attempts to add a new item with name "<name>", price <price>, points <points>, size "<size>", and quantity <quantity>
    Then the system shall raise the error "<error>"
    And no item shall exist with name "<name>"
    And the total number of items shall be 2

    Examples:
      | name   | price | points | size | quantity | error                          |
      |        | 29.99 |     10 | M    |       10 | name is required               |
      | Jacket | 0     |     10 | M    |       10 | price must be positive         |
      | Jacket | -1    |     10 | M    |       10 | price must be positive         |
      | Jacket | 29.99 |      0 | M    |       10 | points must be between one and five |
      | Jacket | 29.99 |      6 | M    |       10 | points must be between one and five |
      | Jacket | 29.99 |      3 | M    |      -1 | quantity must be non-negative  |

  Scenario Outline: Successfully update item price
    When the manager attempts to update the price of item "<item>" to <newPrice>
    Then the system shall not raise any errors
    And an item shall exist with name "<item>"
    And the item shall cost <newPrice>
    And the item shall be worth <oldPoints> points
    And the total number of items shall be 2

    Examples:
      | item        | newPrice | oldPoints |
      | T-Shirt     | 34.99    |      4    |
      | Winter Coat | 189.99   |      5    |

  Scenario Outline: Successfully update item point value
    When the manager attempts to update the point value of item "<item>" to <newPoints>
    Then the system shall not raise any errors
    And an item shall exist with name "<item>"
    And the item shall cost <oldPrice>
    And the item shall be worth <newPoints> points
    And the total number of items shall be 2

    Examples:
      | item        | oldPrice | newPoints |
      | T-Shirt     | 29.99    |      5    |
      | Winter Coat | 199.99   |      4    |

  Scenario Outline: Try to update item that doesn't exist
    When the manager attempts to update the <field> of item "<item>" to <newValue>
    Then the system shall raise the error "there is no item called \"<item>\""
    And no item shall exist with name "<item>"
    And the total number of items shall be 2

    Examples:
      | item        | field       | newValue |
      | nonexistent | price       | 10.99    |
      | not real    | price       | 20.00    |
      | nonexistent | point value | 3        |
      | not real    | point value | 4        |

  Scenario Outline: Try to update item with invalid values
    When the manager attempts to update the <field> of item "<item>" to <newValue>
    Then the system shall raise the error "<error>"
    And an item shall exist with name "<item>"
    And the item shall cost <oldPrice>
    And the item shall be worth <oldPoints> points
    And the total number of items shall be 2

    Examples:
      | item        | field       | newValue | oldPrice | oldPoints | error                          |
      | T-Shirt     | price       | 0        | 29.99    |      4    | price must be positive         |
      | Winter Coat | price       | -1       | 199.99   |      5    | price must be positive         |
      | T-Shirt     | point value | 0        | 29.99    |      4    | points must be between one and five |
      | Winter Coat | point value | 6        | 199.99   |      5    | points must be between one and five |

  Scenario Outline: Successfully delete an item
    When the manager attempts to delete the item "<name>"
    Then the system shall not raise any errors
    And no item shall exist with name "<name>"
    And the total number of items shall be 1

    Examples:
      | name        |
      | T-Shirt     |
      | Winter Coat |

  Scenario Outline: Try to delete an item that doesn't exist
    When the manager attempts to delete the item "<name>"
    Then the system shall raise the error "there is no item called \"<name>\""
    And no item shall exist with name "<name>"
    And the total number of items shall be 2

    Examples:
      | name           |
      | Beanie         |
      | Socks (Wool)   |