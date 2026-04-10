package ca.mcgill.ecse.fashionstoremanagement.javafx.fxml.controllers;

import ca.mcgill.ecse.fashionstoremanagement.controller.OrderController;
import ca.mcgill.ecse.fashionstoremanagement.controller.OrderProcessingController;
import ca.mcgill.ecse.fashionstoremanagement.controller.FashionStoreException;
import ca.mcgill.ecse.fashionstoremanagement.javafx.fxml.FashionStoreFxmlView;
import ca.mcgill.ecse.fashionstoremanagement.model.Order;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class OrderPageController {

    @FXML private TextField orderNumberField;
    @FXML private TextField usernameField;
    @FXML private ChoiceBox<Order.DeliveryDeadline> deadlineField;

    @FXML private TextField itemNameField;
    @FXML private ChoiceBox<String> sizeBox;
    @FXML private TextField quantityField;

    @FXML private TextField orderIdActionField;

    @FXML private CheckBox usePointsCheckBox;
    @FXML private Label statusLabel;

    @FXML
    public void initialize() {
        sizeBox.getItems().addAll("XS", "S", "M", "L", "XL");
        deadlineField.getItems().addAll(Order.DeliveryDeadline.values());
    }

    @FXML
    public void createOrderClicked() {
        try {
            String customerUsername = usernameField.getText();
            String deadline = deadlineField.getValue().toString();

            OrderController.createOrder(customerUsername, deadline);

            FashionStoreFxmlView.getInstance().refresh(); // refresh page (update view info) !Important

            statusLabel.setText("Order created successfully!");
            statusLabel.setStyle("-fx-text-fill: green;");
        } catch (FashionStoreException e) {
            ViewUtils.showError(e.getMessage());
        } catch (Exception e) {
            ViewUtils.showError("Invalid input!");
        }
    }

    @FXML
    public void deleteOrderClicked() {
        try {
            int orderNumber = Integer.parseInt(orderIdActionField.getText());
            OrderController.deleteOrder(orderNumber);

            FashionStoreFxmlView.getInstance().refresh();

            statusLabel.setText("Order deleted successfully!");
            statusLabel.setStyle("-fx-text-fill: green;");
        } catch (FashionStoreException e) {
            ViewUtils.showError(e.getMessage());
        } catch (NumberFormatException e) {
            ViewUtils.showError("Order number must be an integer!");
        } catch (Exception e) {
            ViewUtils.showError("Invalid input!");
        }
    }

    @FXML
    public void addItemToOrderClicked() {
        try {
            int orderNumber = Integer.parseInt(orderNumberField.getText());
            String itemName = itemNameField.getText();
            String size = sizeBox.getValue();
            int quantity = Integer.parseInt(quantityField.getText());

            OrderController.addItemToOrder(orderNumber, itemName, size, quantity);

            statusLabel.setText("Item added to order successfully!");
            statusLabel.setStyle("-fx-text-fill: green;");
        } catch (FashionStoreException e) {
            ViewUtils.showError(e.getMessage());
        } catch (NumberFormatException e) {
            ViewUtils.showError("Order number and quantity must be integers!");
        } catch (Exception e) {
            ViewUtils.showError("Invalid input!");
        }
    }

    @FXML
    public void updateOrderItemQuantityClicked() {
        try {
            int orderNumber = Integer.parseInt(orderNumberField.getText());
            String itemName = itemNameField.getText();
            String size = sizeBox.getValue();
            int quantity = Integer.parseInt(quantityField.getText());

            OrderController.setOrderItemQuantity(orderNumber, itemName, size, quantity);

            if (quantity == 0) {
                statusLabel.setText("Item removed from order successfully!");
            } else {
                statusLabel.setText("Order item quantity updated successfully!");
            }
            statusLabel.setStyle("-fx-text-fill: green;");
        } catch (FashionStoreException e) {
            ViewUtils.showError(e.getMessage());
        } catch (NumberFormatException e) {
            ViewUtils.showError("Order number and quantity must be integers!");
        } catch (Exception e) {
            ViewUtils.showError("Invalid input!");
        }
    }

    @FXML
    public void checkOutClicked() {
        try {
            int orderNumber = Integer.parseInt(orderIdActionField.getText());
            OrderProcessingController.checkOut(orderNumber);

            statusLabel.setText("Order checked out successfully!");
            statusLabel.setStyle("-fx-text-fill: green;");
        } catch (FashionStoreException e) {
            ViewUtils.showError(e.getMessage());
        } catch (NumberFormatException e) {
            ViewUtils.showError("Order number must be an integer!");
        } catch (Exception e) {
            ViewUtils.showError("Invalid input!");
        }
    }

    @FXML
    public void payForOrderClicked() {
        try {
            int orderNumber = Integer.parseInt(orderIdActionField.getText());
            boolean usePoints = usePointsCheckBox.isSelected();

            OrderProcessingController.payForOrder(orderNumber, usePoints);

            statusLabel.setText("Order paid successfully!");
            statusLabel.setStyle("-fx-text-fill: green;");
        } catch (FashionStoreException e) {
            ViewUtils.showError(e.getMessage());
        } catch (NumberFormatException e) {
            ViewUtils.showError("Order number must be an integer!");
        } catch (Exception e) {
            ViewUtils.showError("Invalid input!");
        }
    }

    @FXML
    public void finishAssemblyClicked() {
        try {
            int orderNumber = Integer.parseInt(orderIdActionField.getText());
            OrderProcessingController.finishOrderAssembly(orderNumber);

            statusLabel.setText("Order assembly finished successfully!");
            statusLabel.setStyle("-fx-text-fill: green;");
        } catch (FashionStoreException e) {
            ViewUtils.showError(e.getMessage());
        } catch (NumberFormatException e) {
            ViewUtils.showError("Order number must be an integer!");
        } catch (Exception e) {
            ViewUtils.showError("Invalid input!");
        }
    }

    @FXML
    public void deliverOrderClicked() {
        try {
            int orderNumber = Integer.parseInt(orderIdActionField.getText());
            OrderProcessingController.deliverOrder(orderNumber);

            statusLabel.setText("Order marked as delivered successfully!");
            statusLabel.setStyle("-fx-text-fill: green;");
        } catch (FashionStoreException e) {
            ViewUtils.showError(e.getMessage());
        } catch (NumberFormatException e) {
            ViewUtils.showError("Order number must be an integer!");
        } catch (Exception e) {
            ViewUtils.showError("Invalid input!");
        }
    }

    @FXML
    public void cancelOrderClicked() {
        try {
            int orderNumber = Integer.parseInt(orderIdActionField.getText());
            OrderProcessingController.cancelOrder(orderNumber);

            statusLabel.setText("Order cancelled successfully!");
            statusLabel.setStyle("-fx-text-fill: green;");
        } catch (FashionStoreException e) {
            ViewUtils.showError(e.getMessage());
        } catch (NumberFormatException e) {
            ViewUtils.showError("Order number must be an integer!");
        } catch (Exception e) {
            ViewUtils.showError("Invalid input!");
        }
    }
}