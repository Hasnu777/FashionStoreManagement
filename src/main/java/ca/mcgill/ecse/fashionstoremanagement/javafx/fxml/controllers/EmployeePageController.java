package ca.mcgill.ecse.fashionstoremanagement.javafx.fxml.controllers;

import ca.mcgill.ecse.fashionstoremanagement.controller.UserController;
import ca.mcgill.ecse.fashionstoremanagement.controller.FashionStoreException;
import ca.mcgill.ecse.fashionstoremanagement.javafx.fxml.FashionStoreFxmlView;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class EmployeePageController {

    @FXML private TextField usernameField;
    @FXML private TextField nameField;
    @FXML private TextField phoneField;
    @FXML private Label statusLabel;

    @FXML
    public void registerClicked() {
        try {
            UserController.registerNewEmployee(usernameField.getText());
            statusLabel.setText("Employee registered successfully!");
            statusLabel.setStyle("-fx-text-fill: green;");
            FashionStoreFxmlView.getInstance().refresh();
        } catch (FashionStoreException e) {
            ViewUtils.showError(e.getMessage());
        }
    }

    @FXML
    public void updateNameClicked() {
        try {
            UserController.updateName(usernameField.getText(), nameField.getText());
            statusLabel.setText("Name updated successfully!");
            statusLabel.setStyle("-fx-text-fill: green;");
            FashionStoreFxmlView.getInstance().refresh();
        } catch (FashionStoreException e) {
            ViewUtils.showError(e.getMessage());
        }
    }

    @FXML
    public void updatePhoneClicked() {
        try {
            UserController.updatePhoneNumber(usernameField.getText(), phoneField.getText());
            statusLabel.setText("Phone updated successfully!");
            statusLabel.setStyle("-fx-text-fill: green;");
            FashionStoreFxmlView.getInstance().refresh();
        } catch (FashionStoreException e) {
            ViewUtils.showError(e.getMessage());
        }
    }

    @FXML
    public void deleteClicked() {
        try {
            UserController.deleteEmployee(usernameField.getText());
            statusLabel.setText("Employee deleted successfully!");
            statusLabel.setStyle("-fx-text-fill: green;");
            FashionStoreFxmlView.getInstance().refresh();
        } catch (FashionStoreException e) {
            ViewUtils.showError(e.getMessage());
        }
    }
}