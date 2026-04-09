package ca.mcgill.ecse.fashionstoremanagement.javafx.fxml.controllers;

import ca.mcgill.ecse.fashionstoremanagement.controller.ShipmentController;
import ca.mcgill.ecse.fashionstoremanagement.controller.FashionStoreException;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class ShipmentItemPageController {

    @FXML private TextField shipmentNumberField;
    @FXML private TextField itemNameField;
    @FXML private TextField sizeField;
    @FXML private TextField quantityField;
    @FXML private Label statusLabel;

    @FXML
    public void addItemClicked() {
        try {
            int shipmentNumber = Integer.parseInt(shipmentNumberField.getText());
            // add the item to the shipment
            ShipmentController.addSizedItemToShipment(shipmentNumber, itemNameField.getText(), sizeField.getText());
            statusLabel.setText("Item added successfully!");
            statusLabel.setStyle("-fx-text-fill: green;");
        } catch (FashionStoreException e) {
            ViewUtils.showError(e.getMessage());
        } catch (NumberFormatException e) {
            ViewUtils.showError("Please enter a valid shipment number");
        }
    }

    @FXML
    public void removeItemClicked() {
        try {
            int shipmentNumber = Integer.parseInt(shipmentNumberField.getText());
            int quantity = Integer.parseInt(quantityField.getText());
            // setting quantity to 0 removes the item
            ShipmentController.updateQuantityInShipment(shipmentNumber, itemNameField.getText(), sizeField.getText(), 0);
            statusLabel.setText("Item removed successfully!");
            statusLabel.setStyle("-fx-text-fill: green;");
        } catch (FashionStoreException e) {
            ViewUtils.showError(e.getMessage());
        } catch (NumberFormatException e) {
            ViewUtils.showError("Please enter valid numbers");
        }
    }
}
