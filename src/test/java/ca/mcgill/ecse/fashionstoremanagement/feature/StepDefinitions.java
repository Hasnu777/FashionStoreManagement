package ca.mcgill.ecse.fashionstoremanagement.feature;

import ca.mcgill.ecse.fashionstoremanagement.controller.FashionStoreException;
import ca.mcgill.ecse.fashionstoremanagement.controller.FashionStoreManagementController;
import ca.mcgill.ecse.fashionstoremanagement.model.FashionStoreManagement;

public class StepDefinitions {

    /**
     * Set this field in <code>@When</code> steps if an error was raised.
     */
    static FashionStoreException error;

    protected void before() {
        FashionStoreManagementController.resetSystem();
        error = null;
    }

    protected FashionStoreManagement getSystem() {
        return FashionStoreManagementController.getFashionStoreManagement();
    }
}
