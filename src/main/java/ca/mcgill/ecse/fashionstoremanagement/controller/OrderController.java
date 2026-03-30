package ca.mcgill.ecse.fashionstoremanagement.controller;

import ca.mcgill.ecse.fashionstoremanagement.model.*;

import java.util.List;

public class OrderController {

    public static Order createOrder(String customerUsername, String deadline) throws FashionStoreException {
        // check if no customer username was provided
        if (customerUsername == null || customerUsername.isEmpty() || customerUsername.equals("NULL")) {
            throw new FashionStoreException("customer is required");
        }
        // check if no deadline was provided
        if (deadline == null || deadline.isEmpty() || deadline.equals("NULL")) {
            throw new FashionStoreException("delivery deadline is required");
        }

        FashionStoreManagement system = FashionStoreManagementController.getFashionStoreManagement();
        User user = User.getWithUsername(customerUsername);

        // Check if the user actually exists before checking their roles
        if (user == null) {
            throw new FashionStoreException("there is no user with username \"" + customerUsername + "\"");
        }

        Customer customer = findCustomer(user);

        return new Order(null, null, Order.DeliveryDeadline.valueOf(deadline), system, customer);
    }
    
    public static void deleteOrder(int orderNumber) throws FashionStoreException {
        Order orderToDelete = getOrder(orderNumber);

        if (orderToDelete == null) {
            throw new FashionStoreException("there is no order with number \"" + orderNumber + "\"");
        }

        // Prevent deleting an order that is already placed
        if (orderToDelete.getDatePlaced() != null) {
            throw new FashionStoreException("cannot delete an order which has already been placed");
        }

        orderToDelete.delete();
    }
    
    public static Order getOrder(int orderNumber) {
        FashionStoreManagement system = FashionStoreManagementController.getFashionStoreManagement();
        System.out.println("Entering getOrder()");
        // Iterate through the orders to match the ID, avoiding index out of bounds
        System.out.println("Going through orders right now");
        for (Order order : system.getOrders()) {
            System.out.println("Looking at order with order ID " + order.getOrderNumber());
            System.out.println(order.toString());
            if (order.getOrderNumber() == orderNumber) {
                return order;
            }
        }
        return null;
    }
    
    public static List<Order> getAllOrders() {
        return FashionStoreManagementController.getFashionStoreManagement().getOrders();
    }
    
    public static User findUserByUsername(String username, FashionStoreManagement sys) {
        return User.getWithUsername(username);
    }
    
    public static Item findItemByName(String name, FashionStoreManagement sys) {
        return Item.getWithName(name);

    }

    public static void addItemToOrder(int orderNumber, String itemName, String size, int quantity) throws FashionStoreException {
        FashionStoreManagement system = FashionStoreManagementController.getFashionStoreManagement();

        // make sure the order exists
        Order order = getOrder(orderNumber);
        if (order == null) {
            System.out.println("order is null");
            throw new FashionStoreException("there is no order with number \"" + orderNumber + "\"");
        }
        System.out.println("Acquired the following order:\n\n" + order.toString() + "\nState: " + order.getState());

        // can only add items if the order is still being built
        if (order.getState() != Order.State.UnderConstruction) {
            System.out.println("Order is not under construction");
            throw new FashionStoreException("order has already been placed");
        }

        // make sure the item exists
        Item itemToAdd = Item.getWithName(itemName);
        if (itemToAdd == null) {
            throw new FashionStoreException("there is no item called \"" + itemName + "\"");
        }
        System.out.println("Attempting to add the following item:\n\n" + itemToAdd.toString());
        SizedItem.Size targetSize = SizedItem.Size.valueOf(size.toUpperCase());

        // Check if sized item is already in order
        for (OrderItem orderItem : order.getOrderItems()) {
            if (orderItem.getItem().getItem().equals(itemToAdd) && orderItem.getItem().getSize() == targetSize) {
                throw new FashionStoreException("order already includes item \"" + itemName + "\" in size \"" + size + "\"");
            }
        }

        if (order.getOrderItems().size() == 49) {
            throw new FashionStoreException("order cannot include more than 49 distinct items");
        }

        SizedItem targetSizedItem = null;
        for (SizedItem sizedItem : system.getSizedItems()) {
            if (sizedItem.getItem().equals(itemToAdd) && sizedItem.getSize() == targetSize) {
                targetSizedItem = sizedItem;
                break;
            }
        }
        System.out.println("adding the target item oh em gee");
        // Add sized item to order
        if (targetSizedItem != null) {
            new OrderItem(quantity, system, order, targetSizedItem); // Hassan - use model function to add item to order
        }

    }


    public static void setOrderItemQuantity(int orderNumber, String itemName, String size, int quantity) throws FashionStoreException {

        if (quantity < 0) {
            throw new FashionStoreException("quantity must be non-negative");
        }
        if (quantity > 10) {
            throw new FashionStoreException("quantity cannot exceed 10");
        }


//        if (quantity > 10) {
//            throw new FashionStoreException("a customer cannot order more than 10 of each different item with a specific size");
//        }



        Order order = getOrder(orderNumber);
        if (order == null) {
            throw new FashionStoreException("there is no order with number \"" + orderNumber + "\"");
        }
        System.out.println("Order acquired:\n\n " + order.toString() + "\nState: " + order.getState());
//        if(order.getState() == Order.State.Pending){
//            throw new FashionStoreException("order has already been checked out");
//        }

        if (order.getState() == Order.State.Placed) {
            throw new FashionStoreException("order has already been placed");
        }

        if (order.getState() == Order.State.Pending) {
            throw new FashionStoreException("order has already been checked out");
        }

        Item baseItem = Item.getWithName(itemName);
        if (baseItem == null) {
            throw new FashionStoreException("there is no item called \"" + itemName + "\"");
        }

        SizedItem.Size targetSize = SizedItem.Size.valueOf(size);
        OrderItem itemToUpdate = null;

        for (OrderItem orderItem : order.getOrderItems()) {
            if (orderItem.getItem().getItem().getName().equals(itemName) && orderItem.getItem().getSize() == targetSize) {
                itemToUpdate = orderItem;
                break;
            }
        }

        if (itemToUpdate == null) {
            throw new FashionStoreException("order does not include item \"" + itemName + "\" in size \"" + size + "\"");
        }

        if (quantity > itemToUpdate.getItem().getQuantityInInventory()) {
            throw new FashionStoreException("the quantity requested of the item must be available in inventory");
        }

            if (quantity == 0) {
                itemToUpdate.delete();
            } else {
                itemToUpdate.setQuantity(quantity);
            }
    }

    
    public static Customer findCustomer(User user) throws FashionStoreException {
        // Look through the user's roles to find the Customer role
        for (UserRole role : user.getRoles()) {
            if (role instanceof Customer) {
                return (Customer) role;
            }
        }
        throw new FashionStoreException("\"" + user.getUsername() + "\" is not a customer");
    }
}

