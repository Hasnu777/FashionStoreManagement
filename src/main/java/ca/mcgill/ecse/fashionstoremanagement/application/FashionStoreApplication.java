package ca.mcgill.ecse.fashionstoremanagement.application;

import ca.mcgill.ecse.fashionstoremanagement.controller.FashionStoreManagementController;
import ca.mcgill.ecse.fashionstoremanagement.javafx.fxml.FashionStoreFxmlView;
import javafx.application.Application;

import ca.mcgill.ecse.fashionstoremanagement.model.FashionStoreManagement;


import java.sql.Date;
import java.time.LocalDate;

public class FashionStoreApplication{

    public static void main(String[] args) {
        FashionStoreManagementController.getFashionStoreManagement();
        Application.launch(FashionStoreFxmlView.class, args);

    }


}
