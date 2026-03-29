package ca.mcgill.ecse.fashionstoremanagement.controller;

import ca.mcgill.ecse.fashionstoremanagement.model.Employee;
import ca.mcgill.ecse.fashionstoremanagement.model.Order;
import ca.mcgill.ecse.fashionstoremanagement.model.User;
import ca.mcgill.ecse.fashionstoremanagement.model.UserRole;

import java.util.Calendar;
import java.util.Date;

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
        //throw new RuntimeException("TODO");

        //The findEmployee method in the userController class is private for some reason. So I basically recreated the method.
        Order order = OrderController.getOrder(orderNumber);
        User user = User.getWithUsername(employeeUsername);
        if (user == null) {
            throw new FashionStoreException(
                    String.format("there is no user with username \"%s\"", employeeUsername));
        }

        Employee employee = null;

        for (UserRole r : user.getRoles()) {
            if (r instanceof Employee) {
               employee = (Employee) r;
            }
        }
        order.assignEmployee(employee);
    }

    public static void finishOrderAssembly(int orderNumber) {
        //throw new RuntimeException("TODO");
        Order order = OrderController.getOrder(orderNumber);
        boolean flag = order.finishAssembly();
        if (!flag){
            throw new FashionStoreException("Unable to finish assembly");
        }
    }

    public static void deliverOrder(int orderNumber) {
        //throw new RuntimeException("TODO");
        /* Hassan - you must calculate the order's actual delivery deadline in Date form
         by using date placed and deadline variables in the form of a Date (lowkey ask Claude)
         then pass it into the event. The event guard will be checking if this delivery deadline
         is on or before the current date.
         */
        Order order = OrderController.getOrder(orderNumber);
        Date datePlaced = order.getDatePlaced();
        Order.DeliveryDeadline deadline = order.getDeadline();
        int count;
        switch (deadline) {
            case SameDay:
                count = 0;
            case InOneDay:
                count = 1;
            case InTwoDays:
                count = 2;
            case InThreeDays:
                count = 3;
            default:
                throw new FashionStoreException("Invalid deadline");
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(datePlaced);
        cal.add(Calendar.DAY_OF_MONTH, count);
        Date actualDate = cal.getTime();
        //not sure what to do with the retuned boolean value here
        order.deliver(actualDate);
    }

    public static void cancelOrder(int orderNumber) {
        //throw new RuntimeException("TODO");
        Order order = OrderController.getOrder(orderNumber);
        boolean flag = order.cancelOrder();
        if (!flag){
            throw new FashionStoreException("Unable to finish assembly");
        }
    }
}
