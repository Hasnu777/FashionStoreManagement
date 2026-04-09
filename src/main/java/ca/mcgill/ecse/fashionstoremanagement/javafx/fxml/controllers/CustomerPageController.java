package ca.mcgill.ecse.fashionstoremanagement.javafx.fxml.controllers;

import ca.mcgill.ecse.fashionstoremanagement.controller.UserController;
import ca.mcgill.ecse.fashionstoremanagement.controller.FashionStoreException;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class CustomerPageController {

    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private TextField addressField;
    @FXML private TextField nameField;
    @FXML private TextField phoneField;
    @FXML private Label statusLabel;

    @FXML
    public void registerClicked() {
        try {
            UserController.registerNewCustomer(
                    usernameField.getText(),
                    passwordField.getText(),
                    addressField.getText());
            statusLabel.setText("Customer registered successfully!");
            statusLabel.setStyle("-fx-text-fill: green;");
        } catch (FashionStoreException e) {
            ViewUtils.showError(e.getMessage());
        }
    }

    @FXML
    public void updateAddressClicked() {
        try {
            UserController.updateAddress(usernameField.getText(), addressField.getText());
            statusLabel.setText("Address updated successfully!");
            statusLabel.setStyle("-fx-text-fill: green;");
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
        } catch (FashionStoreException e) {
            ViewUtils.showError(e.getMessage());
        }
    }

    @FXML
    public void deleteClicked() {
        try {
            UserController.deleteCustomer(usernameField.getText());
            statusLabel.setText("Customer deleted successfully!");
            statusLabel.setStyle("-fx-text-fill: green;");
        } catch (FashionStoreException e) {
            ViewUtils.showError(e.getMessage());
        }
    }
}