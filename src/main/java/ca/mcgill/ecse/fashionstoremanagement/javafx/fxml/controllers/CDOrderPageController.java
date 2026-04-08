package ca.mcgill.ecse.fashionstoremanagement.javafx.fxml.controllers;

import ca.mcgill.ecse.fashionstoremanagement.controller.OrderController;
import ca.mcgill.ecse.fashionstoremanagement.controller.UserController;
import ca.mcgill.ecse.fashionstoremanagement.controller.FashionStoreException;
import ca.mcgill.ecse.fashionstoremanagement.javafx.fxml.FashionStoreFxmlView;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class CDOrderPageController {
    private static OrderPageController instance;

    @FXML private TextField usernameField;
    @FXML private TextField deadlineField;

    @FXML
    private void createOrder() {
        try {
            OrderController.createOrder(usernameField.getText(), deadlineField.getText());
            FashionStoreFxmlView.getInstance().refresh(); // triggers the table to reload
            // clear fields
            usernameField.clear();
            deadlineField.clear();
        } catch (FashionStoreException e) {
            // show error to user
            System.out.println(e.getMessage());
        }
    }
}