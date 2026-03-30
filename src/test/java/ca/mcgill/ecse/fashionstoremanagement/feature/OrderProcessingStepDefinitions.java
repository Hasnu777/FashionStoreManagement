package ca.mcgill.ecse.fashionstoremanagement.feature;

import ca.mcgill.ecse.fashionstoremanagement.controller.FashionStoreException;
import ca.mcgill.ecse.fashionstoremanagement.controller.OrderController;
import ca.mcgill.ecse.fashionstoremanagement.controller.OrderProcessingController;
import ca.mcgill.ecse.fashionstoremanagement.controller.UserController;
import ca.mcgill.ecse.fashionstoremanagement.model.FashionStoreManagement;
import ca.mcgill.ecse.fashionstoremanagement.model.Order;
import io.cucumber.java.PendingException;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class OrderProcessingStepDefinitions extends StepDefinitions {

    private int currentOrderNumber;

    private Map<String, Integer> orderIdToNumber = new HashMap<>();

    @When("the user attempts to check out the order with ID {string}")
    public void the_user_attempts_to_check_out_the_order_with_id(String orderId) {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @When("the user attempts to pay for the order with ID {string} {string} their points")
    public void the_user_attempts_to_pay_for_the_order_with_id_without_using_their_points(String orderId,
                                                                                          String usingOrWithoutUsing) {
        int orderNumber = Integer.parseInt(orderId);
        boolean usePoints = usingOrWithoutUsing.equals("using");

//        Retrieve orderNumber
        try {
            OrderProcessingController.payForOrder(orderNumber, usePoints);
        }
        catch (FashionStoreException e) {
            StepDefinitions.error = e;
        }
    }

    @When("the manager attempts to assign the order with ID {string} to {string}")
    public void the_manager_attempts_to_assign_the_order_with_id_to(String orderId, String employeeUsername) {
        // Write code here that turns the phrase above into concrete actions
        //TODO
        try{
            OrderProcessingController.assignOrderToEmployee(orderIdToNumber.get(orderId), employeeUsername);
        } catch (FashionStoreException e){
            StepDefinitions.error = e;
        }
        //throw new PendingException();
    }

    @When("the user attempts to indicate that assembly of the order with ID {string} is finished")
    public void the_user_attempts_to_indicate_that_assembly_of_the_order_with_id_is_finished(String orderId) {
        // Write code here that turns the phrase above into concrete actions
        try{
            OrderProcessingController.finishOrderAssembly(orderIdToNumber.get(orderId));
        } catch (FashionStoreException e) {
            StepDefinitions.error = e;
        }
//        throw new PendingException();
    }

    @When("the user attempts to cancel the order with ID {string}")
    public void the_user_attempts_to_cancel_the_order_with_id(String orderId) {
        // Write code here that turns the phrase above into concrete actions
        try{
            OrderProcessingController.cancelOrder(orderIdToNumber.get(orderId));
        } catch (FashionStoreException e){
            StepDefinitions.error = e;
        }
    }

    @When("the manager attempts to mark the order with ID {string} as delivered")
    public void the_manager_attempts_to_mark_the_order_with_id_as_delivered(String orderId) {
        // Write code here that turns the phrase above into concrete actions

        Order.State state = OrderController.getOrder(orderIdToNumber.get(orderId)).getState();
        assertEquals(state, "delivered"); //prolly not correct implementation
    }

    @Then("the order shall be {string}")
    public void the_order_shall_be(String expectedState) {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Then("the order's placer shall be {string}")
    public void the_order_s_placer_shall_be(String customerUsername) {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Then("the order's assignee shall be {string}")
    public void the_order_s_assignee_shall_be(String employeeUsername) {
        // Write code here that turns the phrase above into concrete actions
        Order order = OrderController.getOrder(currentOrderNumber);
        assert order != null;
        assertEquals(order.getOrderAssignee().getUser().getUsername(), employeeUsername);
    }

    @Then("the order's date placed shall be today")
    public void the_order_s_date_placed_shall_be_today() {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Then("the total cost of the order shall be {int} cents")
    public void the_total_cost_of_the_order_shall_be_cents(Integer expectedCost) {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Then("the final cost of the order, after considering points, shall be {int} cents")
    public void the_final_cost_of_the_order_after_considering_points_shall_be_cents(Integer expectedCost) {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

}