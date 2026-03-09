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
        FashionStoreManagement fm = FashionStoreManagementController.getFashionStoreManagement();
        try {
            Shipment s = new Shipment(null, null, fm);
            return s.getShipmentNumber();
        } catch (Exception e) {
            throw new FashionStoreException("Could not create shipment");
        }

    }

    public static void deleteShipment(int shipmentNumber) throws FashionStoreException {
        //throw new RuntimeException("TODO");

        Shipment shipment = getShipment(shipmentNumber);
        if (shipment == null) {
            throw new FashionStoreException("there is no shipment with number \"" + shipmentNumber + "\"");
        }

        if (shipment.getDateOrdered() != null) {
            throw new FashionStoreException("cannot delete a shipment which has already been ordered");
        }

        shipment.delete();
    }

    public static void orderShipment(int shipmentNumber, Date dateOrdered) throws FashionStoreException {
        //throw new RuntimeException("TODO");
        Shipment shipment = getShipment(shipmentNumber);
        if (shipment == null) {
            throw new FashionStoreException("there is no shipment with number \"" + shipmentNumber + "\"");
        }
        if (shipment.getDateOrdered() != null) {
            throw new FashionStoreException("shipment has already been ordered");
        }
        shipment.setDateOrdered(dateOrdered);
    }

    public static Shipment getShipment(int shipmentNumber) {
        //throw new RuntimeException("TODO");
//        FashionStoreManagement sys = FashionStoreManagementController.getFashionStoreManagement();
//        return sys.getShipment(shipmentNumber);
        FashionStoreManagement sys = FashionStoreManagementController.getFashionStoreManagement();
        for (Shipment s : sys.getShipments()) {
            if (s.getShipmentNumber() == shipmentNumber) {
                return s;
            }
        }
        return null;
    }


    public static List<Shipment> getAllShipments() {
        //throw new RuntimeException("TODO");
        FashionStoreManagement sys = FashionStoreManagementController.getFashionStoreManagement();
        return sys.getShipments();
    }

    public static List<ShipmentItem> getShipmentItems(int shipmentNumber) {
        //throw new RuntimeException("TODO");
        Shipment shipment = getShipment(shipmentNumber);
        if (shipment != null) {
            return shipment.getShipmentItems();
        }
        return new java.util.ArrayList<>();
    }

    public static void addSizedItemToShipment(int shipmentNumber, String itemName, String sizeStr) throws FashionStoreException {
        //throw new RuntimeException("TODO");
        FashionStoreManagement fm = FashionStoreManagementController.getFashionStoreManagement();
        System.out.println(shipmentNumber);
        Shipment shipment = getShipment(shipmentNumber);
        if (shipment == null) {
            throw new FashionStoreException("there is no shipment with number \"" + shipmentNumber + "\"");
        }

        if (shipment.getDateOrdered() != null) {
            throw new FashionStoreException("shipment has already been ordered");
        }
        Item item = Item.getWithName(itemName);
        SizedItem targetSizedItem = null;

        if (item != null) {
            SizedItem.Size targetSize = SizedItem.Size.valueOf(sizeStr);
            for (SizedItem si : item.getSizedItems()) {
                if (si.getSize() == targetSize) {
                    targetSizedItem = si;
                    break;
                }
            }
        }

        if (targetSizedItem == null) {
            throw new FashionStoreException("there is no sized item called \"" + itemName + "\" of size \"" + sizeStr + "\"");
        }
        for (ShipmentItem si : shipment.getShipmentItems()) {
            if (si.getItem().equals(targetSizedItem)) {
                throw new FashionStoreException("shipment already includes sized item \"" + itemName + "\" of size \"" + sizeStr + "\"");
            }
        }
        new ShipmentItem(1, fm, shipment, targetSizedItem);
    }

    public static void updateQuantityInShipment(int shipmentNumber, String itemName, String sizeStr, int newQuantity) throws FashionStoreException {
        throw new RuntimeException("TODO");
//        try {
//            Shipment ship = getShipment(shipmentNumber);
//            List <ShipmentItem> l = ship.getShipmentItems();
//
//            for (ShipmentItem si : ship.getShipmentItems()) {
//
//                SizedItem sizedItem = si.getItem();
//                Item item = sizedItem.getItem();
//
//                if (item.getName().equals(itemName) &&
//                        sizedItem.getSize().toString().equals(sizeStr)) {
//
//                    si.setQuantity(newQuantity);
//                    return;
//                }
//            }
//
//        } catch (Exception e){
//            throw new FashionStoreException("Unable to add sized item");
//        }
    }
}