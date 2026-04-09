package ca.mcgill.ecse.fashionstoremanagement.javafx.fxml.controllers;

import ca.mcgill.ecse.fashionstoremanagement.controller.ItemController;
import ca.mcgill.ecse.fashionstoremanagement.controller.FashionStoreException;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class ItemPageController {

    @FXML private TextField nameField;
    @FXML private TextField priceField;
    @FXML private TextField pointsField;
    @FXML private ChoiceBox<String> sizeBox;
    @FXML private TextField quantityField;
    @FXML private Label statusLabel;

    @FXML
    public void initialize() {
        sizeBox.getItems().addAll("XS", "S", "M", "L", "XL");
    }

    @FXML
    public void addItemClicked() {
        try {
            String name = nameField.getText();
            double price = Double.parseDouble(priceField.getText());
            int points = Integer.parseInt(pointsField.getText());
            String size = sizeBox.getValue();
            int quantity = Integer.parseInt(quantityField.getText());
            ItemController.addItem(name, price, points, size, quantity);
            statusLabel.setText("Item added successfully!");
            statusLabel.setStyle("-fx-text-fill: green;");
        } catch (FashionStoreException e) {
            ViewUtils.showError(e.getMessage());
        } catch (Exception e) {
            ViewUtils.showError("Invalid input!");
        }
    }

    @FXML
    public void updatePriceClicked() {
        try {
            String name = nameField.getText();
            double newPrice = Double.parseDouble(priceField.getText());
            ItemController.updateItemPrice(name, newPrice);
            statusLabel.setText("Price updated successfully!");
            statusLabel.setStyle("-fx-text-fill: green;");
        } catch (FashionStoreException e) {
            ViewUtils.showError(e.getMessage());
        } catch (Exception e) {
            ViewUtils.showError("Invalid input!");
        }
    }

    @FXML
    public void updatePointsClicked() {
        try {
            String name = nameField.getText();
            int newPoints = Integer.parseInt(pointsField.getText());
            ItemController.updateItemPoints(name, newPoints);
            statusLabel.setText("Points updated successfully!");
            statusLabel.setStyle("-fx-text-fill: green;");
        } catch (FashionStoreException e) {
            ViewUtils.showError(e.getMessage());
        } catch (Exception e) {
            ViewUtils.showError("Invalid input!");
        }
    }

    @FXML
    public void deleteItemClicked() {
        try {
            String name = nameField.getText();
            ItemController.deleteItem(name);
            statusLabel.setText("Item deleted successfully!");
            statusLabel.setStyle("-fx-text-fill: green;");
        } catch (FashionStoreException e) {
            ViewUtils.showError(e.getMessage());
        }
    }
}