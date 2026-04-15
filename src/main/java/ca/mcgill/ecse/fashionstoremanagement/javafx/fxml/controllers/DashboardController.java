package ca.mcgill.ecse.fashionstoremanagement.javafx.fxml.controllers;

import ca.mcgill.ecse.fashionstoremanagement.controller.FashionStoreManagementController;
import ca.mcgill.ecse.fashionstoremanagement.model.FashionStoreManagement;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class DashboardController {

    @FXML
    private Label userCountLabel;

    @FXML
    private Label orderCountLabel;

    @FXML
    private Label shipmentCountLabel;

    public void initialize() {
        userCountLabel.setText(String.valueOf(FashionStoreManagementController.getFashionStoreManagement().getUsers().size()));
        orderCountLabel.setText(String.valueOf(FashionStoreManagementController.getFashionStoreManagement().getOrders().size()));
        shipmentCountLabel.setText(String.valueOf(FashionStoreManagementController.getFashionStoreManagement().getShipments().size()));
    }
}