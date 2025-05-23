package be.ucll.unit.model;

import org.junit.jupiter.api.Test;
import jakarta.validation.*;
import static org.junit.jupiter.api.Assertions.*;

public class UserTest {
    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    @Test
    void givenValidUser_whenValidate_thenNoViolations() {
        User user = new User("John Doe", 30, "john.doe@ucll.be", "password123");
        assertTrue(validator.validate(user).isEmpty());
    }

    @Test
    void givenEmptyName_whenValidate_thenViolation() {
        User user = new User("   ", 25, "a@b.com", "password123");
        var violations = validator.validate(user);
        assertEquals(1, violations.size());
        assertEquals("Name is required.", violations.iterator().next().getMessage());
    }

    // ... tests for email format, password length, age bounds
}
