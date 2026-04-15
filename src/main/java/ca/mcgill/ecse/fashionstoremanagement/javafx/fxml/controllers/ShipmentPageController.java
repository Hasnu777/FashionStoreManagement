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
import java.util.stream.Collectors;

public class ShipmentPageController {
//    View
    @FXML private TableView<TOShipment> shipmentTable;
    @FXML private TableColumn<TOShipment, String> colNumber;
    @FXML private TableColumn<TOShipment, String>  colDateOrdered;
    @FXML private TableColumn<TOShipment, String>  colDateArrived;
    @FXML private TableColumn<TOShipment, String> colItems;
    @FXML private Pagination                  pagination;
    private ObservableList<TOShipment> allShipments;

//    management
    @FXML private TextField shipmentNumberField;
    @FXML private Label statusLabel;
    @FXML private ChoiceBox<String> sizeField;
    @FXML private TextField shipmentNumberUField;
    @FXML private TextField itemNameField;
    @FXML private TextField quantityField;

    /**
     * This method initializes the frontend page
     */
    @FXML
    public void initialize() {
        sizeField.getItems().addAll("XS", "S", "M", "L", "XL");
        initializeViewPage();
    }

    /**
     * This method creates a new shipment
     */
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

    /**
     * This method deletes the selected shipment
     */
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



    /**
     * This method adds an item to the selected shipment
     */
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

    /**
     * This removes the selected shipment
     */
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

    /**
     * This method initializes the table view
     */
    public void initializeViewPage() {
        shipmentTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        allShipments = FXCollections.observableArrayList();

        //columns
        colNumber.setCellValueFactory(data -> {
            TOShipment shipment = data.getValue();
            return new SimpleStringProperty(String.valueOf(shipment.getShipmentNumber()));
        });

        colItems.setCellValueFactory(data -> {
            TOShipment shipment = data.getValue();
            List<TOShipmentItem> items = ShipmentController.getTOShipmentItems(shipment.getShipmentNumber());

            if (items == null || items.isEmpty()) {
                return new SimpleStringProperty("No items");
            }

            String itemSummary = items.stream()
                    .map(i -> i.getItemName() + " (" + i.getSize() + ") x" + i.getQuantity())
                    .collect(Collectors.joining(", "));

            return new SimpleStringProperty(itemSummary);
        });
        colDateArrived.setCellValueFactory(data -> {
            TOShipment shipment = data.getValue();

            String items = shipment.getDateArrived() != null
                    ? shipment.getDateArrived().toString()
                    : "N/A";
            return new SimpleStringProperty(items);
        });
        colDateOrdered.setCellValueFactory(data -> {
            TOShipment shipment = data.getValue();

            String items = shipment.getDateOrdered() != null
                    ? shipment.getDateOrdered().toString()
                    : "N/A";
            return new SimpleStringProperty(items);
        });

        loadShipments();

        //each column 1/3 of width
        colNumber.prefWidthProperty().bind(shipmentTable.widthProperty().divide(4));
        colDateOrdered.prefWidthProperty().bind(shipmentTable.widthProperty().divide(4));
        colDateArrived.prefWidthProperty().bind(shipmentTable.widthProperty().divide(4));
        colItems.prefWidthProperty().bind(shipmentTable.widthProperty().divide(4));

        //set up pagination
        int pageCount = (int) Math.ceil((double) allShipments.size() / 8); //rows per page
        pagination.setPageCount(pageCount == 0 ? 1 : pageCount);
        pagination.setCurrentPageIndex(0);

        // Show the correct slice of users whenever the page changes
        pagination.currentPageIndexProperty().addListener((obs, oldIndex, newIndex) ->
                showPage(newIndex.intValue()));

        showPage(0); // show first page immediately

        FashionStoreFxmlView.getInstance().registerRefreshEvent(shipmentTable);
        shipmentTable.addEventHandler(FashionStoreFxmlView.REFRESH_EVENT, e -> loadShipments());
    }

    /**
     * This helper method loads the current existing shipments
     */
    private void loadShipments() {
        List<TOShipment> shipments = ShipmentController.getAllTOShipments();
        allShipments = FXCollections.observableArrayList(shipments != null ? shipments : List.of());

        int pageCount = (int) Math.ceil((double) allShipments.size() / 8);
        pagination.setPageCount(pageCount == 0 ? 1 : pageCount);
        pagination.setCurrentPageIndex(0);
        showPage(0);

        shipmentTable.refresh();
    }

    /**
     * This helper method shows the shipments across several pages
     */
    private void showPage(int pageIndex) {
        int from = pageIndex * 8; // rows per page
        int to   = Math.min(from + 8, allShipments.size()); // rows per page
        shipmentTable.setItems(FXCollections.observableArrayList(
                allShipments.subList(from, to))
        );
    }
}
