package ca.mcgill.ecse.fashionstoremanagement.javafx.fxml.controllers;

import ca.mcgill.ecse.fashionstoremanagement.controller.*;
import ca.mcgill.ecse.fashionstoremanagement.javafx.fxml.FashionStoreFxmlView;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

public class OrderPageController {
//    View variables
    @FXML private TableView<TOOrder> orderTable;
    @FXML private TableColumn<TOOrder, Integer> colOrderId;
    @FXML private TableColumn<TOOrder, String>  colCustomer;
    @FXML private TableColumn<TOOrder, String>  colTotal;
    @FXML private TableColumn<TOOrder, String>  colDeadline;
    @FXML private TableColumn<TOOrder, String>  colStatus;
    @FXML private TableColumn<TOOrder, String>  colAssignee;
    @FXML
    Pagination pagination;
    ObservableList<TOOrder> allOrders;

//    Actions/Management variables
    @FXML private TextField orderNumberField;
    @FXML private TextField usernameField;
    @FXML private ChoiceBox<String> deadlineField;
    @FXML private TextField itemNameField;
    @FXML private ChoiceBox<String> sizeBox;
    @FXML private TextField quantityField;
    @FXML private TextField orderIdActionField;
    @FXML private CheckBox usePointsCheckBox;
    @FXML private Label statusLabel;
    @FXML private TextField employeeUsernameField;

    @FXML
    public void initialize() {
        initializeViewPage();
        initializeActionsPage();
    }

//    Actions functions
    @FXML
    public void initializeActionsPage() {
        sizeBox.getItems().addAll("XS", "S", "M", "L", "XL");
        deadlineField.getItems().addAll("SameDay", "InOneDay", "InTwoDays", "InThreeDays");
    }

    @FXML
    public void createOrderClicked() {
        try {
            String customerUsername = usernameField.getText();
            String deadline = deadlineField.getValue().toString();

            OrderController.createOrder(customerUsername, deadline);

            FashionStoreFxmlView.getInstance().refresh(); // refresh page (update view info) !Important

            System.out.println("good");

            statusLabel.setText("Order created successfully!");
            statusLabel.setStyle("-fx-text-fill: green;");
        } catch (FashionStoreException e) {
            ViewUtils.showError(e.getMessage());
            System.out.println(e.getMessage());
        } catch (Exception e) {
            ViewUtils.showError("Invalid input!");
            System.out.println(e.getMessage());
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

    @FXML
    public void assignEmployeeClicked() {
        try {
            int orderNumber = Integer.parseInt(orderIdActionField.getText());
            // assign the order to the employee
            OrderProcessingController.assignOrderToEmployee(orderNumber, employeeUsernameField.getText());
            statusLabel.setText("Order assigned successfully!");
            statusLabel.setStyle("-fx-text-fill: green;");
        } catch (FashionStoreException e) {
            ViewUtils.showError(e.getMessage());
        } catch (NumberFormatException e) {
            ViewUtils.showError("Please enter a valid order number");
        }
    }


//    View functions
@FXML
public void initializeViewPage() {
    orderTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

    // initialize allOrders first before anything else can throw
    allOrders = FXCollections.observableArrayList();

    setupColumns();
    loadOrders();

    FashionStoreFxmlView.getInstance().registerRefreshEvent(orderTable);
    orderTable.addEventHandler(FashionStoreFxmlView.REFRESH_EVENT, e -> loadOrders());

    pagination.currentPageIndexProperty().addListener((obs, oldVal, newVal) -> showPage(newVal.intValue()));
}

    public void setupColumns() {
//        height and colours
        orderTable.setFixedCellSize(25);
        orderTable.prefHeightProperty().bind(
                Bindings.size(orderTable.getItems()).multiply(orderTable.getFixedCellSize()).add(28)
        );

//        each column 1/6 of width
        colOrderId.prefWidthProperty().bind(orderTable.widthProperty().divide(6));
        colCustomer.prefWidthProperty().bind(orderTable.widthProperty().divide(6));
        colTotal.prefWidthProperty().bind(orderTable.widthProperty().divide(6));
        colDeadline.prefWidthProperty().bind(orderTable.widthProperty().divide(6));
        colStatus.prefWidthProperty().bind(orderTable.widthProperty().divide(6));
        colAssignee.prefWidthProperty().bind(orderTable.widthProperty().divide(6));

//        values
        colOrderId.setCellValueFactory(new PropertyValueFactory<>("orderNumber"));
        colCustomer.setCellValueFactory(data -> {
            TOOrder order = data.getValue();
            String username = "N/A";

            if (order.getOrderPlacer() != null && order.getOrderPlacer() != null) {
                username = order.getOrderPlacer().getUsername();
            }
            System.out.println(order.getOrderPlacer().toString());
            return new javafx.beans.property.SimpleStringProperty(username);
        });
        colTotal.setCellValueFactory(data -> {
            TOOrder order = data.getValue();
            String total = String.valueOf(order.getTotalCost());
            return new javafx.beans.property.SimpleStringProperty(total);
        });
        colDeadline.setCellValueFactory(data -> {
            TOOrder order = data.getValue();
            String deadline = order.getDeadline() != null
                    ? order.getDeadline()
                    : "N/A";
            return new javafx.beans.property.SimpleStringProperty(deadline);
        });
        colStatus.setCellValueFactory(data -> {
            TOOrder order = data.getValue();
            String state = order.getStatus() != null
                    ? order.getStatus().toString()
                    : "N/A";
            return new javafx.beans.property.SimpleStringProperty(state);
        });
        colAssignee.setCellValueFactory(data -> {
            TOOrder order = data.getValue();
            String assignee = order.getOrderAssignee() != null
                    ? order.getOrderAssignee().getName()
                    : "N/A";
            return new javafx.beans.property.SimpleStringProperty(assignee);
        });
    }

    private void loadOrders() {
        allOrders = FXCollections.observableArrayList(
                FashionStoreManagementController
                        .getFashionStoreManagement()
                        .getOrders()
                        .stream()
                        .map(TOMapper::toTOOrder)
                        .toList()
        );

        int pageCount = (int) Math.ceil((double) allOrders.size() / 8); // rows per page
        pagination.setPageCount(pageCount == 0 ? 1 : pageCount);
        pagination.setCurrentPageIndex(0);
        showPage(0);

        orderTable.refresh();
    }

    void showPage(int pageIndex) {
        int from = pageIndex * 8; // rows per page
        int to   = Math.min(from + 8, allOrders.size()); // rows per page
        orderTable.setItems(FXCollections.observableArrayList(
                allOrders.subList(from, to)));
    }
}