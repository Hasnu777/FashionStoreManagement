# Fashion Store Management System

A layered Java application for managing a fashion retail store — accounts, inventory, customer orders, and shipments — built with **model-driven engineering** for **ECSE 223: Model-Based Software Engineering** at McGill University.

The domain model is defined declaratively in [Umple](https://umple.org/) (`FashionStoreManagement.ump`) and used to generate the model classes; business logic and validation sit in a separate controller layer on top. The system is tested end-to-end with **Cucumber** behavior-driven feature specs rather than unit tests alone.

## Domain Model

Modeled in Umple with composition, inheritance, and association classes:

- **User** — base account (username, password, name, phone), with 0–2 **UserRole**s
- **Customer** / **Employee** / **Manager** — role subtypes; a `Customer` tracks an address and loyalty points
- **Item** — a named product with a price and loyalty-point value; **SpecialItem** extends this with seasonal/limited-edition availability windows
- **SizedItem** — a specific size + inventory quantity of an `Item` (up to 5 sizes per item, XS–XL)
- **Order** — placed by a `Customer`, optionally assigned to an `Employee`, with a delivery deadline and lazily-computed cost/points fields
- **Shipment** — inbound stock shipments with order and arrival dates
- **OrderItem** / **ShipmentItem** — association classes linking orders/shipments to sized items with a quantity

## Architecture

```
src/main/java/ca/mcgill/ecse/fashionstoremanagement/
├── application/
│   └── FashionStoreApplication.java    # Entry point
├── controller/
│   ├── FashionStoreManagementController.java  # System singleton, seeds default manager account
│   ├── UserController.java             # Register/update/delete customers & employees, validation
│   ├── ItemController.java             # Add/update/delete items and their sized variants
│   ├── OrderController.java            # Create/delete orders, manage order line items
│   ├── OrderProcessingController.java  # Checkout, payment, assignment, delivery, cancellation
│   ├── ShipmentController.java         # Create/order/delete shipments, manage shipment line items
│   └── FashionStoreException.java      # Domain-specific runtime exception
└── model/                              # Umple-generated model classes (from FashionStoreManagement.ump)

src/main/resources/
└── FashionStoreManagement.ump          # Domain model source of truth

src/test/
├── java/.../feature/                   # Cucumber step definitions + JUnit 5 test runner
└── resources/                          # Gherkin .feature files (one per user story)
```

## Status

**Implemented and tested:**
- Account management — registering/updating/deleting customer and employee accounts, with username, password, address, name, and phone number validation (`UserController`)
- Item and inventory management — adding items with sizes and quantities, updating price/points, deleting items, with full validation of price/point/quantity bounds (`ItemController`)
- Order creation and line-item management — creating/deleting draft orders, adding items to an order, adjusting quantities (`OrderController`)
- Shipment management — creating/deleting/ordering shipments and managing shipment contents (`ShipmentController`)

**In progress:** `OrderProcessingController` — checkout, payment (including loyalty points), assigning orders to employees, marking assembly complete, delivery, and cancellation are stubbed out (currently throw `TODO`). This is the piece that drives the order lifecycle state machine and is the next major chunk of work.

## Testing

Behavior is specified as **Gherkin** feature files under `src/test/resources/`, one per user story (e.g. `CreateUpdateDeleteItem.feature`, `RegisterUpdateDeleteCustomer.feature`, `ProcessItemOrder.feature`), each with scenario outlines covering both success and validation-error paths. These are run via **Cucumber** (`cucumber-java`, `cucumber-junit-platform-engine`) on the **JUnit 5** platform.

```bash
./gradlew test
```

## Tech Stack

- **Java 21**
- **Umple** — declarative domain modeling with generated model code
- **Gradle** — build system
- **Cucumber 7** + **JUnit 5** — behavior-driven testing

## Getting Started

```bash
git clone https://github.com/Hasnu777/FashionStoreManagement.git
cd FashionStoreManagement
./gradlew build
./gradlew test
```

## Background

Built as a semester-long group project for ECSE 223 (Model-Based Programming) at McGill, practicing model-driven development with Umple, layered controller architecture, and behavior-driven testing with Cucumber against user-story-level acceptance criteria. `FashionStoreManagement.ump`, along with `.feature` files and Cucumber templates were provided.
