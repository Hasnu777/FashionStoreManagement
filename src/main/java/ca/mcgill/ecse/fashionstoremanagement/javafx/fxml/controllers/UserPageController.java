package ca.mcgill.ecse.fashionstoremanagement.javafx.fxml.controllers;

import ca.mcgill.ecse.fashionstoremanagement.controller.FashionStoreManagementController;
import ca.mcgill.ecse.fashionstoremanagement.javafx.fxml.FashionStoreFxmlView;
import ca.mcgill.ecse.fashionstoremanagement.model.User;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import javafx.collections.ObservableList;
import javafx.scene.control.Pagination;

import java.util.List;

public class UserPageController {

    @FXML private TableView<User> userTable;
    @FXML private TableColumn<User, String>  colUsername;
    @FXML private TableColumn<User, String>  colName;
    @FXML private TableColumn<User, String>  colNumber;
    @FXML private TableColumn<User, String>  colRole;
    @FXML private Pagination                  pagination;

    private ObservableList<User> allUsers;

    @FXML
    public void initialize() {
        userTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        allUsers = FXCollections.observableArrayList();

//        columns
        colUsername.setCellValueFactory(new PropertyValueFactory<>("username"));
        colName.setCellValueFactory(data -> {
            User user = data.getValue();

            String username = user.getName() != null
                    ? user.getName()
                    : "N/A";
            return new javafx.beans.property.SimpleStringProperty(username);
        });
        colNumber.setCellValueFactory(data -> {
            User user = data.getValue();
            String number = user.getName() != null
                    ? user.getPhoneNumber()
                    : "N/A";
            return new javafx.beans.property.SimpleStringProperty(number);
        });
        colRole.setCellValueFactory(data -> {
            User user = data.getValue();
            if (user.getRoles() == null || user.getRoles().isEmpty()) {
                return new javafx.beans.property.SimpleStringProperty("N/A");
            }
            String roles = user.getRoles().stream()
                    .map(role -> role.getClass().getSimpleName()) // gets "Customer", "Employee", "Manager" etc.
                    .collect(java.util.stream.Collectors.joining(", "));
            return new javafx.beans.property.SimpleStringProperty(roles);
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
        List<User> users = FashionStoreManagementController.getFashionStoreManagement().getUsers();
        allUsers = FXCollections.observableArrayList(users != null ? users : List.of());

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