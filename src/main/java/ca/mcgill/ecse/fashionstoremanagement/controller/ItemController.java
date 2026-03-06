package ca.mcgill.ecse.fashionstoremanagement.controller;

import ca.mcgill.ecse.fashionstoremanagement.model.FashionStoreManagement;
import ca.mcgill.ecse.fashionstoremanagement.model.Item;
import ca.mcgill.ecse.fashionstoremanagement.model.SizedItem;

import java.util.Objects;

public class ItemController {

	public static void addItem(String name, double price, int points, String sizeStr, int quantity) {
	// Get FashionStoreManagement instance to perform operations on it
	FashionStoreManagement fm = FashionStoreManagementController.getFashionStoreManagement();
	Item existingItem = Item.getWithName(name);
	if (existingItem != null) {
		throw new FashionStoreException("an item called \"" + name + "\" already exists");
	}
	if (name.isEmpty()) {
		throw new FashionStoreException("name is required");
	}
	if (price <= 0) {
		throw new FashionStoreException("price must be positive");
	}
	if (points <= 1 || points >= 5) {
		throw new FashionStoreException("points must be between one and five");
	}
	if (quantity < 0) {
		throw new FashionStoreException("quantity must be non-negative");
	}
	// Item must be created prior to a SizedItem
	Item itemToAdd = new Item(name, price, points, fm);
	// Create SizedItem, in-line execution of acquiring Size enum
	SizedItem sizedItemToAdd = fm.addSizedItem(SizedItem.Size.valueOf(sizeStr), quantity, itemToAdd);
	// Insert new SizedItem object into FashionStoreManagement instance
		fm.addSizedItem(sizedItemToAdd);
	}

	public static void updateItemPrice(String name, double newPrice) {
		Item itemToUpdate = Item.getWithName(name);
		itemToUpdate.setPrice(newPrice);
	}

	public static void updateItemPoints(String name, int newPoints) {
		Item itemToUpdate = Item.getWithName(name);
		itemToUpdate.setLoyaltyPoints(newPoints);
	}

	public static void deleteItem(String name) throws FashionStoreException {
		Item itemToDelete = Item.getWithName(name);
		itemToDelete.delete();
	}

}
