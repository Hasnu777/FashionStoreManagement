package ca.mcgill.ecse.fashionstoremanagement.controller;

import ca.mcgill.ecse.fashionstoremanagement.model.*;

import java.sql.Date;

public class OrderProcessingController {

    public static void checkOut(int orderNumber) {
        throw new RuntimeException("TODO");
        // Hassan - calculate the total cost then pass into the event
    }

    public static void payForOrder(int orderNumber, boolean usePoints) {
        // Hassan - compute the final cost and points used and points awarded to customer and date placed then pass into the event

//        Retrieve order object
        Order order = OrderController.getOrder(orderNumber);

//      Retrieve cost, points awarded, date placed
        int subtotal = order.getTotalCost();
        int pointsAwarded = order.getPointsAwarded();
        Date orderDate = order.getDatePlaced();

//        Calculate points to use
        Customer customer = order.getOrderPlacer();
        int pointsInAccount = customer.getLoyaltyPoints();
        int pointsToUse = Math.min(pointsInAccount, subtotal);

//        Calculate final cost and leftover points
        int total;
        int pointsUsed;
        if (usePoints) {
            total = subtotal - pointsToUse;
            customer.setLoyaltyPoints(pointsInAccount - pointsToUse);
        }
        else {
            total = subtotal;
        }

//       Pay for order
        order.pay(total, pointsToUse, pointsAwarded, orderDate);
    }

    public static void assignOrderToEmployee(int orderNumber, String employeeUsername) {
        throw new RuntimeException("TODO");
    }

    public static void finishOrderAssembly(int orderNumber) {
        throw new RuntimeException("TODO");
    }

    public static void deliverOrder(int orderNumber) {
        throw new RuntimeException("TODO");
        /* Hassan - you must calculate the order's actual delivery deadline in Date form
         by using date placed and deadline variables in the form of a Date (lowkey ask Claude)
         then pass it into the event. The event guard will be checking if this delivery deadline
         is on or before the current date.
         */
    }

    public static void cancelOrder(int orderNumber) {
        throw new RuntimeException("TODO");

    }
}
