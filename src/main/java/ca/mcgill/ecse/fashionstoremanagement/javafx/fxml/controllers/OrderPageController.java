package ca.mcgill.ecse.fashionstoremanagement.javafx.fxml.controllers;

import ca.mcgill.ecse.fashionstoremanagement.controller.OrderController;
import ca.mcgill.ecse.fashionstoremanagement.controller.FashionStoreException;
import ca.mcgill.ecse.fashionstoremanagement.javafx.fxml.FashionStoreFxmlView;
import ca.mcgill.ecse.fashionstoremanagement.model.Order;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class OrderPageController {
    private static ViewOrdersPageController instance;

    @FXML private TextField usernameField;
    @FXML private ChoiceBox<Order.DeliveryDeadline> deadlineField;

    @FXML private Label statusLabel;

    @FXML
    public void initialize() {
        deadlineField.getItems().addAll(Order.DeliveryDeadline.values());
    }

    @FXML
    private void createOrder() {
        try {
            OrderController.createOrder(usernameField.getText(), deadlineField.getValue().toString());
            FashionStoreFxmlView.getInstance().refresh(); // triggers the table to reload
            statusLabel.setText("Order created successfully!");
            statusLabel.setStyle("-fx-text-fill: green;");
            // clear fields
            usernameField.clear();
            deadlineField.setValue(null);
        } catch (FashionStoreException e) {
            ViewUtils.showError(e.getMessage());
        }
    }
}