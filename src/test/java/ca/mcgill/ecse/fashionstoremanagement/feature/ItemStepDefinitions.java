package ca.mcgill.ecse.fashionstoremanagement.feature;

import ca.mcgill.ecse.fashionstoremanagement.controller.FashionStoreException;
import ca.mcgill.ecse.fashionstoremanagement.controller.ItemController;
import ca.mcgill.ecse.fashionstoremanagement.model.Item;
import ca.mcgill.ecse.fashionstoremanagement.model.SizedItem;
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

public class ItemStepDefinitions extends StepDefinitions {

    @Before
    public void setup() {
        super.before();
    }


    @Given("the following items exist in the system")
    public void theFollowingItemsExistInTheSystem(DataTable dataTable) {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @When("the manager attempts to add a new item with name {string}, price {double}, points {int}, size {string}, and quantity {int}")
    public void theManagerAttemptsToAddANewItemWithNamePricePricePointsPointsSizeAndQuantityQuantity(String name, double price, int points, String size, int quantity) {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }


    @When("the manager attempts to update the price of item {string} to {double}")
    public void theManagerAttemptsToUpdateThePriceOfItemToNewPrice(String name, double price) {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @When("the manager attempts to update the point value of item {string} to {int}")
    public void theManagerAttemptsToUpdateThePointValueOfItemToNewPoints(String name, int points) {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }


    @When("the manager attempts to delete the item {string}")
    public void theManagerAttemptsToDeleteTheItem(String name) {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }


    @Then("an item shall exist with name {string}")
    public void anItemShallExistWithName(String name) {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Then("the item shall cost {double}")
    public void theItemShallCostPrice(double price) {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }


    @Then("the item shall be worth {int} points")
    public void theItemShallBeWorthPointsPoints(int points) {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }


    @Then("a sized item of size {string} shall exist for item {string}")
    public void aSizedItemOfSizeShallExistForItem(String sizeStr, String itemName) {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Then("the quantity of the sized item {string} of size {string} shall be {int}")
    public void theQuantityOfTheSizedItemOfSizeShallBeQuantity(String name, String size, int quantity) {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Then("the total number of items shall be {int}")
    public void theTotalNumberOfItemsShallBe(int expectedCount) {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Then("no item shall exist with name {string}")
    public void noItemShallExistWithName(String name) {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }
}
