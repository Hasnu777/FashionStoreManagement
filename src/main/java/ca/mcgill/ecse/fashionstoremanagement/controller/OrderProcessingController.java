package ca.mcgill.ecse.fashionstoremanagement.controller;

public class OrderProcessingController {

    public static void checkOut(int orderNumber) {
        throw new RuntimeException("TODO");
        // Hassan - calculate the total cost then pass into the event
    }

    public static void payForOrder(int orderNumber, boolean usePoints) {
        throw new RuntimeException("TODO");
        // Hassan - compute the final cost and points used and points awarded to customer and date placed then pass into the event
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
