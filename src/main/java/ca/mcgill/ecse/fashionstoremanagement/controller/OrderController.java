package ca.mcgill.ecse.fashionstoremanagement.controller;

import ca.mcgill.ecse.fashionstoremanagement.model.*;

import java.util.List;

public class OrderController {

    public static Order createOrder(String customerUsername, String deadline) throws FashionStoreException {
        if (customerUsername == null || customerUsername.isEmpty()) {
            throw new FashionStoreException("there is no user with username \"" + customerUsername + "\"");
        }
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

        // Iterate through the orders to match the ID, avoiding index out of bounds
        for (Order order : system.getOrders()) {
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

        Order order = getOrder(orderNumber);
        if (order == null) {
            throw new FashionStoreException("there is no order with number \"" + orderNumber + "\"");
        }

        if (order.getDatePlaced() != null) {
            throw new FashionStoreException("order has already been placed");
        }
        Item itemToAdd = Item.getWithName(itemName);
        if (itemToAdd == null) {
            throw new FashionStoreException("there is no item called \"" + itemName + "\"");
        }

        SizedItem.Size targetSize = SizedItem.Size.valueOf(size);

        // Check if sized item is already in order
        for (OrderItem orderItem : order.getOrderItems()) {
            if (orderItem.getItem().getItem().equals(itemToAdd) && orderItem.getItem().getSize() == targetSize) {
                throw new FashionStoreException("order already includes item \"" + itemName + "\" in size \"" + size + "\"");
            }
        }

        SizedItem targetSizedItem = null;

        for (SizedItem sizedItem : system.getSizedItems()) {
            if (sizedItem.getItem().equals(itemToAdd) && sizedItem.getSize() == targetSize) {
                targetSizedItem = sizedItem;
                break;
            }
        }

        // Add sized item to order
        if (targetSizedItem != null) {
            new OrderItem(quantity, system, order, targetSizedItem); // Hassan - use model function to add item to order
        }

    }


    public static void setOrderItemQuantity(int orderNumber, String itemName, String size, int quantity) throws FashionStoreException {

        if (quantity < 0) {
            throw new FashionStoreException("quantity must be non-negative");
        }

        Order order = getOrder(orderNumber);
        if (order == null) {
            throw new FashionStoreException("there is no order with number \"" + orderNumber + "\"");
        }

        if (order.getDatePlaced() != null) {
            throw new FashionStoreException("order has already been placed");
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

