package ca.mcgill.ecse.fashionstoremanagement.controller;

import ca.mcgill.ecse.fashionstoremanagement.model.*;

import java.util.List;

public class OrderController {
    
    public static Order createOrder(String customerUsername, String deadline) throws FashionStoreException {
        // throw new RuntimeException("TODO");
        try {
            Order o = new Order(null, null, null, null);
            return o;
        } catch(Exception e){
            throw new FashionStoreException("Could not create order");
        }
    }
    
    public static void deleteOrder(int orderNumber) throws FashionStoreException {
        // throw new RuntimeException("TODO");
        try {
            getOrder(orderNumber).delete();
        } catch (Exception e) {
            throw new FashionStoreException("Order doesn't exist");
        }
    }
    
    public static Order getOrder(int orderNumber) {
        // throw new RuntimeException("TODO");
        return FashionStoreManagementController.getFashionStoreManagement().getOrder(orderNumber);
    }
    
    public static List<Order> getAllOrders() {
        // throw new RuntimeException("TODO");
        return FashionStoreManagementController.getFashionStoreManagement().getOrders();
    }
    
    public static User findUserByUsername(String username, FashionStoreManagement sys) {
        throw new RuntimeException("TODO");

    }
    
    public static Item findItemByName(String name, FashionStoreManagement sys) {
       throw new RuntimeException("TODO");

    }

    public static void addItemToOrder(int orderNumber, String itemName, String size) throws FashionStoreException {
        // throw new RuntimeException("TODO");
        try{
            Order order = getOrder(orderNumber);
            Item itemToAdd = Item.getWithName(itemName);
            SizedItem targetSizedItem = null;
            SizedItem.Size targetSize = SizedItem.Size.valueOf(size);
            tar
        } catch (Exception e) {
            throw new FashionStoreException("Couldn't add item");
        }
    }

    public static void setOrderItemQuantity(int orderNumber, String itemName, String size, int quantity) throws FashionStoreException {
        throw new RuntimeException("TODO");
    }
    
    public static Customer findCustomer(User user) throws FashionStoreException {
        throw new RuntimeException("TODO");
    }
}

