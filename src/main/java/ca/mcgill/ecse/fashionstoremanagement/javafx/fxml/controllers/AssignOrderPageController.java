package ca.mcgill.ecse.fashionstoremanagement.javafx.fxml.controllers;

import ca.mcgill.ecse.fashionstoremanagement.controller.OrderProcessingController;
import ca.mcgill.ecse.fashionstoremanagement.controller.FashionStoreException;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class AssignOrderPageController {

    @FXML private TextField orderNumberField;
    @FXML private TextField employeeUsernameField;
    @FXML private Label statusLabel;

    @FXML
    public void assignClicked() {
        try {
            int orderNumber = Integer.parseInt(orderNumberField.getText());
            // assign the order to the employee
            OrderProcessingController.assignOrderToEmployee(orderNumber, employeeUsernameField.getText());
            statusLabel.setText("Order assigned successfully!");
            statusLabel.setStyle("-fx-text-fill: green;");
        } catch (FashionStoreException e) {
            ViewUtils.showError(e.getMessage());
        } catch (NumberFormatException e) {
            ViewUtils.showError("Please enter a valid order number");
        }
    }
}
