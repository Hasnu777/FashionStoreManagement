package ca.mcgill.ecse.fashionstoremanagement.feature;

import ca.mcgill.ecse.fashionstoremanagement.controller.FashionStoreException;
import ca.mcgill.ecse.fashionstoremanagement.controller.OrderController;
import ca.mcgill.ecse.fashionstoremanagement.controller.OrderProcessingController;
import ca.mcgill.ecse.fashionstoremanagement.controller.UserController;
import ca.mcgill.ecse.fashionstoremanagement.model.FashionStoreManagement;
import ca.mcgill.ecse.fashionstoremanagement.model.Order;
import ca.mcgill.ecse.fashionstoremanagement.model.*;
import io.cucumber.java.Before;
import io.cucumber.java.PendingException;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.sql.Date;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class OrderProcessingStepDefinitions extends StepDefinitions {

    private int currentOrderNumber = OrderStepDefinitions.lastCreatedOrder.getOrderNumber();

    private Map<String, String> orderIdToNumber = OrderStepDefinitions.orderIds;

    /**
     * Attempts to check out a specific order based on its string ID.
     * @param orderId the string identifier of the order to be checked out
     */
    @When("the user attempts to check out the order with ID {string}")
    public void the_user_attempts_to_check_out_the_order_with_id(String orderId) {
        // Parse the mapped string ID into the actual integer order number
        int orderNumber = Integer.parseInt(orderIdToNumber.get(orderId));

        // Attempt the checkout process via the controller and catch any system errors
        try {
            OrderProcessingController.checkOut(orderNumber);
        } catch (FashionStoreException e) {
            StepDefinitions.error = e;
        }
    }

    /**
     * Attempts to pay for an order, with an option to use or not use existing loyalty points.
     * @param orderId the string identifier of the order to be paid for
     * @param usingOrWithoutUsing specifies whether the user is "using" or "without using" points
     */
    @When("the user attempts to pay for the order with ID {string} {string} their points")
    public void the_user_attempts_to_pay_for_the_order_with_id_without_using_their_points(String orderId, String usingOrWithoutUsing) {
        // Retrieve and store the current order number for subsequent verification steps
        int orderNumber = Integer.parseInt(orderIdToNumber.get(orderId));
        currentOrderNumber = orderNumber;

        // Determine the boolean flag for point usage based on the Gherkin string
        boolean usePoints = usingOrWithoutUsing.equals("using");

        // Execute payment processing through the controller
        try {
            OrderProcessingController.payForOrder(orderNumber, usePoints);
        } catch (FashionStoreException e) {
            StepDefinitions.error = e;
        }
    }

    /**
     * Attempts to assign a specific order to an employee.
     * @param orderId the string identifier of the order to assign
     * @param employeeUsername the username of the employee taking the assignment
     */
    @When("the manager attempts to assign the order with ID {string} to {string}")
    public void the_manager_attempts_to_assign_the_order_with_id_to(String orderId, String employeeUsername) {
        try {
            // Update the state tracker and invoke the controller assignment logic
            currentOrderNumber = Integer.parseInt(orderIdToNumber.get(orderId));
            OrderProcessingController.assignOrderToEmployee(currentOrderNumber, employeeUsername);
        } catch (FashionStoreException e){
            StepDefinitions.error = e;
        }
    }

    /**
     * Attempts to mark the assembly process of a specific order as finished.
     * @param orderId the string identifier of the order being assembled
     */
    @When("the user attempts to indicate that assembly of the order with ID {string} is finished")
    public void the_user_attempts_to_indicate_that_assembly_of_the_order_with_id_is_finished(String orderId) {
        try {
            // Track the order and trigger the assembly completion state transition
            currentOrderNumber = Integer.parseInt(orderIdToNumber.get(orderId));
            OrderProcessingController.finishOrderAssembly(currentOrderNumber);
        } catch (FashionStoreException e) {
            StepDefinitions.error = e;
        }
    }

    /**
     * Attempts to cancel an existing order.
     * @param orderId the string identifier of the order to cancel
     */
    @When("the user attempts to cancel the order with ID {string}")
    public void the_user_attempts_to_cancel_the_order_with_id(String orderId) {
        try {
            // Track the order and trigger the cancellation state transition
            currentOrderNumber = Integer.parseInt(orderIdToNumber.get(orderId));
            OrderProcessingController.cancelOrder(currentOrderNumber);
        } catch (FashionStoreException e){
            StepDefinitions.error = e;
        }
    }

    /**
     * Attempts to mark a given order as successfully delivered.
     * @param orderId the string identifier of the delivered order
     */
    @When("the manager attempts to mark the order with ID {string} as delivered")
    public void the_manager_attempts_to_mark_the_order_with_id_as_delivered(String orderId) {
        currentOrderNumber = Integer.parseInt(orderIdToNumber.get(orderId));
        System.out.println("Current order number is " + currentOrderNumber);

        try {
            // Execute the delivery logic in the controller
            OrderProcessingController.deliverOrder(currentOrderNumber);
        } catch (FashionStoreException e) {
            StepDefinitions.error = e;
        }
    }

    /**
     * Verifies that the order has transitioned to the expected state machine state.
     * @param expectedState the human-readable string representation of the expected state
     */
    @Then("the order shall be {string}")
    public void the_order_shall_be(String expectedState) {
        // Retrieve the actual state enum from the model
        Order.State state = OrderController.getOrder(currentOrderNumber).getState();
        String stateString = "";

        // Map the internal Enum state to the expected Gherkin string format
        if (state == Order.State.UnderConstruction) {
            stateString = "under construction";
        } else if (state == Order.State.Pending) {
            stateString = "pending";
        } else if (state == Order.State.Placed) {
            stateString = "placed";
        } else if (state == Order.State.InPreparation) {
            stateString = "in preparation";
        } else if (state == Order.State.ReadyForDelivery) {
            stateString = "ready for delivery";
        } else if (state == Order.State.Delivered) {
            stateString = "delivered";
        } else {
            stateString = "cancelled";
        }

        // Assert that the mapped state matches the test's expectation
        assertEquals(expectedState, stateString);
    }

    /**
     * Verifies that the customer who placed the order matches the expected username.
     * @param customerUsername the expected username of the order placer
     */
    @Then("the order's placer shall be {string}")
    public void the_order_s_placer_shall_be(String customerUsername) {
        Order order = OrderController.getOrder(currentOrderNumber);
        assert order != null;

        // Fetch the placer and verify their username
        Customer placer = order.getOrderPlacer();
        assertEquals(placer.getUser().getUsername(), customerUsername);
    }

    /**
     * Verifies that the employee assigned to the order matches the expected username.
     * @param employeeUsername the expected username of the assigned employee, or "NULL" if unassigned
     */
    @Then("the order's assignee shall be {string}")
    public void the_order_s_assignee_shall_be(String employeeUsername) {
        Order order = OrderController.getOrder(currentOrderNumber);
        assert order != null;

        try {
            // Attempt to get the assignee's username
            User employee = order.getOrderAssignee().getUser();
            assertEquals(employee.getUsername(), employeeUsername);
        } catch (NullPointerException e) {
            // If there is no assignee, a NullPointerException is thrown; handle the "NULL" expectation
            assertEquals(employeeUsername, "NULL");
        }
    }

    /**
     * Verifies that the date the order was placed is set to the current system date.
     */
    @Then("the order's date placed shall be today")
    public void the_order_s_date_placed_shall_be_today() {
        Order order = OrderController.getOrder(currentOrderNumber);
        assert order != null;

        // Compare the order's stored placement date with today's date
        Date orderDate = order.getDatePlaced();
        Date today = new Date(System.currentTimeMillis());
        assertEquals(today.toLocalDate(), orderDate.toLocalDate());
    }

    /**
     * Verifies the pre-calculated total cost of the order matches the expected amount.
     * @param expectedCost the expected total cost in cents
     */
    @Then("the total cost of the order shall be {int} cents")
    public void the_total_cost_of_the_order_shall_be_cents(Integer expectedCost) {
        Order order = OrderController.getOrder(currentOrderNumber);
        assert order != null;

        // Retrieve the total cost calculated by the controller and verify it
        Integer current = order.getTotalCost();
        assertEquals(expectedCost, current);
    }

    /**
     * Verifies the final cost of the order after factoring in loyalty point usage.
     * @param expectedCost the expected final cost in cents
     */
    @Then("the final cost of the order, after considering points, shall be {int} cents")
    public void the_final_cost_of_the_order_after_considering_points_shall_be_cents(Integer expectedCost) {
        Order order = OrderController.getOrder(currentOrderNumber);
        assert order != null;
        //        Calculate points awarded, check for insufficient stock
        assertEquals(expectedCost, order.getFinalCost());

    }
}