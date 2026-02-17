package ca.mcgill.ecse.fashionstoremanagement.controller;

import ca.mcgill.ecse.fashionstoremanagement.model.FashionStoreManagement;
import ca.mcgill.ecse.fashionstoremanagement.model.Item;
import ca.mcgill.ecse.fashionstoremanagement.model.Shipment;
import ca.mcgill.ecse.fashionstoremanagement.model.ShipmentItem;
import ca.mcgill.ecse.fashionstoremanagement.model.SizedItem;

import java.sql.Date;
import java.util.List;

public class ShipmentController {

    public static int createShipment() throws FashionStoreException {
        throw new RuntimeException("TODO");
    }

    public static void deleteShipment(int shipmentNumber) throws FashionStoreException {
        throw new RuntimeException("TODO");
    }

    public static void orderShipment(int shipmentNumber, Date dateOrdered) throws FashionStoreException {
        throw new RuntimeException("TODO");
    }

    public static Shipment getShipment(int shipmentNumber) {
        throw new RuntimeException("TODO");
    }

    public static List<Shipment> getAllShipments() {
        throw new RuntimeException("TODO");
    }

    public static List<ShipmentItem> getShipmentItems(int shipmentNumber) {
        throw new RuntimeException("TODO");
    }

    public static void addSizedItemToShipment(int shipmentNumber, String itemName, String sizeStr) throws FashionStoreException {
        throw new RuntimeException("TODO");
    }

    public static void updateQuantityInShipment(int shipmentNumber, String itemName, String sizeStr, int newQuantity) throws FashionStoreException {
        throw new RuntimeException("TODO");
    }
}
