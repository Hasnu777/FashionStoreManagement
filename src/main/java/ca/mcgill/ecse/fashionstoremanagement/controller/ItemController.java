package ca.mcgill.ecse.fashionstoremanagement.controller;

import ca.mcgill.ecse.fashionstoremanagement.model.FashionStoreManagement;
import ca.mcgill.ecse.fashionstoremanagement.model.Item;
import ca.mcgill.ecse.fashionstoremanagement.model.SizedItem;

public class ItemController {

	public static void addItem(String name, double price, int points, String sizeStr, int quantity) {
	// Get FashionStoreManagement instance to perform operations on it
	FashionStoreManagement fm = FashionStoreManagementController.getFashionStoreManagement();
	// Item must be created prior to a SizedItem
	Item itemToAdd = new Item(name, price, points, fm);
	// Create SizedItem, in-line execution of acquiring Size enum
	SizedItem sizedItemToAdd = fm.addSizedItem(SizedItem.Size.valueOf(sizeStr), quantity, itemToAdd);
	// Insert new SizedItem object into FashionStoreManagement instance
		fm.addSizedItem(sizedItemToAdd);
	}

	public static void updateItemPrice(String name, double newPrice) throws FashionStoreException {
		throw new RuntimeException("TODO");
	}

	public static void updateItemPoints(String name, int newPoints) throws FashionStoreException {
		throw new RuntimeException("TODO");
	}

	public static void deleteItem(String name) throws FashionStoreException {
		throw new RuntimeException("TODO");
	}

}
