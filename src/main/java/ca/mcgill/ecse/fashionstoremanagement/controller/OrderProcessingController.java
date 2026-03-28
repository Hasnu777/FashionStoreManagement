package ca.mcgill.ecse.fashionstoremanagement.controller;

import ca.mcgill.ecse.fashionstoremanagement.model.*;

import java.sql.Date;
import java.time.LocalDate;

public class OrderProcessingController {

    public static void checkOut(int orderNumber) {
        throw new RuntimeException("TODO");
        // Hassan - calculate the total cost then pass into the event
    }

    public static void payForOrder(int orderNumber, boolean usePoints) {
        // Hassan - compute the final cost and points used and points awarded to customer and date placed then pass into the event

//        Retrieve order object
        Order order = OrderController.getOrder(orderNumber);

//        Retrieve cost, date placed
        int subtotal = order.getTotalCost();
        Date orderDate = order.getDatePlaced();

//        Calculate points awarded
        int pointsToAward = 0;
        for (OrderItem orderItem : order.getOrderItems()) {
            int quantity = orderItem.getQuantity();
            SizedItem item = orderItem.getItem();
            int points = item.getItem().getLoyaltyPoints();

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
        throw new RuntimeException("TODO");
    }

    public static void finishOrderAssembly(int orderNumber) {
        throw new RuntimeException("TODO");
    }

    public static void deliverOrder(int orderNumber) {
        /* Hassan - you must calculate the order's actual delivery deadline in Date form
         by using date placed and deadline variables in the form of a Date (lowkey ask Claude)
         then pass it into the event. The event guard will be checking if this delivery deadline
         is on or before the current date.
         */

//        Retrieve order object
        Order order = OrderController.getOrder(orderNumber);

//        Retrieve datePlaced, deliveryDeadline
        Date datePlaced = order.getDatePlaced();
        Order.DeliveryDeadline deadline = order.getDeadline();
        int deadlineNumber = deadline.ordinal();

//        Create delivery date
        LocalDate localDeliveryDate = datePlaced.toLocalDate().plusDays(deadlineNumber);
        Date deliveryDate = java.sql.Date.valueOf(localDeliveryDate);

//        Deliver order
        order.deliver(deliveryDate);
    }

    public static void cancelOrder(int orderNumber) {
//        Retrieve order object
        Order order = OrderController.getOrder(orderNumber);

//        Try to cancel
        boolean success = order.cancelOrder();

//        Handle failure
        if (!success) {
            throw new RuntimeException("Cannot cancel order in state: " + order.getState());
        }
    }
}
