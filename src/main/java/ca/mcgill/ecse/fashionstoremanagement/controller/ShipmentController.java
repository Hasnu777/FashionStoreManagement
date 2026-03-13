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
        // throw new RuntimeException("TODO");
        FashionStoreManagement system = FashionStoreManagementController.getFashionStoreManagement();
        try {
            // Create a new shipment
            Shipment shipment = new Shipment(null, null, system);
            return shipment.getShipmentNumber();
        }
        catch (Exception e) {
            throw new FashionStoreException("Could not create shipment");
        }

    }

    public static void deleteShipment(int shipmentNumber) throws FashionStoreException {
        // Acquire the shipment to be deleted
        Shipment shipment = getShipment(shipmentNumber);
        if (shipment == null) {

            // Shipment with given shipmentNumber does not exist
            throw new FashionStoreException("there is no shipment with number \"" + shipmentNumber + "\"");
        }
        if (shipment.getDateOrdered() != null) {

            // Shipment date ordered exist meaning that shipment has been ordered
            throw new FashionStoreException("cannot delete a shipment which has already been ordered");
        }
        shipment.delete();
    }

    public static void orderShipment(int shipmentNumber, Date dateOrdered) throws FashionStoreException {

        // Get the shipment to order
        Shipment shipment = getShipment(shipmentNumber);
        if (shipment == null) {

            // Shipment with given shipmentNumber does not exist
            throw new FashionStoreException("there is no shipment with number \"" + shipmentNumber + "\"");
        }
        if (shipment.getDateOrdered() != null) {
            // Shipment date ordered exist meaning that shipment has been ordered
            throw new FashionStoreException("shipment has already been ordered");
        }
        shipment.setDateOrdered(dateOrdered);
    }

    public static Shipment getShipment(int shipmentNumber) {
        // Find the desired shipment via its number
        FashionStoreManagement system = FashionStoreManagementController.getFashionStoreManagement();
        for (Shipment shipment : system.getShipments()) {
            if (shipment.getShipmentNumber() == shipmentNumber) {
                return shipment;
            }
        }
        // Shipment with given shipmentNumber not found
        return null;
    }


    public static List<Shipment> getAllShipments() {
        FashionStoreManagement system = FashionStoreManagementController.getFashionStoreManagement();
        return system.getShipments();
    }

    public static List<ShipmentItem> getShipmentItems(int shipmentNumber) {
        Shipment shipment = getShipment(shipmentNumber);
        // If shipment with given shipmentNumber is found
        if (shipment != null) {
            return shipment.getShipmentItems();
        }
        // Return empty list
        return new java.util.ArrayList<>();
    }

    public static void addSizedItemToShipment(int shipmentNumber, String itemName, String sizeStr) throws FashionStoreException {
        FashionStoreManagement system = FashionStoreManagementController.getFashionStoreManagement();
        Shipment shipment = getShipment(shipmentNumber);
        if (shipment == null) {
            throw new FashionStoreException("there is no shipment with number \"" + shipmentNumber + "\"");
        }

        if (shipment.getDateOrdered() != null) {
            throw new FashionStoreException("shipment has already been ordered");
        }
        Item item = Item.getWithName(itemName);
        SizedItem targetSizedItem = null;

        // Linear search of target sized item
        if (item != null) {
            SizedItem.Size targetSize = SizedItem.Size.valueOf(sizeStr);
            for (SizedItem sizeItem : item.getSizedItems()) {
                if (sizeItem.getSize() == targetSize) {
                    targetSizedItem = sizeItem;
                    break;
                }
            }
        }

        if (targetSizedItem == null) {
            throw new FashionStoreException("there is no sized item called \"" + itemName + "\" of size \"" + sizeStr + "\"");
        }
        for (ShipmentItem shipItem : shipment.getShipmentItems()) {
            if (shipItem.getItem().equals(targetSizedItem)) {
                throw new FashionStoreException("shipment already includes sized item \"" + itemName + "\" of size \"" + sizeStr + "\"");
            }
        }
        new ShipmentItem(1, system, shipment, targetSizedItem);
    }

    public static void updateQuantityInShipment(int shipmentNumber, String itemName, String sizeStr, int newQuantity) throws FashionStoreException {
            Shipment ship = getShipment(shipmentNumber);
            if (ship == null){
                throw new FashionStoreException("there is no shipment with number \"" + shipmentNumber + "\"");
            }
            if (ship.getDateOrdered() != null){
                throw new FashionStoreException("shipment has already been ordered");
            }

            for (ShipmentItem shipItem : ship.getShipmentItems()) {

                SizedItem sizedItem = shipItem.getItem();
                Item item = sizedItem.getItem();

                // Linear search of target sized item
                if (item.getName().equals(itemName) &&
                        sizedItem.getSize().toString().equals(sizeStr)) {
                    if (newQuantity == 0){
                        ship.removeShipmentItem(shipItem);
                        shipItem.delete();
                    }
                    shipItem.setQuantity(newQuantity);
                    return;
                }
            }

            // If target sized item exists but is not in this shipment
            FashionStoreManagement system = FashionStoreManagementController.getFashionStoreManagement();
            List<SizedItem> list = system.getSizedItems();
            for (SizedItem sizedItem : list){
                if (sizedItem.getItem().getName().equals(itemName) && sizedItem.getSize().toString().equals(sizeStr)){
                    throw new FashionStoreException("shipment does not include sized item \"" + itemName + "\"" + " of size \"" + sizeStr + "\"");
                }
            }

            // If target sized item does not exist
            throw new FashionStoreException("there is no sized item called \"" + itemName + "\" of size \"" + sizeStr + "\"");
    }
}