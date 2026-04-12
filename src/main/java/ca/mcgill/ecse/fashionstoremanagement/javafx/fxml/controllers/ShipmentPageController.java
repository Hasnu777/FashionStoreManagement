package ca.mcgill.ecse.fashionstoremanagement.javafx.fxml.controllers;

import ca.mcgill.ecse.fashionstoremanagement.controller.ShipmentController;
import ca.mcgill.ecse.fashionstoremanagement.controller.FashionStoreException;
import ca.mcgill.ecse.fashionstoremanagement.javafx.fxml.FashionStoreFxmlView;
import ca.mcgill.ecse.fashionstoremanagement.model.User;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class ShipmentPageController {
//    View
    @FXML private TableView<User> shipmentTable;
    @FXML private TableColumn<User, String> colNumber;
    @FXML private TableColumn<User, String>  colItems;
    @FXML private TableColumn<User, String>  colPoints;
    @FXML private TableColumn<User, String>  colSize;
    @FXML private TableColumn<User, String>  colQuant;
    @FXML private Pagination                  pagination;
    private ObservableList<User> allUsers;

//    management
    @FXML private TextField shipmentNumberField;
    @FXML private Label statusLabel;

    @FXML private ChoiceBox<String> sizeField;

    @FXML
    public void initialize() {
        sizeField.getItems().addAll("XS", "S", "M", "L", "XL");
    }

    @FXML
    public void createClicked() {
        try {
            // create a new shipment and show the number
            int shipmentNumber = ShipmentController.createShipment();
            FashionStoreFxmlView.getInstance().refresh();
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
            FashionStoreFxmlView.getInstance().refresh();
            statusLabel.setText("Shipment deleted successfully!");
            statusLabel.setStyle("-fx-text-fill: green;");
        } catch (FashionStoreException e) {
            ViewUtils.showError(e.getMessage());
        } catch (NumberFormatException e) {
            ViewUtils.showError("Please enter a valid shipment number");
        }
    }

    @FXML private TextField shipmentNumberUField;
    @FXML private TextField itemNameField;
    @FXML private TextField quantityField;

    @FXML
    public void addItemClicked() {
        try {
            int shipmentNumber = Integer.parseInt(shipmentNumberUField.getText());
            // add the item to the shipment
            ShipmentController.addSizedItemToShipment(shipmentNumber, itemNameField.getText(), sizeField.getValue());
            FashionStoreFxmlView.getInstance().refresh();
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
            int shipmentNumber = Integer.parseInt(shipmentNumberUField.getText());
            int quantity = Integer.parseInt(quantityField.getText());
            // setting quantity to 0 removes the item
            ShipmentController.updateQuantityInShipment(shipmentNumber, itemNameField.getText(), sizeField.getValue(), 0);
            FashionStoreFxmlView.getInstance().refresh();
            statusLabel.setText("Item removed successfully!");
            statusLabel.setStyle("-fx-text-fill: green;");
        } catch (FashionStoreException e) {
            ViewUtils.showError(e.getMessage());
        } catch (NumberFormatException e) {
            ViewUtils.showError("Please enter valid numbers");
        }
    }
}
