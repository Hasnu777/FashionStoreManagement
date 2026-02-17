package ca.mcgill.ecse.fashionstoremanagement.controller;

import ca.mcgill.ecse.fashionstoremanagement.model.*;

import java.util.regex.Pattern;

public class UserController {
    private static final Pattern PHONE_NUMBER_PATTERN = Pattern.compile("\\(\\d{3,3}\\) \\d{3,3}-\\d{4,4}");
    private static final String PHONE_NUMBER_FORMAT_EXPLANATION = "(xxx) xxx-xxxx";

    /**
     * Create a customer account for someone who does not have any accounts yet.
     */
    public static void registerNewCustomer(String username, String password, String address) {
        validateUsername(username);
        validatePassword(password);
        validateAddress(address);

        FashionStoreManagement sys = FashionStoreManagementController.getFashionStoreManagement();
        User u = new User(username, password, "", "", sys);
        new Customer(u, address, 0, sys);
    }

    /**
     * Create an employee account for someone who does not have any accounts yet.
     */
    public static void registerNewEmployee(String username) {
        validateUsername(username);

        FashionStoreManagement sys = FashionStoreManagementController.getFashionStoreManagement();
        User u = new User(username, "employee", "", "", sys);
        new Employee(u, sys);
    }

    /**
     * Create a customer account for someone who is already a user.
     */
    public static void addCustomerAccount(String username, String address) {
        validateAddress(address);

        FashionStoreManagement sys = FashionStoreManagementController.getFashionStoreManagement();
        User u = findUser(username);
        if (isCustomer(u)) {
            throw new FashionStoreException(
                    String.format("\"%s\" already has a customer account", username));
        }
        new Customer(u, address, 0, sys);
    }

    /**
     * Create an employee account for someone who is already a user.
     */
    public static void addEmployeeAccount(String username) {
        FashionStoreManagement sys = FashionStoreManagementController.getFashionStoreManagement();
        User u = findUser(username);
        if (isEmployee(u)) {
            throw new FashionStoreException(
                    String.format("\"%s\" already has an employee account", username));
        }
        new Employee(u, sys);
    }

    /**
     * Update the given user's password.
     */
    public static void updatePassword(String username, String newPassword) {
        validatePassword(newPassword);
        User u = findUser(username);
        u.setPassword(newPassword);
    }

    /**
     * Update the given user's address.
     */
    public static void updateAddress(String username, String address) {
        validateAddress(address);
        Customer c = findCustomer(username);
        c.setAddress(address);
    }

    /**
     * Update the given user's name.
     */
    public static void updateName(String username, String name) {
        name = validateAndCleanName(name);
        User u = findUser(username);
        u.setName(name);
    }

    /**
     * Update the given user's phone number.
     */
    public static void updatePhoneNumber(String username, String phoneNumber) {
        phoneNumber = validateAndCleanPhoneNumber(phoneNumber);
        User u = findUser(username);
        u.setPhoneNumber(phoneNumber);
    }

    /**
     * Delete the given user's customer account. If they have no other accounts, delete the user altogether.
     */
    public static void deleteCustomer(String username) {
        Customer c = findCustomer(username);
        User u = c.getUser();
        c.delete();
        if (u.getRoles().isEmpty()) {
            u.delete();
        }
    }

    /**
     * Delete the given user's employee account. If they have no other accounts, delete the user altogether.
     */
    public static void deleteEmployee(String username) {
        Employee e = findEmployee(username);
        User u = e.getUser();
        e.delete();
        if (u.getRoles().isEmpty()) {
            u.delete();
        }
    }

    private static User findUser(String username) {
        User u = User.getWithUsername(username);
        if (u == null) {
            throw new FashionStoreException(
                    String.format("there is no user with username \"%s\"", username));
        }
        return u;
    }

    private static Customer findCustomer(String username) {
        User u = findUser(username);
        for (UserRole r : u.getRoles()) {
            if (r instanceof Customer) {
                return (Customer) r;
            }
        }
        throw new FashionStoreException(
                String.format("\"%s\" is not a customer", username));
    }

    private static Employee findEmployee(String username) {
        User u = findUser(username);
        for (UserRole r : u.getRoles()) {
            if (r instanceof Employee) {
                return (Employee) r;
            }
        }
        throw new FashionStoreException(
                String.format("\"%s\" is not an employee", username));
    }

    private static boolean isCustomer(User u) {
        for (UserRole r : u.getRoles()) {
            if (r instanceof Customer) {
                return true;
            }
        }
        return false;
    }

    private static boolean isEmployee(User u) {
        for (UserRole r : u.getRoles()) {
            if (r instanceof Employee) {
                return true;
            }
        }
        return false;
    }

    private static void validateUsername(String username) {
        if (username == null || username.isBlank()) {
            throw new FashionStoreException("username is required");
        }
        if (User.hasWithUsername(username)) {
            throw new FashionStoreException("username is already in use");
        }
        for (Character c : username.toCharArray()) {
            boolean isValid =
                    ('a' <= c && c <= 'z')
                            || ('A' <= c && c <= 'Z')
                            || ('0' <= c && c <= '9');
            if (!isValid) {
                throw new FashionStoreException(
                        String.format("username contains an invalid character '%c'", c));
            }
        }
    }

    private static void validatePassword(String password) {
        if (password == null || password.isBlank()) {
            throw new FashionStoreException("password is required");
        }
    }

    private static void validateAddress(String address) {
        if (address == null || address.isBlank()) {
            throw new FashionStoreException("address is required");
        }
    }

    private static String validateAndCleanName(String name) {
        if (name == null) {
            return "";
        }
        return name.strip();
    }

    private static String validateAndCleanPhoneNumber(String phoneNumber) {
        if (phoneNumber == null || phoneNumber.isBlank()) {
            return "";
        }
        phoneNumber = phoneNumber.strip();
        if (!PHONE_NUMBER_PATTERN.matcher(phoneNumber).matches()) {
            throw new FashionStoreException(
                    String.format("phone number does not match expected format \"%s\"", PHONE_NUMBER_FORMAT_EXPLANATION));
        }
        return phoneNumber;
    }
}
