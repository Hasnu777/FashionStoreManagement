package ca.mcgill.ecse.fashionstoremanagement.javafx.fxml.controllers;

import ca.mcgill.ecse.fashionstoremanagement.controller.OrderController;
import ca.mcgill.ecse.fashionstoremanagement.model.Order;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import javafx.collections.ObservableList;
import javafx.scene.control.Pagination;

import java.util.List;

public class OrderPageController {

    @FXML private TableView<Order> orderTable;
    @FXML private TableColumn<Order, Integer> colOrderId;
    @FXML private TableColumn<Order, String>  colCustomer;
    @FXML private TableColumn<Order, String>  colTotal;
    @FXML private TableColumn<Order, String>  colDeadline;
    @FXML private TableColumn<Order, String>  colStatus;
    @FXML private TableColumn<Order, String>  colAssignee;
    @FXML private Pagination                  pagination;

    private ObservableList<Order> allOrders;

    @FXML
    public void initialize() {
        orderTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

//        columns
        colOrderId.setCellValueFactory(new PropertyValueFactory<>("orderNumber"));
        colCustomer.setCellValueFactory(data -> {
            Order order = data.getValue();

            String username = order.getOrderPlacer() != null
                    ? order.getOrderPlacer().getUser().getName()
                    : "N/A";
            return new javafx.beans.property.SimpleStringProperty(username);
        });
        colTotal.setCellValueFactory(data -> {
            Order order = data.getValue();
            String total = String.valueOf(order.getTotalCost());
            return new javafx.beans.property.SimpleStringProperty(total);
        });
        colDeadline.setCellValueFactory(data -> {
            Order order = data.getValue();
            String deadline = order.getDeadline() != null
                    ? order.getDeadline().toString()
                    : "N/A";
            return new javafx.beans.property.SimpleStringProperty(deadline);
        });
        colStatus.setCellValueFactory(data -> {
            Order order = data.getValue();
            String state = order.getState() != null
                    ? order.getState().toString()
                    : "N/A";
            return new javafx.beans.property.SimpleStringProperty(state);
        });
        colAssignee.setCellValueFactory(data -> {
            Order order = data.getValue();
            String assignee = order.getOrderAssignee() != null
                    ? order.getOrderAssignee().getUser().getName()
                    : "N/A";
            return new javafx.beans.property.SimpleStringProperty(assignee);
        });

//        load all orders from the controller
        List<Order> orders = OrderController.getAllOrders();
        orderTable.setItems(FXCollections.observableArrayList(orders));

//        set up pagination
        int pageCount = (int) Math.ceil((double) allOrders.size() / 8); //rows per page
        pagination.setPageCount(pageCount == 0 ? 1 : pageCount);
        pagination.setCurrentPageIndex(0);

        // Show the correct slice of orders whenever the page changes
        pagination.currentPageIndexProperty().addListener((obs, oldIndex, newIndex) ->
                showPage(newIndex.intValue()));

        showPage(0); // show first page immediately
    }

    private void showPage(int pageIndex) {
        int from = pageIndex * 8; // rows per page
        int to   = Math.min(from + 8, allOrders.size()); // rows per page
        orderTable.setItems(FXCollections.observableArrayList(
                allOrders.subList(from, to)));
    }
}