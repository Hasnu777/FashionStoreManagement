package ca.mcgill.ecse.fashionstoremanagement.feature;

import io.cucumber.java.PendingException;
import io.cucumber.java.en.Then;

import static org.junit.jupiter.api.Assertions.*;

public class ErrorStepDefinitions extends StepDefinitions {
    @Then("the system shall not raise any errors")
    public void the_system_shall_not_raise_any_errors() {
        assertNull(error, "no errors should be raised");
    }

    @Then("the system shall raise the error {string}")
    public void the_system_shall_raise_the_error(String message) {
        assertNotNull(error, "an error should be raised");
        assertEquals(message, error.getMessage());
    }
}
