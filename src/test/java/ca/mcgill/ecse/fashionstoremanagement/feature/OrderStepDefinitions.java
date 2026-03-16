package ca.mcgill.ecse.fashionstoremanagement.feature;

import ca.mcgill.ecse.fashionstoremanagement.controller.FashionStoreException;
import ca.mcgill.ecse.fashionstoremanagement.controller.OrderController;
import ca.mcgill.ecse.fashionstoremanagement.model.*;
import io.cucumber.java.Before;
import io.cucumber.java.PendingException;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class OrderStepDefinitions extends StepDefinitions {

    @Before
    public void before() {
        super.before();
    }

    @Given("the following orders exist in the system")
    public void theFollowingOrdersExistInTheSystem(List<Map<String, String>> dataTable) {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Given("the following items are part of orders")
    public void theFollowingItemsArePartOfOrders(List<Map<String, String>> dataTable) {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Given("the order with ID {string} has {int} distinct items")
    public void theOrderWithIDHasDistinctItems(String id, int count) {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @When("{string} attempts to create an order with deadline {string}")
    public void attemptsToCreateAnOrderWithDeadline(String username, String deadline) {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @When("the user attempts to delete the order with ID {string}")
    public void theUserAttemptsToDeleteTheOrderWithID(String id) {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @When("the user attempts to delete the non-existent order with ID {string}")
    public void theUserAttemptsToDeleteTheNonExistentOrderWithOrderNumberOrderNumber(String id) {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @When("the user attempts to add item {string} in size {string} to the order with ID {int}")
    public void theUserAttemptsToAddItemInSizeToTheOrderWithID(String itemName, String size, int id) {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @When("the user attempts to add item {string} in size {string} to the non-existent order with order number {int}")
    public void theUserAttemptsToAddItemInSizeToTheNonExistentOrderWithOrderNumberOrderNumber(String itemName, String size, int orderId) {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @When("the user attempts to set the quantity of item {string} in size {string} in the order with ID {string} to {int}")
    public void theUserAttemptsToSetTheQuantityOfItemInSizeInTheOrderWithIDTo(String itemName, String size, String orderId, int quantity) {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @When("the user attempts to set the quantity of item {string} in size {string} in the non-existent order {int} to {int}")
    public void theUserAttemptsToSetTheQuantityOfItemInSizeInTheNonExistentOrderOrderNumberToNewQty(String itemName, String size, int id, int quantity) {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Then("{string} shall have a new order")
    public void shallHaveANewOrder(String username) {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Then("the newly-created order shall have deadline {string}")
    public void theNewlyCreatedOrderShallHaveDeadline(String deadline) {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Then("the newly-created order shall have {int} items")
    public void theNewlyCreatedOrderShallHaveItems(int count) {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Then("the newly-created order shall not have been placed")
    public void theNewlyCreatedOrderShallNotHaveBeenPlaced() {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Then("the total number of orders shall be {int}")
    public void theTotalNumberOfOrdersShallBe(int expectedCount) {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Then("no order shall exist with ID {string}")
    public void noOrderShallExistWithID(String id) {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Then("an order shall exist with ID {string}")
    public void anOrderShallExistWithID(String id) {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Then("the order with ID {string} shall include {int} {string} in size {string}")
    public void theOrderWithIDShallIncludeInSize(String id, int quantity, String name, String size) {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Then("the order with ID {string} shall include {int} distinct items")
    public void theOrderWithIDShallIncludeNumItemsDistinctItems(String id, int quantity) {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Then("the order with ID {string} shall not include any items called {string}")
    public void theOrderWithIDShallNotIncludeAnyItemsCalled(String arg0, String arg1) {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Then("the order with ID {string} shall include {int} distinct item")
    public void theOrderWithIDShallIncludeDistinctItem(String arg0, int arg1) {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }
}
