package ca.mcgill.ecse.fashionstoremanagement.javafx.fxml.controllers;

import ca.mcgill.ecse.fashionstoremanagement.controller.*;
import ca.mcgill.ecse.fashionstoremanagement.javafx.fxml.FashionStoreFxmlView;
import ca.mcgill.ecse.fashionstoremanagement.model.User;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import javafx.collections.ObservableList;

public class UserPageController {
//    View
    @FXML private TableView<TOUser> userTable;
    @FXML private TableColumn<TOUser, String>  colUsername;
    @FXML private TableColumn<TOUser, String>  colName;
    @FXML private TableColumn<TOUser, String>  colNumber;
    @FXML private TableColumn<TOUser, String>  colRole;
    @FXML private Pagination                  pagination;
    private ObservableList<TOUser> allUsers;

//    Employee
    @FXML private TextField usernameField;

//    Customer
    @FXML private TextField usernameCField;
    @FXML private PasswordField passwordField;
    @FXML private TextField addressField;

//    Users
    @FXML private TextField usernameUField;
    @FXML private TextField nameField;
    @FXML private TextField phoneField;
    @FXML private TextField addressUField;
    @FXML private Label statusLabel;

    @FXML
    public void initialize() {
        initializeViewPage();
    }

//    Customer actions
    @FXML
    public void registerCustomerClicked() {
        try {
            UserController.registerNewCustomer(
                    usernameCField.getText(),
                    passwordField.getText(),
                    addressField.getText());
            statusLabel.setText("Customer registered successfully!");
            statusLabel.setStyle("-fx-text-fill: green;");
            FashionStoreFxmlView.getInstance().refresh();
        } catch (FashionStoreException e) {
            ViewUtils.showError(e.getMessage());
        }
    }

    @FXML
    public void updateAddressClicked() {
        try {
            UserController.updateAddress(usernameUField.getText(), addressUField.getText());
            statusLabel.setText("Address updated successfully!");
            statusLabel.setStyle("-fx-text-fill: green;");
            FashionStoreFxmlView.getInstance().refresh();
        } catch (FashionStoreException e) {
            ViewUtils.showError(e.getMessage());
        }
    }

    @FXML
    public void deleteCustomerClicked() {
        try {
            UserController.deleteCustomer(usernameUField.getText());
            statusLabel.setText("Customer deleted successfully!");
            statusLabel.setStyle("-fx-text-fill: green;");
            FashionStoreFxmlView.getInstance().refresh();
        } catch (FashionStoreException e) {
            ViewUtils.showError(e.getMessage());
        }
    }


//    Employee actions
    @FXML
    public void registerEmployeeClicked() {
        try {
            UserController.registerNewEmployee(usernameField.getText());
            statusLabel.setText("Employee registered successfully!");
            statusLabel.setStyle("-fx-text-fill: green;");
            FashionStoreFxmlView.getInstance().refresh();
        } catch (FashionStoreException e) {
            ViewUtils.showError(e.getMessage());
        }
    }

    @FXML
    public void deleteEmployeeClicked() {
        try {
            UserController.deleteEmployee(usernameUField.getText());
            statusLabel.setText("Employee deleted successfully!");
            statusLabel.setStyle("-fx-text-fill: green;");
            FashionStoreFxmlView.getInstance().refresh();
        } catch (FashionStoreException e) {
            ViewUtils.showError(e.getMessage());
        }
    }

//    General actions
    @FXML
    public void updateNameClicked() {
        try {
            UserController.updateName(usernameUField.getText(), nameField.getText());
            statusLabel.setText("Name updated successfully!");
            statusLabel.setStyle("-fx-text-fill: green;");
            FashionStoreFxmlView.getInstance().refresh();
        } catch (FashionStoreException e) {
            ViewUtils.showError(e.getMessage());
        }
    }

    @FXML
    public void updatePhoneClicked() {
        try {
            UserController.updatePhoneNumber(usernameUField.getText(), phoneField.getText());
            statusLabel.setText("Phone updated successfully!");
            statusLabel.setStyle("-fx-text-fill: green;");
            FashionStoreFxmlView.getInstance().refresh();
        } catch (FashionStoreException e) {
            ViewUtils.showError(e.getMessage());
        }
    }

//    View functions
    public void initializeViewPage() {
        userTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        allUsers = FXCollections.observableArrayList();

//        columns
        colUsername.setCellValueFactory(new PropertyValueFactory<>("username"));
        colName.setCellValueFactory(data -> {
            TOUser user = data.getValue();

            String username = user.getName() != null
                    ? user.getName()
                    : "N/A";
            return new javafx.beans.property.SimpleStringProperty(username);
        });
        colNumber.setCellValueFactory(data -> {
            TOUser user = data.getValue();
            String number = user.getName() != null
                    ? user.getPhoneNumber()
                    : "N/A";
            return new javafx.beans.property.SimpleStringProperty(number);
        });
        colRole.setCellValueFactory(data -> {
            String username = data.getValue().getUsername();

            try {
                boolean isCustomer =
                        FashionStoreManagementController.getFashionStoreManagement().getCustomers().contains(username);

                boolean isEmployee =
                        FashionStoreManagementController.getFashionStoreManagement().getEmployees().contains(username);

                StringBuilder roles = new StringBuilder();

                if (isCustomer) roles.append("Customer");
                if (isEmployee) {
                    if (!roles.isEmpty()) roles.append(", ");
                    roles.append("Employee");
                }

                return new javafx.beans.property.SimpleStringProperty(
                        roles.length() > 0 ? roles.toString() : "N/A"
                );

            } catch (Exception e) {
                return new javafx.beans.property.SimpleStringProperty("N/A");
            }
        });

        loadUsers();

//        set up pagination
        int pageCount = (int) Math.ceil((double) allUsers.size() / 8); //rows per page
        pagination.setPageCount(pageCount == 0 ? 1 : pageCount);
        pagination.setCurrentPageIndex(0);

        // Show the correct slice of users whenever the page changes
        pagination.currentPageIndexProperty().addListener((obs, oldIndex, newIndex) ->
                showPage(newIndex.intValue()));

        showPage(0); // show first page immediately

        FashionStoreFxmlView.getInstance().registerRefreshEvent(userTable);
        userTable.addEventHandler(FashionStoreFxmlView.REFRESH_EVENT, e -> loadUsers());
    }

    private void loadUsers() {
        allUsers = FXCollections.observableArrayList(
                FashionStoreManagementController
                        .getFashionStoreManagement()
                        .getUsers()
                        .stream()
                        .map(TOMapper::toTOUser)
                        .toList()
        );

        int pageCount = (int) Math.ceil((double) allUsers.size() / 8);
        pagination.setPageCount(pageCount == 0 ? 1 : pageCount);
        pagination.setCurrentPageIndex(0);
        showPage(0);

        userTable.refresh();
    }

    private void showPage(int pageIndex) {
        int from = pageIndex * 8; // rows per page
        int to   = Math.min(from + 8, allUsers.size()); // rows per page
        userTable.setItems(FXCollections.observableArrayList(
                allUsers.subList(from, to)));
    }
}