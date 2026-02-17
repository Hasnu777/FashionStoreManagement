package ca.mcgill.ecse.fashionstoremanagement.feature;

import ca.mcgill.ecse.fashionstoremanagement.controller.FashionStoreException;
import ca.mcgill.ecse.fashionstoremanagement.controller.ShipmentController;
import ca.mcgill.ecse.fashionstoremanagement.model.Item;
import ca.mcgill.ecse.fashionstoremanagement.model.Shipment;
import ca.mcgill.ecse.fashionstoremanagement.model.ShipmentItem;
import ca.mcgill.ecse.fashionstoremanagement.model.SizedItem;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.PendingException;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.cucumber.datatable.DataTable;

import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class ShipmentStepDefinitions extends StepDefinitions {


    @Before
    public void setup() {
        super.before();
    }

    @Given("the following shipments exist")
    public void the_following_shipments_exist(DataTable dataTable) {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Given("the following sized items are part of shipments")
    public void theFollowingSizedItemsArePartOfShipments(DataTable dataTable) {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @When("the manager attempts to create a new shipment")
    public void the_manager_attempts_to_create_a_new_shipment() {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @When("the manager attempts to delete the shipment with ID {string}")
    public void the_manager_attempts_to_delete_the_shipment_with_id(String id) {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @When("the manager attempts to delete the non-existent shipment with shipment number {int}")
    public void the_manager_attempts_to_delete_the_non_existent_shipment_with_shipment_number(Integer number) {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }



    @When("the manager attempts to add sized item {string} of size {string} to the shipment with ID {int}")
    public void theManagerAttemptsToAddSizedItemOfSizeToTheShipmentWithID(String itemName, String sizeStr, Integer shipmentId) {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }


    @When("the manager attempts to set the quantity of sized item {string} of size {string} in the shipment with ID {int} to {int}")
    public void theManagerAttemptsToSetTheQuantityOfSizedItemOfSizeInTheShipmentWithIDShipmentIdToNewQty(String item, String size, int shipmentId, int newQuantity) {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Then("a new shipment shall exist")
    public void a_new_shipment_shall_exist() {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Then("the newly-created shipment shall have {int} items")
    public void the_newly_created_shipment_shall_have_items(Integer expectedCount) {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Then("the newly-created shipment shall not have been ordered yet")
    public void the_newly_created_shipment_shall_not_have_been_ordered_yet() {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Then("no shipment shall exist with ID {string}")
    public void no_shipment_shall_exist_with_id(String id) {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Then("no shipment shall exist with shipment number {int}")
    public void no_shipment_shall_exist_with_shipment_number(Integer number) {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Then("a shipment shall exist with ID {string}")
    public void a_shipment_shall_exist_with_id(String id) {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Then("the total number of shipments shall be {int}")
    public void the_total_number_of_shipments_shall_be(Integer expectedCount) {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Then("the shipment with ID {int} shall include {int} {string} of size {string}")
    public void theShipmentWithIDShallIncludeOfSize(Integer shipmentId, Integer expectedQuantity, String itemName, String sizeStr) {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Then("the shipment with ID {int} shall include {int} distinct sized items")
    public void theShipmentWithIDShallIncludeDistinctSizedItems(Integer shipmentId, Integer expectedCount) {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Then("no shipment shall exist with number {int}")
    public void noShipmentShallExistWithNumber(Integer shipmentId) {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Then("no sized item shall exist with name {string} and size {string}")
    public void noSizedItemShallExistWithNameAndSize(String itemName, String sizeStr) {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Then("the shipment with ID {int} shall not include any sized items called {string} of size {string}")
    public void theShipmentWithIDShallNotIncludeAnySizedItemsCalledOfSize(Integer shipmentId, String itemName, String sizeStr) {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Then("the shipment with ID {int} shall include {int} distinct sized item")
    public void theShipmentWithIDShallIncludeDistinctSizedItem(Integer shipmentId, Integer expectedCount) {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

}
