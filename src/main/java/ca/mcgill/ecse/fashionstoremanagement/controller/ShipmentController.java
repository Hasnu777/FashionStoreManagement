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
        try {
            Shipment s = new Shipment(null, null, null);
            return s.getShipmentNumber();
        } catch (Exception e) {
            throw new FashionStoreException("Could not create shipment");
        }

    }

    public static void deleteShipment(int shipmentNumber) throws FashionStoreException {
        //throw new RuntimeException("TODO");
        try {
            getShipment(shipmentNumber).delete();
        } catch (Exception e){
            throw new FashionStoreException("No shipment with this number exists");
        }
    }

    public static void orderShipment(int shipmentNumber, Date dateOrdered) throws FashionStoreException {
        //throw new RuntimeException("TODO");
        try{
            getShipment(shipmentNumber).setDateOrdered(dateOrdered);
        } catch (Exception e){
            throw new FashionStoreException("Unable to order shipment");
        }

    }

    public static Shipment getShipment(int shipmentNumber) {
        //throw new RuntimeException("TODO");
            FashionStoreManagement sys = FashionStoreManagementController.getFashionStoreManagement();
            return sys.getShipment(shipmentNumber);
    }

    public static List<Shipment> getAllShipments() {
        //throw new RuntimeException("TODO");
        FashionStoreManagement sys = FashionStoreManagementController.getFashionStoreManagement();
        return sys.getShipments();
    }

    public static List<ShipmentItem> getShipmentItems(int shipmentNumber) {
        //throw new RuntimeException("TODO");
        FashionStoreManagement sys = FashionStoreManagementController.getFashionStoreManagement();
        return sys.getShipmentItems();
    }

    public static void addSizedItemToShipment(int shipmentNumber, String itemName, String sizeStr) throws FashionStoreException {
        //throw new RuntimeException("TODO");
        try {
            Shipment ship = getShipment(shipmentNumber);
            Item existingItem = Item.getWithName(itemName);
            existingItem.addSizedItem(SizedItem.Size.valueOf(sizeStr), 1, existingItem.getFashionStoreManagement());
        } catch (Exception e){
            throw new FashionStoreException("Unable to add sized item");
        }
    }

    public static void updateQuantityInShipment(int shipmentNumber, String itemName, String sizeStr, int newQuantity) throws FashionStoreException {
        //throw new RuntimeException("TODO");
        try {
            Shipment ship = getShipment(shipmentNumber);
            List <ShipmentItem> l = ship.getShipmentItems();

            for (ShipmentItem si : ship.getShipmentItems()) {

                SizedItem sizedItem = si.getItem();
                Item item = sizedItem.getItem();

                if (item.getName().equals(itemName) &&
                        sizedItem.getSize().toString().equals(sizeStr)) {

                    si.setQuantity(newQuantity);
                    return;
                }
            }

        } catch (Exception e){
            throw new FashionStoreException("Unable to add sized item");
        }
    }
}
