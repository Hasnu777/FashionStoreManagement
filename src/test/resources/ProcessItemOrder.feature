Feature: Process order
  As a customer, I want to place orders so that I can buy items.
  As a manager, I want to assign orders to employees so that the orders can be fulfilled.
  As an employee, I want to mark orders as completed once I have finished assembling them.

  Background:
    Given the following employees exist in the system
      | username | password | name           | phone          |
      | alice    | alice123 | Alice Allisson | (514) 555-1111 |
      | bob      | password | Bob Robertson  | (514) 555-2222 |
      | claire   | password | Claire Clark   | (514) 555-3333 |
    And the following customers exist in the system
      | username  | password         | name             | phone          | address                | points |
      | obiwan212 | highground       | Obi-Wan Kenobi   | (438) 555-1234 | Jedi Temple, Coruscant |    212 |
      | anakin501 | i-dont-like-sand | Anakin Skywalker | (514) 555-9876 | Jedi Temple, Coruscant |    501 |
      | alice     | ---              | ---              | ---            | 123 Alice Avenue       |      2 |
    And the following items exist in the system
      | name                | price | size | quantityInInventory | points | seasonality |
      | TShirt              |   549 | M    |                  20 |      5 | core       |
      | Jeans               |   179 | M    |                   2 |      2 | seasonal   |
      | Hoodie              |   100 | M    |                   8 |      1 | core       |
      | Socks               |     1 | M    |                 100 |      1 | core       |

  Scenario Outline: Successfully check out a bulk order of hoodies
    Given the following orders exist in the system
      | id   | datePlaced | deadline   | customer   | assignee | state              |
      | <id> | NULL       | <deadline> | <customer> | NULL     | under construction |
    And the following items are part of orders
      | order | item | size | quantity |
      | <id> | Hoodie | M | <numHoodies> |
    When the user attempts to check out the order with ID "<id>"
    Then the system shall not raise any errors
    And the total cost of the order shall be <cost> cents
    And the order shall be "pending"
    And the order's placer shall be "<customer>"
    # No change yet in points, inventory, assignee, etc.
    And the order's assignee shall be "NULL"
    And "<customer>" shall have <points> points
    And the quantity of the sized item "Hoodie" of size "M" shall be 8

    Examples:
      | id | deadline    | customer  | points | numHoodies | cost |
      | a  | InOneDay    | alice     |      2 |          1 |  100 |
      # (2 hoodies)($0.95/hoodie) = $1.90
      | b  | InOneDay    | anakin501 |    501 |          2 |  190 |
      # (3 hoodies)($0.90/hoodie) = $2.70
      | c  | InTwoDays   | obiwan212 |    212 |          3 |  270 |
      # (8 hoodies)($0.65/hoodie) = $5.20
      | d  | InThreeDays | obiwan212 |    212 |          8 |  520 |
      # (9 hoodies)($0.60/hoodie) = $5.40
      | e  | InTwoDays   | obiwan212 |    212 |          9 |  540 |
      # (10 hoodies)($0.55/hoodie) = $5.50
      | f  | InOneDay    | obiwan212 |    212 |         10 |  550 |

  Scenario Outline: Successfully check out an order with multiple items
    Given the following orders exist in the system
      | id | datePlaced | deadline   | customer | assignee | state              |
      | g  | NULL       | <deadline> | alice    | NULL     | under construction |
    And the following items are part of orders
      | order | item | size | quantity |
      | g | TShirt | M | <qtyTShirtInOrder> |
      | g | Jeans | M | <qtyJeansInOrder> |
    When the user attempts to check out the order with ID "g"
    Then the system shall not raise any errors
    And the total cost of the order shall be <cost> cents
    And the order shall be "pending"
    And the order's placer shall be "alice"
    # No change yet in points, inventory, assignee, etc.
    And the order's assignee shall be "NULL"
    And "alice" shall have 2 points
    And the quantity of the sized item "TShirt" of size "M" shall be 20
    And the quantity of the sized item "Jeans" of size "M" shall be 2

    Examples:
      | deadline    | qtyTShirtInOrder | qtyJeansInOrder | cost |
      | InOneDay    |              1 |              1 |  728 |
      #  TShirt: (5 units)(0.8)($5.49/unit) = $21.96
      #  Jeans: (3 units)(0.9)($1.79/unit) = $4.833
      # Total: $26.793 --> $26.79
      | InTwoDays   |              5 |              3 | 2679 |
      | InThreeDays |              5 |              3 | 2679 |
      # Extra fee for same-day delivery
      | SameDay     |              1 |              1 | 1228 |
      | SameDay     |              5 |              3 | 3179 |

  Scenario: Try to check out an empty order
    Given the following orders exist in the system
      | id    | datePlaced | deadline  | customer | assignee | state              |
      | empty | NULL       | InTwoDays | alice    | NULL     | under construction |
    When the user attempts to check out the order with ID "empty"
    Then the system shall raise the error "cannot check out an empty order"
    And the order shall be "under construction"
    And the order's assignee shall be "NULL"

  Scenario Outline: Try to check out an order in the wrong state
    Given the following orders exist in the system
      | id       | datePlaced   | deadline  | customer  | assignee   | state   |
      | badstate | <datePlaced> | InTwoDays | anakin501 | <assignee> | <state> |
    And the following items are part of orders
      | order | item | size | quantity |
      | badstate | TShirt | M | 5 |
      | badstate | Jeans | M | 2 |
    When the user attempts to check out the order with ID "badstate"
    Then the system shall raise the error "order has already been checked out"
    And the order shall be "<state>"
    And the order's assignee shall be "<assignee>"
    And "anakin501" shall have 501 points
    And the quantity of the sized item "TShirt" of size "M" shall be 20
    And the quantity of the sized item "Jeans" of size "M" shall be 2

    Examples:
      | state              | datePlaced | assignee |
      | pending            | NULL       | NULL     |
      | placed             | today      | NULL     |
      | in preparation     | yesterday  | bob      |
      | ready for delivery | yesterday  | claire   |
      | delivered          | yesterday  | alice    |
      | cancelled          | yesterday  | bob      |

  Scenario Outline: Successfully pay for an order
    Given the following orders exist in the system
      | id      | datePlaced | deadline   | customer   | assignee | state   |
      | primary | NULL       | <deadline> | <customer> | NULL     | pending |
      | bulk    | NULL       | <deadline> | <customer> | NULL     | pending |
      | mini    | NULL       | <deadline> | <customer> | NULL     | pending |
    And the following items are part of orders
      | order | item | size | quantity |
      | primary | TShirt | M | 1 |
      | primary | Jeans | M | 1 |
      | bulk | Hoodie | M | 8 |
      | mini | Socks | M | 1 |
    When the user attempts to pay for the order with ID "<orderId>" "<usingOrWithoutUsing>" their points
    Then the system shall not raise any errors
    And the final cost of the order, after considering points, shall be <cost> cents
    And the order shall be "placed"
    And the order's date placed shall be today
    And the order's assignee shall be "NULL"
    And "<customer>" shall have <points> points
    And the quantity of the sized item "TShirt" of size "M" shall be <newQtyTShirt>
    And the quantity of the sized item "Jeans" of size "M" shall be <newQtyJeans>
    And the quantity of the sized item "Hoodie" of size "M" shall be <newQtyHoodies>
    And the quantity of the sized item "Socks" of size "M" shall be <newQtySocks>

    Examples:
      | orderId | deadline    | customer  | usingOrWithoutUsing | cost | points | newQtyTShirt | newQtyJeans | newQtyHoodies | newQtySocks |
      # 1 TShirt + 1 pair of jeans = 7 points
      | primary | InOneDay    | alice     | without using       |  728 |      9 |         19 |          1 |             8 |        100 |
      | primary | InTwoDays   | alice     | using               |  726 |      7 |         19 |          1 |             8 |        100 |
      | primary | InThreeDays | anakin501 | without using       |  728 |    508 |         19 |          1 |             8 |        100 |
      | primary | InOneDay    | anakin501 | using               |  227 |      7 |         19 |          1 |             8 |        100 |
      # 8 hoodies = 8 points
      | bulk    | InTwoDays   | obiwan212 | without using       |  520 |    220 |         20 |          2 |             0 |        100 |
      | bulk    | InThreeDays | obiwan212 | using               |  308 |      8 |         20 |          2 |             0 |        100 |
      # 1 pair of socks = 1 point
      | mini    | InOneDay    | alice     | without using       |    1 |      3 |         20 |          2 |             8 |         99 |
      | mini    | InTwoDays   | alice     | using               |    0 |      2 |         20 |          2 |             8 |         99 |
      # Extra fee for same-day delivery
      | primary | SameDay     | alice     | without using       | 1228 |      9 |         19 |          1 |             8 |        100 |
      | primary | SameDay     | alice     | using               | 1226 |      7 |         19 |          1 |             8 |        100 |
      | bulk    | SameDay     | obiwan212 | without using       | 1020 |    220 |         20 |          2 |             0 |        100 |
      | bulk    | SameDay     | obiwan212 | using               |  808 |      8 |         20 |          2 |             0 |        100 |
      | mini    | SameDay     | alice     | without using       |  501 |      3 |         20 |          2 |             8 |         99 |
      | mini    | SameDay     | alice     | using               |  499 |      1 |         20 |          2 |             8 |         99 |
      | mini    | SameDay     | anakin501 | without using       |  501 |    502 |         20 |          2 |             8 |         99 |
      | mini    | SameDay     | anakin501 | using               |    0 |      1 |         20 |          2 |             8 |         99 |

  Scenario Outline: Unsuccessfully pay for order due to insufficient stock
    Given the following orders exist in the system
      | id      | datePlaced | deadline    | customer  | assignee | state   |
      | primary | NULL       | InThreeDays | obiwan212 | NULL     | pending |
      | bulk    | NULL       | SameDay     | obiwan212 | NULL     | pending |
    And the following items are part of orders
      | order | item | size | quantity |
      | primary | TShirt | M | 1 |
      | primary | Jeans | M | 5 |
      | bulk | Hoodie | M | 9 |
    When the user attempts to pay for the order with ID "<orderId>" "<usingOrWithoutUsing>" their points
    Then the system shall raise the error "<error>"
    And the order shall be "pending"
    And the order's assignee shall be "NULL"
    And "obiwan212" shall have 212 points
    And the quantity of the sized item "TShirt" of size "M" shall be 20
    And the quantity of the sized item "Jeans" of size "M" shall be 2
    And the quantity of the sized item "Hoodie" of size "M" shall be 8
    And the quantity of the sized item "Socks" of size "M" shall be 100

    Examples:
      | orderId | usingOrWithoutUsing | error                                                |
      | primary | using               | insufficient stock of item \\"Jeans\\" |
      | primary | without using       | insufficient stock of item \\"Jeans\\" |
      | bulk    | using               | insufficient stock of item \\"Hoodie\\"              |
      | bulk    | without using       | insufficient stock of item \\"Hoodie\\"              |

  Scenario Outline: Unsuccessfully pay for order due to wrong state
    Given the following orders exist in the system
      | id       | datePlaced | deadline    | customer | assignee   | state   |
      | badstate | NULL       | InThreeDays | alice    | <assignee> | <state> |
    And the following items are part of orders
      | order | item | size | quantity |
      | badstate | TShirt | M | 1 |
    When the user attempts to pay for the order with ID "badstate" "<usingOrWithoutUsing>" their points
    Then the system shall raise the error "<error>"
    And the order shall be "<state>"
    And the order's assignee shall be "<assignee>"
    And "alice" shall have 2 points
    And the quantity of the sized item "TShirt" of size "M" shall be 20
    And the quantity of the sized item "Jeans" of size "M" shall be 2
    And the quantity of the sized item "Hoodie" of size "M" shall be 8
    And the quantity of the sized item "Socks" of size "M" shall be 100

    Examples:
      | state              | assignee | usingOrWithoutUsing | error                                                   |
      | under construction | NULL     | without using       | cannot pay for an order which has not been checked out  |
      | under construction | NULL     | using               | cannot pay for an order which has not been checked out  |
      | placed             | NULL     | without using       | cannot pay for an order which has already been paid for |
      | placed             | NULL     | using               | cannot pay for an order which has already been paid for |
      | in preparation     | bob      | without using       | cannot pay for an order which has already been paid for |
      | in preparation     | bob      | using               | cannot pay for an order which has already been paid for |
      | ready for delivery | claire   | without using       | cannot pay for an order which has already been paid for |
      | ready for delivery | claire   | using               | cannot pay for an order which has already been paid for |
      | delivered          | alice    | without using       | cannot pay for an order which has already been paid for |
      | delivered          | alice    | using               | cannot pay for an order which has already been paid for |
      | cancelled          | alice    | without using       | cannot pay for an order which has been cancelled        |
      | cancelled          | alice    | using               | cannot pay for an order which has been cancelled        |

  Scenario Outline: Successfully assign order to employee
    Given the following orders exist in the system
      | id         | datePlaced | deadline    | customer  | assignee | state          |
      | unassigned | today      | InThreeDays | alice     | NULL     | placed         |
      | assigned   | yesterday  | InTwoDays   | anakin501 | bob      | in preparation |
    And the following items are part of orders
      | order | item | size | quantity |
      | unassigned | TShirt | M | 1 |
      | assigned | Jeans | M | 1 |
    When the manager attempts to assign the order with ID "<orderId>" to "<employee>"
    Then the system shall not raise any errors
    And the order shall be "in preparation"
    And the order's assignee shall be "<employee>"

    Examples:
      | orderId    | employee |
      | unassigned | alice    |
      | unassigned | bob      |
      | unassigned | claire   |
      # Change assignee of already-assigned order.
      | assigned   | alice    |
      | assigned   | claire   |

  Scenario Outline: Unsuccessfully assign order to employee
    Given the following orders exist in the system
      | id | datePlaced | deadline    | customer | assignee      | state      |
      | m  | today      | InThreeDays | alice    | <oldAssignee> | <oldState> |
    And the following items are part of orders
      | order | item | size | quantity |
      | m | TShirt | M | 1 |
    When the manager attempts to assign the order with ID "m" to "<newAssignee>"
    Then the system shall raise the error "<error>"
    And the order shall be "<oldState>"
    And the order's assignee shall be "<oldAssignee>"

    Examples:
      | newAssignee | oldAssignee | oldState           | error                                                             |
      | alice       | NULL        | under construction | cannot assign employee to order that has not been placed          |
      | bob         | NULL        | under construction | cannot assign employee to order that has not been placed          |
      | claire      | NULL        | pending            | cannot assign employee to order that has not been placed          |
      | alice       | claire      | ready for delivery | cannot assign employee to an order that has already been prepared |
      | bob         | alice       | delivered          | cannot assign employee to an order that has already been prepared |
      | claire      | bob         | cancelled          | cannot assign employee to an order that has been cancelled        |
      | nonexistent | NULL        | placed             | there is no user with username \\"nonexistent\\"                  |
      | ghost       | NULL        | placed             | there is no user with username \\"ghost\\"                        |
      | obiwan212   | NULL        | placed             | \\"obiwan212\\" is not an employee                                |
      | anakin501   | NULL        | placed             | \\"anakin501\\" is not an employee                                |

  Scenario Outline: Successfully finish order assembly
    Given the following orders exist in the system
      | id                 | datePlaced   | deadline   | customer  | assignee | state          |
      | first  | <datePlaced> | <deadline> | obiwan212 | alice    | in preparation |
      | second | <datePlaced> | <deadline> | anakin501 | bob      | in preparation |
    And the following items are part of orders
      | order | item | size | quantity |
      | first | Jeans | M | 1 |
      | first | TShirt | M | 1 |
      | second | Hoodie | M | 1 |
    When the user attempts to indicate that assembly of the order with ID "<orderId>" is finished
    Then the system shall not raise any errors
    And the order shall be "ready for delivery"

    Examples:
      | orderId            | datePlaced | deadline    |
      | first  | yesterday  | InOneDay    |
      | first  | today      | SameDay     |
      | second | yesterday  | InTwoDays   |
      | second | yesterday  | InThreeDays |
      | second | today      | InOneDay    |
      | second | today      | InTwoDays   |
      | second | today      | InThreeDays |
      | first  | yesterday  | SameDay     |
      | second | yesterday  | SameDay     |

  Scenario Outline: Unsuccessfully finish order assembly
    Given the following orders exist in the system
      | id        | datePlaced | deadline | customer | assignee      | state      |
      | bad_state | yesterday  | InOneDay | alice    | <oldAssignee> | <oldState> |
    And the following items are part of orders
      | order | item | size | quantity |
      | bad_state | Jeans | M | 1 |
    When the user attempts to indicate that assembly of the order with ID "bad_state" is finished
    Then the system shall raise the error "<error>"
    And the order shall be "<oldState>"

    Examples:
      | oldState           | oldAssignee | error                                                                          |
      | under construction | NULL        | cannot finish assembling order because it has not been assigned to an employee |
      | pending            | NULL        | cannot finish assembling order because it has not been assigned to an employee |
      | placed             | NULL        | cannot finish assembling order because it has not been assigned to an employee |
      | ready for delivery | bob         | cannot finish assembling order that has already been assembled                 |
      | delivered          | bob         | cannot finish assembling order that has already been assembled                 |
      | cancelled          | bob         | cannot finish assembling order because it was cancelled                        |

  Scenario Outline: Successfully cancel order
    Given the following orders exist in the system
      | id   | datePlaced | deadline | customer   | assignee | state      |
      | all  | yesterday  | InOneDay | <customer> | NULL     | <oldState> |
      | some | today      | SameDay  | <customer> | NULL     | <oldState> |
    And the following items are part of orders
      | order | item | size | quantity |
      | all | TShirt | M | 1 |
      | all | Jeans | M | 2 |
      | all | Hoodie | M | 3 |
      | all | Socks | M | 4 |
      | some | Jeans | M | 10 |
      | some | Hoodie | M | 9 |
    When the user attempts to cancel the order with ID "<orderId>"
    Then the system shall not raise any errors
    And the order shall be "cancelled"
    # Need to add back inventory that was "reserved" for this order (if it was already placed)
    And the quantity of the sized item "TShirt" of size "M" shall be <newTShirtQty>
    And the quantity of the sized item "Jeans" of size "M" shall be <newJeansQty>
    And the quantity of the sized item "Hoodie" of size "M" shall be <newHoodieQty>
    And the quantity of the sized item "Socks" of size "M" shall be <newSocksQty>
    # No change to the customer's points
    And "<customer>" shall have <points> points

    Examples:
      | orderId | oldState           | newTShirtQty | newJeansQty | newHoodieQty | newSocksQty | customer  | points |
      | all     | placed             |         21 |          4 |           11 |        104 | obiwan212 |    212 |
      | all     | placed             |         21 |          4 |           11 |        104 | alice     |      2 |
      | some    | placed             |         20 |         12 |           17 |        100 | obiwan212 |    212 |
      # No change in inventory if the order has not yet been placed
      | all     | under construction |         20 |          2 |            8 |        100 | obiwan212 |    212 |
      | some    | pending            |         20 |          2 |            8 |        100 | alice     |      2 |

  Scenario Outline: Unsuccessfully cancel order
    Given the following orders exist in the system
      | id        | datePlaced | deadline | customer  | assignee      | state      |
      | bad_state | yesterday  | InOneDay | anakin501 | <oldAssignee> | <oldState> |
    And the following items are part of orders
      | order | item | size | quantity |
      | bad_state | TShirt | M | 1 |
    When the user attempts to cancel the order with ID "bad_state"
    Then the system shall raise the error "<error>"
    And the order shall be "<oldState>"
    And the quantity of the sized item "TShirt" of size "M" shall be 20
    And the quantity of the sized item "Jeans" of size "M" shall be 2
    And the quantity of the sized item "Hoodie" of size "M" shall be 8
    And the quantity of the sized item "Socks" of size "M" shall be 100
    And "anakin501" shall have 501 points

    Examples:
      | oldState           | oldAssignee | error                                                                |
      | in preparation     | alice       | cannot cancel an order that has already been assigned to an employee |
      | ready for delivery | bob         | cannot cancel an order that has already been assigned to an employee |
      | delivered          | claire      | cannot cancel an order that has already been assigned to an employee |
      | cancelled          | NULL        | order was already cancelled                                          |
      | cancelled          | alice       | order was already cancelled                                          |

  Scenario Outline: Successfully deliver order
    Given the following orders exist in the system
      | id    | datePlaced   | deadline   | customer  | assignee | state              |
      | ready | <datePlaced> | <deadline> | obiwan212 | claire   | ready for delivery |
    And the following items are part of orders
      | order | item | size | quantity |
      | ready | TShirt | M | 1 |
    When the manager attempts to mark the order with ID "ready" as delivered
    Then the system shall not raise any errors
    And the order shall be "delivered"

    Examples:
      | datePlaced     | deadline    |
      | today          | SameDay     |
      | yesterday      | InOneDay    |
      | two days ago   | InTwoDays   |
      | three days ago | InThreeDays |
      # Better late than never
      | yesterday      | SameDay     |
      | two days ago   | SameDay     |
      | two days ago   | InOneDay    |

  Scenario Outline: Unsuccessfully mark order as delivered
    Given the following orders exist in the system
      | id        | datePlaced   | deadline   | customer  | assignee      | state      |
      | not_ready | <datePlaced> | <deadline> | anakin501 | <oldAssignee> | <oldState> |
    And the following items are part of orders
      | order | item | size | quantity |
      | not_ready | TShirt | M | 1 |
    When the manager attempts to mark the order with ID "not_ready" as delivered
    Then the system shall raise the error "<error>"
    And the order shall be "<oldState>"

    Examples:
      | datePlaced   | deadline    | oldState           | oldAssignee | error                                                             |
      | today        | InOneDay    | ready for delivery | alice       | cannot mark order as delivered before the delivery date           |
      | today        | InTwoDays   | ready for delivery | bob         | cannot mark order as delivered before the delivery date           |
      | today        | InThreeDays | ready for delivery | claire      | cannot mark order as delivered before the delivery date           |
      | yesterday    | InTwoDays   | ready for delivery | claire      | cannot mark order as delivered before the delivery date           |
      | yesterday    | InThreeDays | ready for delivery | claire      | cannot mark order as delivered before the delivery date           |
      | two days ago | InThreeDays | ready for delivery | claire      | cannot mark order as delivered before the delivery date           |
      | NULL         | SameDay     | under construction | NULL        | cannot mark an order as delivered if it is not ready for delivery |
      | NULL         | InOneDay    | pending            | NULL        | cannot mark an order as delivered if it is not ready for delivery |
      | today        | SameDay     | placed             | NULL        | cannot mark an order as delivered if it is not ready for delivery |
      | yesterday    | InOneDay    | in preparation     | bob         | cannot mark an order as delivered if it is not ready for delivery |
      | NULL         | InOneDay    | cancelled          | NULL        | cannot mark an order as delivered if it is not ready for delivery |
      | yesterday    | InOneDay    | cancelled          | NULL        | cannot mark an order as delivered if it is not ready for delivery |
