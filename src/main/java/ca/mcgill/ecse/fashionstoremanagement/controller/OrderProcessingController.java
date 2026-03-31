package ca.mcgill.ecse.fashionstoremanagement.controller;

import ca.mcgill.ecse.fashionstoremanagement.model.*;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;


public class OrderProcessingController {

    private static int calculateTotalCost(Order order) {
        double totalCostDouble = 0.0;

        // Calculate cost with bulk discount
        for (OrderItem orderItem : order.getOrderItems()) {
            System.out.println("Looking at order item: \n\n" + orderItem.toString());
            int quantity = orderItem.getQuantity();
            System.out.println("Quantity: " + quantity);
            double basePrice = orderItem.getItem().getItem().getPrice(); // Price in dollars
            System.out.println("Base price: " + basePrice);

            // Calculate discount (5% per additional item, capped at 45%)
            double discountMultiplier = 0.05 * (quantity - 1);
            if (discountMultiplier > 0.45) {
                discountMultiplier = 0.45;
            }
            System.out.println("Discount multiplier: " + discountMultiplier);

            double discountedPricePerItem = basePrice * (1.0 - discountMultiplier);
            System.out.println("Discounted price per item: " + discountedPricePerItem);
            totalCostDouble += (discountedPricePerItem * quantity);
            System.out.println("Total cost: " + totalCostDouble);
        }

        // Add same-day delivery fee
        if (order.getDeadline() == Order.DeliveryDeadline.SameDay) {
            totalCostDouble += 500; // $5.00 extra fee in cents
        }

        // Convert total cost to cents (since loyalty points are 1 point = 1 cent)
        int totalCostCents = (int) Math.round(totalCostDouble);

        return totalCostCents;

    }

    public static void checkOut(int orderNumber) {
        // throw new RuntimeException("TODO");
        // Hassan - calculate the total cost then pass into the event
        // Retrieve order object
        Order order = OrderController.getOrder(orderNumber);

        if (order == null) {
            throw new FashionStoreException("there is no order with number \"" + orderNumber + "\"");
        }

        // Pending orders cannot be empty
        if (order.getOrderItems().isEmpty()) {
            throw new FashionStoreException("cannot check out an empty order");
        }
        int totalCostCents = calculateTotalCost(order);
        order.setTotalCost(totalCostCents);

        // Trigger state machine event
        boolean success = order.checkout(totalCostCents);
        if (!success) {
//            Order.State orderState = order.getState();
//            if (orderState == Order.State.Pending) {
//                throw new FashionStoreException("order has already been checked out");
//            }
            throw new FashionStoreException("order has already been checked out");
        }
    }

