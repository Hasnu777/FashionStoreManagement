package ca.mcgill.ecse.fashionstoremanagement.controller;

import ca.mcgill.ecse.fashionstoremanagement.model.FashionStoreManagement;
import ca.mcgill.ecse.fashionstoremanagement.model.Item;
import ca.mcgill.ecse.fashionstoremanagement.model.SizedItem;

import java.util.Objects;
import java.util.List;

public class ItemController {

	public static void addItem(String name, double price, int points, String sizeStr, int quantity) {
		FashionStoreManagement system = FashionStoreManagementController.getFashionStoreManagement();
		boolean newSizeForItem = false;
		Item existingItem = Item.getWithName(name);
		// Adding an entirely new item
		if (existingItem == null) {
			if (name.isEmpty()) {
				throw new FashionStoreException("name is required");
			}
			if (price <= 0) {
				throw new FashionStoreException("price must be positive");
			}
			if (points < 1 || points > 5) {
				throw new FashionStoreException("points must be between one and five");
			}
			if (quantity < 0) {
				throw new FashionStoreException("quantity must be non-negative");
			}
			// Item must be created prior to a SizedItem
			Item itemToAdd = new Item(name, price, points, system);
			// Create SizedItem, in-line execution of acquiring Size enum
			SizedItem sizedItemToAdd = system.addSizedItem(SizedItem.Size.valueOf(sizeStr.toUpperCase()), quantity, itemToAdd);
			// Insert new SizedItem object into FashionStoreManagement instance
			system.addSizedItem(sizedItemToAdd);
		}
		// Adding a new size for an item
		else {
			List<SizedItem> sizedItems = existingItem.getSizedItems();
			for (SizedItem sizedItem : sizedItems) {
				if (sizedItem.getSize().equals(SizedItem.Size.valueOf(sizeStr.toUpperCase()))) {
					throw new FashionStoreException("item \"" + name + "\" already has size \"" + sizeStr.toUpperCase() + "\"");
				}
			}
			if (points != existingItem.getLoyaltyPoints()) {
				throw new FashionStoreException("item \"" + name + "\" has a different points value");
			}
			if (price != existingItem.getPrice()) {
				throw new FashionStoreException("item \"" + name + "\" has a different price");
			}

			for (SizedItem sizedItem : sizedItems) {
				if (sizedItem.getSize().equals(SizedItem.Size.valueOf(sizeStr.toUpperCase()))) {
					throw new FashionStoreException("an item called \"" + name + "\" already exists");
				}
			}
			system.addSizedItem(SizedItem.Size.valueOf(sizeStr.toUpperCase()), quantity, existingItem);
		}

//	// Get FashionStoreManagement instance to perform operations on it
//	FashionStoreManagement system = FashionStoreManagementController.getFashionStoreManagement();
//	Item existingItem = Item.getWithName(name);
//	boolean newSizeForItem = false;
//	if (existingItem != null) {
//		List<SizedItem> sizedItems = existingItem.getSizedItems();
//		for (SizedItem sizedItem : sizedItems) {
//			if (sizedItem.getSize().equals(SizedItem.Size.valueOf(sizeStr.toUpperCase()))) {
//				throw new FashionStoreException("an item called \"" + name + "\" already exists");
//			}
//		}
//		newSizeForItem = true;
//	}
//	if (name.isEmpty()) {
//		throw new FashionStoreException("name is required");
//	}
//	if (price <= 0) {
//		throw new FashionStoreException("price must be positive");
//	}
//	if (points < 1 || points > 5) {
//		throw new FashionStoreException("points must be between one and five");
//	}
//	if (quantity < 0) {
//		throw new FashionStoreException("quantity must be non-negative");
//	}
//	// Item must be created prior to a SizedItem
//	Item itemToAdd = null;
//	if (!newSizeForItem) {
//		itemToAdd = new Item(name, price, points, system);
//	}
//	else {
//		itemToAdd = Item.getWithName(name);
//	}
//	// Create SizedItem, in-line execution of acquiring Size enum
//	SizedItem sizedItemToAdd = system.addSizedItem(SizedItem.Size.valueOf(sizeStr.toUpperCase()), quantity, itemToAdd);
//	// Insert new SizedItem object into FashionStoreManagement instance
//	system.addSizedItem(sizedItemToAdd);
	}

	public static void updateItemPrice(String name, double newPrice) {
		// Get the desired item to update and check if it exists
		Item itemToUpdate = Item.getWithName(name);
		if (itemToUpdate == null) {
			throw new FashionStoreException("there is no item called \"" + name + "\"");
		}
		// Verify valid price input
		if (newPrice <= 0) {
			throw new FashionStoreException("price must be positive");
		}
		itemToUpdate.setPrice(newPrice);
	}

	public static void updateItemPoints(String name, int newPoints) {
		// Get the desired item to update and check if it exists
		Item itemToUpdate = Item.getWithName(name);
		if (itemToUpdate == null) {
			throw new FashionStoreException("there is no item called \"" + name + "\"");
		}
		// Verify valid quantity input
		if (newPoints < 1 || newPoints > 5) {
			throw new FashionStoreException("points must be between one and five");
		}
		itemToUpdate.setLoyaltyPoints(newPoints);
	}

	public static void deleteItem(String name) throws FashionStoreException {
		// Get the desired item to delete and verify it exists
		Item itemToDelete = Item.getWithName(name);
		if (itemToDelete == null) {
			throw new FashionStoreException("there is no item called \"" + name + "\"");
		}
		itemToDelete.delete();
	}
}
