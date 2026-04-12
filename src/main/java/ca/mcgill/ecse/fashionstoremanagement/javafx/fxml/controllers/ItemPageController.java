package ca.mcgill.ecse.fashionstoremanagement.javafx.fxml.controllers;

import ca.mcgill.ecse.fashionstoremanagement.controller.FashionStoreManagementController;
import ca.mcgill.ecse.fashionstoremanagement.controller.ItemController;
import ca.mcgill.ecse.fashionstoremanagement.controller.FashionStoreException;
import ca.mcgill.ecse.fashionstoremanagement.controller.OrderController;
import ca.mcgill.ecse.fashionstoremanagement.javafx.fxml.FashionStoreFxmlView;
import ca.mcgill.ecse.fashionstoremanagement.model.Item;
import ca.mcgill.ecse.fashionstoremanagement.model.Order;
import ca.mcgill.ecse.fashionstoremanagement.model.User;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;

public class ItemPageController {
//    View
    @FXML private TableView<User> itemTable;
    @FXML private TableColumn<User, String> colName;
    @FXML private TableColumn<User, String>  colPrice;
    @FXML private TableColumn<User, String>  colPoints;
    @FXML private TableColumn<User, String>  colSize;
    @FXML private TableColumn<User, String>  colQuant;
    @FXML private Pagination                  pagination;
    private ObservableList<User> allItems;

//    Item Actions
    @FXML private Label statusLabel;
//    Creation
    @FXML private TextField nameField;
    @FXML private TextField priceField;
    @FXML private ChoiceBox<String> pointsField;
    @FXML private ChoiceBox<String> sizeBox;
    @FXML private TextField quantityField;
//    Update
    @FXML private TextField nameUField;
    @FXML private TextField priceUField;
    @FXML private ChoiceBox<String> pointsUField;

    @FXML
    public void initialize() {
        pointsField.getItems().addAll("1", "2", "3", "4", "5");
        sizeBox.getItems().addAll("XS", "S", "M", "L", "XL");
    }

    @FXML
    public void addItemClicked() {
        try {
            String name = nameField.getText();
            double price = Double.parseDouble(priceField.getText());
            int points = Integer.parseInt(pointsField.getValue());
            String size = sizeBox.getValue();
            int quantity = Integer.parseInt(quantityField.getText());
            ItemController.addItem(name, price, points, size, quantity);
            FashionStoreFxmlView.getInstance().refresh();
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
            String name = nameUField.getText();
            double newPrice = Double.parseDouble(priceUField.getText());
            ItemController.updateItemPrice(name, newPrice);
            FashionStoreFxmlView.getInstance().refresh();
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
            String name = nameUField.getText();
            int newPoints = Integer.parseInt(pointsUField.getValue());
            ItemController.updateItemPoints(name, newPoints);
            FashionStoreFxmlView.getInstance().refresh();
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
            String name = nameUField.getText();
            ItemController.deleteItem(name);
            FashionStoreFxmlView.getInstance().refresh();
            statusLabel.setText("Item deleted successfully!");
            statusLabel.setStyle("-fx-text-fill: green;");
        } catch (FashionStoreException e) {
            ViewUtils.showError(e.getMessage());
        }
    }
}