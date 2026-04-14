package ca.mcgill.ecse.fashionstoremanagement.controller;

import ca.mcgill.ecse.fashionstoremanagement.model.*;

public class TOMapper {

    // order mapping
    public static TOOrder toTOOrder(Order order) {
        if (order == null) return null;

        return new TOOrder(
                order.getOrderNumber(),
                order.getDatePlaced(),
                order.getDeadline() != null ? order.getDeadline().toString() : null,
                order.getTotalCost(),
                order.getFinalCost(),
                order.getPointsUsedInPayment(),
                order.getPointsAwarded(),
                order.getOrderPlacer() != null ? order.getOrderPlacer().getUser().getUsername() : null,
                null,
                order.getState() != null ? order.getState().toString() : null,
                toTOCustomerBasic(order.getOrderPlacer()) // 🔥 prevent recursion
        );
    }

    // order mapping (shallow)
    private static TOOrder toTOOrderShallow(Order order) {
        if (order == null) return null;

        return new TOOrder(
                order.getOrderNumber(),
                order.getDatePlaced(),
                order.getDeadline() != null ? order.getDeadline().toString() : null,
                order.getTotalCost(),
                order.getFinalCost(),
                order.getPointsUsedInPayment(),
                order.getPointsAwarded(),
                order.getOrderPlacer() != null ? order.getOrderPlacer().getUser().getUsername() : null,
                order.getOrderAssignee() != null ? order.getOrderAssignee().getUser().getUsername() : null,
                order.getState() != null ? order.getState().toString() : null,
                null
        );
    }

    // customer mapping
    public static TOCustomer toTOCustomer(Customer customer) {
        if (customer == null) return null;

        TOCustomer toCustomer = new TOCustomer(
                customer.getUser().getUsername(),
                customer.getUser().getName(),
                customer.getUser().getPhoneNumber(),
                customer.getAddress(),
                customer.getLoyaltyPoints()
        );

        for (Order order : customer.getOrdersPlaced()) {
            toCustomer.addOrdersPlaced(toTOOrderShallow(order));
        }

        return toCustomer;
    }

    // basic mapping (no orders)
    private static TOCustomer toTOCustomerBasic(Customer customer) {
        if (customer == null) return null;

        return new TOCustomer(
                customer.getUser().getUsername(),
                customer.getUser().getName(),
                customer.getUser().getPhoneNumber(),
                customer.getAddress(),
                customer.getLoyaltyPoints()
        );
    }

    public static TOEmployee toTOEmployee(Employee employee) {
        if (employee == null) return null;

        TOEmployee toEmployee = new TOEmployee(
                employee.getUser().getUsername(),
                employee.getUser().getName(),
                employee.getUser().getPhoneNumber()
        );

        for (Order order : employee.getOrdersAssigned()) {
            toEmployee.addOrdersAssigned(toTOOrderShallow(order));
        }

        return toEmployee;
    }

    public static TOUser toTOUser(User user) {
        if (user == null) return null;

        return new TOUser(
                user.getUsername(),
                user.getName(),
                user.getPhoneNumber()
        );
    }
}