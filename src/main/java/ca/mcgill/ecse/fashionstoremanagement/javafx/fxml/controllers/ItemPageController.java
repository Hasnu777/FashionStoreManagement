package ca.mcgill.ecse.fashionstoremanagement.javafx.fxml.controllers;

import ca.mcgill.ecse.fashionstoremanagement.controller.*;
import ca.mcgill.ecse.fashionstoremanagement.javafx.fxml.FashionStoreFxmlView;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;

public class ItemPageController {
//    View
    @FXML private TableView<TOSizedItem> itemTable;
    @FXML private TableColumn<TOSizedItem, String> colName;
    @FXML private TableColumn<TOSizedItem, String>  colPrice;
    @FXML private TableColumn<TOSizedItem, String>  colPoints;
    @FXML private TableColumn<TOSizedItem, String>  colSize;
    @FXML private TableColumn<TOSizedItem, String>  colQuant;
    @FXML private Pagination                  pagination;
    private ObservableList<TOSizedItem> allItems;

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
        initializeViewPage();
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

    // view functions
    public void initializeViewPage() {
        itemTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        allItems = FXCollections.observableArrayList();

//        columns
        colName.setCellValueFactory(new PropertyValueFactory<>("itemName"));
        colPrice.setCellValueFactory(data -> {
            TOSizedItem item = data.getValue();

            String items = String.valueOf(item.getItem().getPrice()) != null
                    ? String.valueOf(item.getItem().getPrice())
                    : "N/A";
            return new SimpleStringProperty(items);
        });
        colPoints.setCellValueFactory(data -> {
            TOSizedItem item = data.getValue();

            String items = String.valueOf(item.getItem().getLoyaltyPoints()) != null
                    ? String.valueOf(item.getItem().getLoyaltyPoints())
                    : "N/A";
            return new SimpleStringProperty(items);
        });
        colSize.setCellValueFactory(data -> {
            TOSizedItem item = data.getValue();

            String items = item.getSize() != null
                    ? item.getSize()
                    : "N/A";
            return new SimpleStringProperty(items);
        });
        colQuant.setCellValueFactory(data -> {
            TOSizedItem item = data.getValue();

            String items = String.valueOf(item.getQuantityInInventory()) != null
                    ? String.valueOf(item.getQuantityInInventory())
                    : "N/A";
            return new SimpleStringProperty(items);
        });

        loadItems();

        //        each column 1/5 of width
        colName.prefWidthProperty().bind(itemTable.widthProperty().divide(5));
        colSize.prefWidthProperty().bind(itemTable.widthProperty().divide(5));
        colPrice.prefWidthProperty().bind(itemTable.widthProperty().divide(5));
        colQuant.prefWidthProperty().bind(itemTable.widthProperty().divide(5));
        colPoints.prefWidthProperty().bind(itemTable.widthProperty().divide(5));


//        set up pagination
        int pageCount = (int) Math.ceil((double) allItems.size() / 8); //rows per page
        pagination.setPageCount(pageCount == 0 ? 1 : pageCount);
        pagination.setCurrentPageIndex(0);

        // Show the correct slice of users whenever the page changes
        pagination.currentPageIndexProperty().addListener((obs, oldIndex, newIndex) ->
                showPage(newIndex.intValue()));

        showPage(0); // show first page immediately

        FashionStoreFxmlView.getInstance().registerRefreshEvent(itemTable);
        itemTable.addEventHandler(FashionStoreFxmlView.REFRESH_EVENT, e -> loadItems());
    }

    private void loadItems() {
        List<TOSizedItem> items = ItemController.getAllTOSizedItem();
        allItems = FXCollections.observableArrayList(items != null ? items : List.of());

        int pageCount = (int) Math.ceil((double) allItems.size() / 8);
        pagination.setPageCount(pageCount == 0 ? 1 : pageCount);
        pagination.setCurrentPageIndex(0);
        showPage(0);

        itemTable.refresh();
    }

    private void showPage(int pageIndex) {
        int from = pageIndex * 8; // rows per page
        int to   = Math.min(from + 8, allItems.size()); // rows per page
        itemTable.setItems(FXCollections.observableArrayList(allItems.subList(from, to)));
    }
}