    public static void payForOrder(int orderNumber, boolean usePoints) {
        // Hassan - compute the final cost and points used and points awarded to customer and date placed then pass into the event
//        Retrieve order object
        Order order = OrderController.getOrder(orderNumber);
        if (order == null) {
            throw new FashionStoreException("there is no order with number \"" + orderNumber + "\"");
        }

//        Check for incorrect states
        if (order.getStateFullName().equals("UnderConstruction")) {
            throw new FashionStoreException("cannot pay for an order which has not been checked out");
        }
        else if (order.getStateFullName().equals("Placed")) {
            throw new FashionStoreException("cannot pay for an order which has already been paid for");
        }
        Order.State orderState = order.getState();
        if (orderState == Order.State.InPreparation || orderState == Order.State.ReadyForDelivery || orderState == Order.State.Delivered) {
            throw new FashionStoreException("cannot pay for an order which has already been paid for");
        }
        else if (order.getStateFullName().equals("Cancelled")) {
            throw new FashionStoreException("cannot pay for an order which has been cancelled");
        }

//        Retrieve cost, date placed
        int subtotal = order.getTotalCost();
        if (subtotal == 0) {
            subtotal = calculateTotalCost(order);
            order.setTotalCost(subtotal);
        }
        System.out.println("Subtotal: " + subtotal);
        Date orderDate = new Date(System.currentTimeMillis());
        System.out.println("Order date: " + orderDate);

//        Calculate points awarded, check for insufficient stock
        int pointsToAward = 0;
        for (OrderItem orderItem : order.getOrderItems()) {
            System.out.println("Looking at order item: \n\n" + orderItem.toString());
            SizedItem item = orderItem.getItem();
            System.out.println("Looking at item: \n\n" + item.toString());
            int quantity = orderItem.getQuantity();
            System.out.println("Order item quantity: " + quantity);
            int points = item.getItem().getLoyaltyPoints();
            System.out.println("Order item points: " + points);
            int quantityInInv = item.getQuantityInInventory();
            System.out.println("Order item quantityInInv: " + quantityInInv);

//            Check stock
            if (quantity > quantityInInv) {
                throw new FashionStoreException("insufficient stock of item \"" + item.getItem().getName() + "\"");
            }

//            Increase points
            pointsToAward += quantity*points;
            System.out.println("Updated points to award to: " + pointsToAward);
        }

//        Calculate points to use
        Customer customer = order.getOrderPlacer();
        int pointsInAccount = customer.getLoyaltyPoints();
        System.out.println("Customer points: " + pointsInAccount);
        int pointsToUse = (usePoints) ? Math.min(pointsInAccount, subtotal) : 0;
        System.out.println("Points to use: " + pointsToUse);

//        Calculate final cost and leftover points
        int total = subtotal - pointsToUse;
        System.out.println("Total cost: " + total);

        customer.setLoyaltyPoints(pointsInAccount - pointsToUse + pointsToAward);

//       Pay for order
        order.pay(total, pointsToUse, pointsToAward, orderDate);
        order.setFinalCost(total);
        System.out.println("Order final cost: " + order.getFinalCost());
        for (OrderItem orderItem : order.getOrderItems()) {
            System.out.println("Looking at the following order item: \n" + orderItem.toString());
            SizedItem item = orderItem.getItem();
            System.out.println("Looking at the above order item's assoc'ed size item: \n" + item.toString());
            int quantity = orderItem.getQuantity();
            System.out.println("Looking at the above order item's quantity: \n" + quantity);
            int quantityInInv = item.getQuantityInInventory();
            System.out.println("Looking at the above order item's quantity inventory: \n" + quantityInInv);

            item.setQuantityInInventory(quantityInInv - quantity);
        }

    }

    public static void assignOrderToEmployee(int orderNumber, String employeeUsername) {
        Order order = OrderController.getOrder(orderNumber);
        if (order == null) {
            throw new FashionStoreException("there is no order with number \"" + orderNumber + "\"");
        }
        FashionStoreManagement system = FashionStoreManagementController.getFashionStoreManagement();
        List<Employee> employees = system.getEmployees();
        Employee employee = null;
        for (Employee e : employees) {
            if (e.getUser().getUsername().equals(employeeUsername)) {
                employee = e;
            }
        }
        if (employee == null) {
            List<User> users = system.getUsers();
            for (User u : users) {
                if (u.getUsername().equals(employeeUsername)) {
                    throw new FashionStoreException("\"" + employeeUsername + "\" is not an employee");
                }
            }
            throw new FashionStoreException("there is no user with username \"" + employeeUsername + "\"");
        }
        else {
            Order.State orderState = order.getState();
            if (orderState == Order.State.UnderConstruction || orderState == Order.State.Pending) {
                throw new FashionStoreException("cannot assign employee to order that has not been placed");
            }
            else if (orderState == Order.State.ReadyForDelivery || orderState == Order.State.Delivered) {
                throw new FashionStoreException("cannot assign employee to an order that has already been prepared");
            }
            else if (orderState == Order.State.Cancelled) {
                throw new FashionStoreException("cannot assign employee to an order that has been cancelled");
            }
            order.assignEmployee(employee);
        }
    }

