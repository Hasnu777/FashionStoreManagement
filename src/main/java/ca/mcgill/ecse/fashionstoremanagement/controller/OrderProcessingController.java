package ca.mcgill.ecse.fashionstoremanagement.controller;

import ca.mcgill.ecse.fashionstoremanagement.model.*;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;


public class OrderProcessingController {

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
            throw new FashionStoreException("pending orders cannot be empty");
        }

        double totalCostDouble = 0.0;

        // Calculate cost with bulk discount
        for (OrderItem orderItem : order.getOrderItems()) {
            int quantity = orderItem.getQuantity();
            double basePrice = orderItem.getItem().getItem().getPrice(); // Price in dollars

            // Calculate discount (5% per additional item, capped at 45%)
            double discountMultiplier = 0.05 * (quantity - 1);
            if (discountMultiplier > 0.45) {
                discountMultiplier = 0.45;
            }

            double discountedPricePerItem = basePrice * (1.0 - discountMultiplier);
            totalCostDouble += (discountedPricePerItem * quantity);
        }

        // Add same-day delivery fee
        if (order.getDeadline() == Order.DeliveryDeadline.SameDay) {
            totalCostDouble += 5.00; // $5.00 extra fee
        }

        // Convert total cost to cents (since loyalty points are 1 point = 1 cent)
        int totalCostCents = (int) Math.round(totalCostDouble * 100);

        // Trigger state machine event
        boolean success = order.checkout(totalCostCents);
        if (!success) {
            throw new FashionStoreException("cannot checkout an order which is not under construction");
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
        else if (order.getStateFullName().equals("Cancelled")) {
            throw new FashionStoreException("cannot pay for an order which has been cancelled");
        }

//        Retrieve cost, date placed
        int subtotal = order.getTotalCost();
        Date orderDate = order.getDatePlaced();

//        Calculate points awarded, check for insufficient stock
        int pointsToAward = 0;
        for (OrderItem orderItem : order.getOrderItems()) {
            SizedItem item = orderItem.getItem();
            int quantity = orderItem.getQuantity();
            int points = item.getItem().getLoyaltyPoints();
            int quantityInInv = item.getQuantityInInventory();

//            Check stock
            if (quantity > quantityInInv) {
                throw new FashionStoreException("insufficient stock of item \"" + item.getItem().getName() + "\"");
            }

//            Increase points
            pointsToAward += quantity*points;
        }

//        Calculate points to use
        Customer customer = order.getOrderPlacer();
        int pointsInAccount = customer.getLoyaltyPoints();
        int pointsToUse = (usePoints) ? Math.min(pointsInAccount, subtotal) : 0;

//        Calculate final cost and leftover points
        int total = subtotal - pointsToUse;
        customer.setLoyaltyPoints(pointsInAccount - pointsToUse);

//       Pay for order
        order.pay(total, pointsToUse, pointsToAward, orderDate);
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
            throw new FashionStoreException("employee not found");
        }
        else {
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
            throw new FashionStoreException("Unable to finish assembly");
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

//        Cancel
        boolean success = order.cancelOrder();

//        Add items back to stock
        if (success) {
            for (OrderItem orderItem : order.getOrderItems()) {;
                SizedItem item = orderItem.getItem();
                int quantity = orderItem.getQuantity();
                int quantityInInv = item.getQuantityInInventory();

                item.setQuantityInInventory(quantityInInv + quantity);
            }
        }
    }
}
