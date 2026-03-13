package ca.mcgill.ecse.fashionstoremanagement.feature;

import ca.mcgill.ecse.fashionstoremanagement.controller.FashionStoreException;
import ca.mcgill.ecse.fashionstoremanagement.controller.FashionStoreManagementController;
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
import ca.mcgill.ecse.fashionstoremanagement.model.FashionStoreManagement;

import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class ShipmentStepDefinitions extends StepDefinitions {
    private Map<String, Integer> shipmentIdToNumber = new HashMap<>();
    private int lastCreatedShipmentNumber = -1;


    @Before
    public void setup() {
        super.before();
        shipmentIdToNumber.clear();
        lastCreatedShipmentNumber = -1;
    }

    @Given("the following shipments exist")
    public void the_following_shipments_exist(DataTable dataTable) {
        FashionStoreManagement system = FashionStoreManagementController.getFashionStoreManagement();
        List<Map<String, String>> rows = dataTable.asMaps(String.class, String.class);

        for(Map<String, String> row : rows){
            String shipmentId = row.get("shipmentId");
            String dateOrderedStr = row.get("dateOrdered");

            Date dateOrdered;
            if(dateOrderedStr.equals("NULL")) {
                dateOrdered = null;
            }else{
                dateOrdered = Date.valueOf(dateOrderedStr);
            }
            Shipment shipment = new Shipment(dateOrdered, null, system);
            shipmentIdToNumber.put(shipmentId, shipment.getShipmentNumber());
        }

        // Write code here that turns the phrase above into concrete actions

    }

    @Given("the following sized items are part of shipments")
    public void theFollowingSizedItemsArePartOfShipments(DataTable dataTable) {
        FashionStoreManagement system = getSystem();
        List<Map<String, String>> rows = dataTable.asMaps(String.class, String.class);
        for(Map<String, String> row : rows){
            String shipmentId = row.get("shipmentId");
            String itemName = row.get("item");
            String sizeStr = row.get("size");

            int quantity = Integer.parseInt(row.get("quantity"));

            int shipmentNumber = shipmentIdToNumber.get(shipmentId);

            Shipment shipment = ShipmentController.getShipment(shipmentNumber);
            assertNotNull(shipment);

            SizedItem sizedItem = findSizedItem(system, itemName, sizeStr);
            assertNotNull(sizedItem);

            new ShipmentItem(quantity, system, shipment, sizedItem);

        }
        // Write code here that turns the phrase above into concrete actions

    }

    @When("the manager attempts to create a new shipment")
    public void the_manager_attempts_to_create_a_new_shipment() {
        try {
            lastCreatedShipmentNumber = ShipmentController.createShipment();
        }
        catch (FashionStoreException e){
            StepDefinitions.error = e;

        }
        // Write code here that turns the phrase above into concrete actions

    }

    @When("the manager attempts to delete the shipment with ID {string}")
    public void the_manager_attempts_to_delete_the_shipment_with_id(String id) {
        try{
            int shipmentNumber = shipmentIdToNumber.get(id.trim());
            ShipmentController.deleteShipment(shipmentNumber);

        }
        catch (FashionStoreException e){
            StepDefinitions.error = e;
        }

        // Write code here that turns the phrase above into concrete actions

    }

    @When("the manager attempts to delete the non-existent shipment with shipment number {int}")
    public void the_manager_attempts_to_delete_the_non_existent_shipment_with_shipment_number(Integer number) {
        try {
            ShipmentController.deleteShipment(number);
        }
        catch(FashionStoreException e){
            StepDefinitions.error = e;

        }

        // Write code here that turns the phrase above into concrete actions

    }



    @When("the manager attempts to add sized item {string} of size {string} to the shipment with ID {int}")
    public void theManagerAttemptsToAddSizedItemOfSizeToTheShipmentWithID(String itemName, String sizeStr, Integer shipmentId) {

        try{
            //maps shipment ID to shipment number
            Integer shipmentNumber = shipmentIdToNumber.get(shipmentId.toString());

            if (shipmentNumber == null) {
                ShipmentController.addSizedItemToShipment(shipmentId, itemName, sizeStr);
            }
            else {
                ShipmentController.addSizedItemToShipment(shipmentNumber, itemName, sizeStr);
            }

        } catch(FashionStoreException e){
            StepDefinitions.error = e;
        }

        // Write code here that turns the phrase above into concrete actions

    }


    @When("the manager attempts to set the quantity of sized item {string} of size {string} in the shipment with ID {int} to {int}")
    public void theManagerAttemptsToSetTheQuantityOfSizedItemOfSizeInTheShipmentWithIDShipmentIdToNewQty(String item, String size, int shipmentId, int newQuantity) {
        try{
            if (newQuantity < 0){
                throw new FashionStoreException("quantity must be non-negative");
            }

            //maps shipment ID to shipment number
            Integer id = shipmentId;
            Integer shipmentNumber = shipmentIdToNumber.get(id.toString());
            if (shipmentNumber != null){
                ShipmentController.updateQuantityInShipment(shipmentNumber, item, size, newQuantity);
            }
            else {
                throw new FashionStoreException("there is no shipment with number \"" + shipmentId + "\"");
            }


        }
        catch(FashionStoreException e){
            StepDefinitions.error = e;
        }

        // Write code here that turns the phrase above into concrete actions

    }

    @Then("a new shipment shall exist")
    public void a_new_shipment_shall_exist() {
        assertNotEquals(-1, lastCreatedShipmentNumber);
        Shipment shipment = ShipmentController.getShipment(lastCreatedShipmentNumber);
        assertNotNull(shipment);

        // Write code here that turns the phrase above into concrete actions

    }

    @Then("the newly-created shipment shall have {int} items")
    public void the_newly_created_shipment_shall_have_items(Integer expectedCount) {
        Shipment shipment = ShipmentController.getShipment(lastCreatedShipmentNumber);
        assertNotNull(shipment);
        assertEquals(expectedCount, shipment.numberOfShipmentItems());

        // Write code here that turns the phrase above into concrete actions

    }

    @Then("the newly-created shipment shall not have been ordered yet")
    public void the_newly_created_shipment_shall_not_have_been_ordered_yet() {
        Shipment shipment = ShipmentController.getShipment(lastCreatedShipmentNumber);
        assertNotNull(shipment);
        assertNull(shipment.getDateOrdered());

        // Write code here that turns the phrase above into concrete actions

    }

    @Then("no shipment shall exist with ID {string}")
    public void no_shipment_shall_exist_with_id(String id) {
        Integer shipmentNumber = shipmentIdToNumber.get(id.trim());
        if(shipmentNumber == null){
            return;
        }
        Shipment shipment = ShipmentController.getShipment(shipmentNumber);
        assertNull(shipment);

        // Write code here that turns the phrase above into concrete actions

    }

    @Then("no shipment shall exist with shipment number {int}")
    public void no_shipment_shall_exist_with_shipment_number(Integer number) {
        Shipment shipment = ShipmentController.getShipment(number);
        assertNull(shipment);

        // Write code here that turns the phrase above into concrete actions

    }

    @Then("a shipment shall exist with ID {string}")
    public void a_shipment_shall_exist_with_id(String id) {
        int shipmentNumber = shipmentIdToNumber.get(id.trim());
        Shipment shipment = ShipmentController.getShipment(shipmentNumber);
        assertNotNull(shipment);

        // Write code here that turns the phrase above into concrete actions

    }

    @Then("the total number of shipments shall be {int}")
    public void the_total_number_of_shipments_shall_be(Integer expectedCount) {
        assertEquals(expectedCount, getSystem().numberOfShipments());

        // Write code here that turns the phrase above into concrete actions

    }

    @Then("the shipment with ID {int} shall include {int} {string} of size {string}")
    public void theShipmentWithIDShallIncludeOfSize(Integer shipmentId, Integer expectedQuantity, String itemName, String sizeStr) {

        Shipment shipment = ShipmentController.getShipment(
                shipmentIdToNumber.get(shipmentId.toString())
        );
        assertNotNull(shipment); //assert that shipment with given shipmentId exists

        SizedItem sizedItem = findSizedItem(getSystem(), itemName, sizeStr);
        assertNotNull(sizedItem); //assert that given sized item exists

        for(ShipmentItem shipmentItem : shipment.getShipmentItems()){
            if(shipmentItem.getItem().equals(sizedItem)){
                assertEquals(expectedQuantity, shipmentItem.getQuantity());
                return;
            }
        }
        fail("Shipment does not contain " + itemName + "of size" + sizeStr);

        // Write code here that turns the phrase above into concrete actions

    }

    @Then("the shipment with ID {int} shall include {int} distinct sized items")
    public void theShipmentWithIDShallIncludeDistinctSizedItems(Integer shipmentId, Integer expectedCount) {

        Shipment shipment = ShipmentController.getShipment(
                shipmentIdToNumber.get(shipmentId.toString())
        );
        assertNotNull(shipment); //assert that shipment with give shipmentId exists
        assertEquals(expectedCount, shipment.numberOfShipmentItems());

        // Write code here that turns the phrase above into concrete actions

    }

    @Then("no shipment shall exist with number {int}")
    public void noShipmentShallExistWithNumber(Integer shipmentId) {

        if (shipmentIdToNumber.get(shipmentId.toString()) == null){
            assertNull(null);
            return;
        }

        Shipment shipment = ShipmentController.getShipment(
                shipmentIdToNumber.get(shipmentId.toString())
        );
        assertNull(shipment);
        // Write code here that turns the phrase above into concrete actions

    }

    @Then("no sized item shall exist with name {string} and size {string}")
    public void noSizedItemShallExistWithNameAndSize(String itemName, String sizeStr) {
        SizedItem sizedItem = findSizedItem(getSystem(), itemName, sizeStr);
        assertNull(sizedItem);


        // Write code here that turns the phrase above into concrete actions

    }

    @Then("the shipment with ID {int} shall not include any sized items called {string} of size {string}")
    public void theShipmentWithIDShallNotIncludeAnySizedItemsCalledOfSize(Integer shipmentId, String itemName, String sizeStr) {
        Shipment shipment = ShipmentController.getShipment(
                shipmentIdToNumber.get(shipmentId.toString())
        );
        assertNotNull(shipment);

        SizedItem sizedItem = findSizedItem(getSystem(), itemName, sizeStr);
        if(sizedItem == null){
            return;
        }
        for(ShipmentItem si : shipment.getShipmentItems()){
            if(si.getItem().equals(sizedItem)){
                fail("Shipment should not contain " + itemName + "of size " + sizeStr);

            }

        }

        // Write code here that turns the phrase above into concrete actions
    }

    @Then("the shipment with ID {int} shall include {int} distinct sized item")
    public void theShipmentWithIDShallIncludeDistinctSizedItem(Integer shipmentId, Integer expectedCount) {
        theShipmentWithIDShallIncludeDistinctSizedItems(shipmentId, expectedCount);


        // Write code here that turns the phrase above into concrete actions

    }
    private SizedItem findSizedItem(FashionStoreManagement system, String itemName, String sizeStr){
        SizedItem.Size size;
        try {
            size = SizedItem.Size.valueOf(sizeStr);
        }
        catch(IllegalArgumentException e) {
            return null;
        }
        for(SizedItem sizedItem : system.getSizedItems()){
            if(sizedItem.getItem().getName().equals(itemName) && sizedItem.getSize().equals(size)){
                return sizedItem;
            }

        }
        return null;
    }

}