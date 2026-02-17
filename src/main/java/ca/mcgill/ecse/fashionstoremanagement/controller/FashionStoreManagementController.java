package ca.mcgill.ecse.fashionstoremanagement.controller;

import ca.mcgill.ecse.fashionstoremanagement.model.FashionStoreManagement;
import ca.mcgill.ecse.fashionstoremanagement.model.Manager;
import ca.mcgill.ecse.fashionstoremanagement.model.User;

public class FashionStoreManagementController {

    private static FashionStoreManagement system;

    public static FashionStoreManagement getFashionStoreManagement() {
        if (system == null) {
            system = new FashionStoreManagement();
            User managerUser = new User("manager", "manager", "", "", system);
            new Manager(managerUser, system);
        }
        return system;
    }

    public static void resetSystem() {
        if (system != null) {
            system.delete();
            system = null;
        }
    }
}
