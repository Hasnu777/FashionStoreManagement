package ca.mcgill.ecse.fashionstoremanagement.feature;

import ca.mcgill.ecse.fashionstoremanagement.controller.FashionStoreException;
import ca.mcgill.ecse.fashionstoremanagement.controller.ItemController;
import ca.mcgill.ecse.fashionstoremanagement.model.*;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.Before;
import io.cucumber.java.PendingException;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ItemStepDefinitions extends StepDefinitions {
    private Item currentItem;

    @Before
    public void setup() {
        super.before();
        this.currentItem = null;
    }

    @Given("the following items exist in the system")
    public void theFollowingItemsExistInTheSystem(DataTable dataTable) {
        // Extract DataTable into Item map
        List<Map<String, String>> itemMap= dataTable.asMaps(); // Convert dataTable to iterable map

        // Iterate through list and create each item in the system
        for (Map<String, String> row : itemMap) {
            Item item = this.getSystem().addItem(
                    row.get("name"),
                    Double.parseDouble(row.get("price")),
                    Integer.parseInt(row.get("points"))
            );
            item.addSizedItem(
                    SizedItem.Size.valueOf(row.get("size")),
                    Integer.parseInt(row.get("quantityInInventory")),
                    this.getSystem()
            );
        }
    }

    @When("the manager attempts to add a new item with name {string}, price {double}, points {int}, size {string}, and quantity {int}")
    public void theManagerAttemptsToAddANewItemWithNamePricePricePointsPointsSizeAndQuantityQuantity(String name, double price, int points, String size, int quantity) {
        try {
            Item item = this.getSystem().addItem(name, price, points);
            SizedItem.Size sizeEnum = SizedItem.Size.valueOf(size);
            item.addSizedItem(sizeEnum, quantity, this.getSystem());
        }
        catch (FashionStoreException e) {
            StepDefinitions.error = e;
        }
    }

    @When("the manager attempts to update the price of item {string} to {double}")
    public void theManagerAttemptsToUpdateThePriceOfItemToNewPrice(String name, double price) {
        try {
            Item item = Item.getWithName(name);
            item.setPrice(price);
        }
        catch (FashionStoreException e) {
            StepDefinitions.error = e;
        }
    }

    @When("the manager attempts to update the point value of item {string} to {int}")
    public void theManagerAttemptsToUpdateThePointValueOfItemToNewPoints(String name, int points) {
        try {
            Item item = Item.getWithName(name);
            item.setLoyaltyPoints(points);
        }
        catch (FashionStoreException e) {
            StepDefinitions.error = e;
        }
    }

    @When("the manager attempts to delete the item {string}")
    public void theManagerAttemptsToDeleteTheItem(String name) {
        try {
            Item item = Item.getWithName(name);
            item.delete();
        }
        catch (FashionStoreException e) {
            StepDefinitions.error = e;
        }
    }


    @Then("an item shall exist with name {string}")
    public void anItemShallExistWithName(String name) {
        Item item = Item.getWithName(name);
        assertNotNull(item, "there should be an item with the given name");
        this.currentItem = item;
    }

    @Then("the item shall cost {double}")
    public void theItemShallCostPrice(double price) {
        assertEquals(price, this.currentItem.getPrice());
    }

    @Then("the item shall be worth {int} points")
    public void theItemShallBeWorthPointsPoints(int points) {
        assertEquals(points, this.currentItem.getLoyaltyPoints());
    }

    @Then("a sized item of size {string} shall exist for item {string}")
    public void aSizedItemOfSizeShallExistForItem(String sizeStr, String itemName) {
        Item item = Item.getWithName(itemName);
        assertNotNull(item, "there should be an item with the given name");

        // check if inputted size is valid
        SizedItem.Size size = SizedItem.Size.valueOf(sizeStr);
        assertNotNull(size, "the size should be a valid value");

        // check for sized item of size sizeStr
        SizedItem itemFound = null;
        for (SizedItem s : item.getSizedItems()) {
            if (s.getSize() == size) {
                itemFound = s;
                break;
            }
        }
        assertNotNull(itemFound, "there should be a sized item with the given name and size");
    }

    @Then("the quantity of the sized item {string} of size {string} shall be {int}")
    public void theQuantityOfTheSizedItemOfSizeShallBeQuantity(String name, String size, int quantity) {
        Item item = Item.getWithName(name);
        assertNotNull(item, "there should be an item with the given name");

        SizedItem.Size sizeEnum = SizedItem.Size.valueOf(size);
        assertNotNull(size, "the size should be a valid value");

        SizedItem itemFound = null;
        for (SizedItem s : item.getSizedItems()) {
            if (s.getSize() == sizeEnum) {
                itemFound = s;
                break;
            }
        }
        assertNotNull(itemFound, "there should be a sized item with the given name and size");

        assertEquals(quantity, itemFound.getQuantityInInventory());
    }

    @Then("the total number of items shall be {int}")
    public void theTotalNumberOfItemsShallBe(int expectedCount) {
        assertEquals(expectedCount, this.getSystem().getItems().size());
    }

    @Then("no item shall exist with name {string}")
    public void noItemShallExistWithName(String name) {
        Item item = Item.getWithName(name);
        assertNull(item, "there should be no item with the given name");
    }
}