    public static void finishOrderAssembly(int orderNumber) {
        //throw new RuntimeException("TODO");
        Order order = OrderController.getOrder(orderNumber);
        if (order == null) {
            throw new FashionStoreException("there is no order with number \"" + orderNumber + "\"");
        }

        boolean flag = order.finishAssembly();
        if (!flag){
            Order.State orderState = order.getState();
            if (orderState == Order.State.ReadyForDelivery || orderState == Order.State.Delivered) {
                throw new FashionStoreException("cannot finish assembling order that has already been assembled");
            }
            else if (orderState == Order.State.Cancelled) {
                throw new FashionStoreException("cannot finish assembling order because it was cancelled");
            }
            throw new FashionStoreException("cannot finish assembling order because it has not been assigned to an employee");
        }
    }

    public static void deliverOrder(int orderNumber) {
        /* Hassan - you must calculate the order's actual delivery deadline in Date form
         by using date placed and deadline variables in the form of a Date (lowkey ask Claude)
         then pass it into the event. The event guard will be checking if this delivery deadline
         is on or before the current date.
         */

//        Retrieve order object
        Order order = OrderController.getOrder(orderNumber);
        if (order == null) {
            throw new FashionStoreException("there is no order with number \"" + orderNumber + "\"");
        }
        Order.State orderState = order.getState();
        if (orderState != Order.State.ReadyForDelivery) {
            throw new FashionStoreException("cannot mark an order as delivered if it is not ready for delivery");
        }
//        Retrieve datePlaced, deliveryDeadline
        Date datePlaced = order.getDatePlaced();
        Order.DeliveryDeadline deadline = order.getDeadline();
        int deadlineNumber = deadline.ordinal();

//        Create delivery date
        LocalDate localDeliveryDate = datePlaced.toLocalDate().plusDays(deadlineNumber);
        Date deliveryDate = java.sql.Date.valueOf(localDeliveryDate);

//        Check if delivery date has passed
        Date today = new Date(System.currentTimeMillis());
        if (today.before(deliveryDate)){
            throw new FashionStoreException("cannot mark order as delivered before the delivery date");
        }

//        Attempt to deliver order
        if (!order.deliver(deliveryDate)) {
            throw new FashionStoreException("cannot mark an order as delivered if it is not ready for delivery");
        }
    }

    public static void cancelOrder(int orderNumber) {
//        Retrieve order object
        Order order = OrderController.getOrder(orderNumber);
        if (order == null) {
            throw new FashionStoreException("there is no order with number \"" + orderNumber + "\"");
        }
        String state = order.getStateFullName();

//        Ensure correct state
        if (state.equals("InPreparation") || state.equals("ReadyForDelivery") || state.equals("Delivered")) {
            throw new FashionStoreException("cannot cancel an order that has already been assigned to an employee");
        }
        else if (state.equals("Cancelled")) {
            throw new FashionStoreException("order was already cancelled");
        }
        Order.State orderState = order.getState();
        boolean itemsRemoved = true;
        if (orderState == Order.State.UnderConstruction || orderState == Order.State.Pending) {
            itemsRemoved = false;
        }
//        Cancel
        boolean success = order.cancelOrder();

//        Add items back to stock
        if (success && itemsRemoved) {
            for (OrderItem orderItem : order.getOrderItems()) {
                System.out.println("Looking at the following order item: \n" + orderItem.toString());
                SizedItem item = orderItem.getItem();
                System.out.println("Looking at the above order item's assoc'ed size item: \n" + item.toString());
                int quantity = orderItem.getQuantity();
                System.out.println("Looking at the above order item's quantity: \n" + quantity);
                int quantityInInv = item.getQuantityInInventory();
                System.out.println("Looking at the above order item's quantity inventory: \n" + quantityInInv);

                item.setQuantityInInventory(quantityInInv + quantity);
            }
        }
    }
}
