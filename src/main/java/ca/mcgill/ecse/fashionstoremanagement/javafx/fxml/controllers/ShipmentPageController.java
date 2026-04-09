package ca.mcgill.ecse.fashionstoremanagement.javafx.fxml.controllers;

import ca.mcgill.ecse.fashionstoremanagement.controller.ShipmentController;
import ca.mcgill.ecse.fashionstoremanagement.controller.FashionStoreException;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class ShipmentPageController {

    @FXML private TextField shipmentNumberField;
    @FXML private Label statusLabel;

    @FXML
    public void createClicked() {
        try {
            // create a new shipment and show the number
            int shipmentNumber = ShipmentController.createShipment();
            statusLabel.setText("Shipment created! Number: " + shipmentNumber);
            statusLabel.setStyle("-fx-text-fill: green;");
        } catch (FashionStoreException e) {
            ViewUtils.showError(e.getMessage());
        }
    }

    @FXML
    public void deleteClicked() {
        try {
            // parse the number from the text field
            int shipmentNumber = Integer.parseInt(shipmentNumberField.getText());
            ShipmentController.deleteShipment(shipmentNumber);
            statusLabel.setText("Shipment deleted successfully!");
            statusLabel.setStyle("-fx-text-fill: green;");
        } catch (FashionStoreException e) {
            ViewUtils.showError(e.getMessage());
        } catch (NumberFormatException e) {
            ViewUtils.showError("Please enter a valid shipment number");
        }
    }
}
