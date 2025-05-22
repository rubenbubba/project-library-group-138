package be.ucll.unit;

import be.ucll.model.User;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.*;

class UserTest {

    private static final Validator validator =
            Validation.buildDefaultValidatorFactory().getValidator();

    @Test
    void validUser_hasNoViolations() {
        User u = new User("John","password123","john@ucll.be",30);
        Assertions.assertTrue(validator.validate(u).isEmpty());
    }

    @Test
    void blankName_triggersViolation() {
        User u = new User("","password123","john@ucll.be",30);
        Assertions.assertFalse(validator.validate(u).isEmpty());
    }

    @Test
    void shortPassword_triggersViolation() {
        User u = new User("John","123","john@ucll.be",30);
        Assertions.assertFalse(validator.validate(u).isEmpty());
    }

    @Test
    void badEmail_triggersViolation() {
        User u = new User("John","password123","no-at",30);
        Assertions.assertFalse(validator.validate(u).isEmpty());
    }

    @Test
    void ageOutOfRange_triggersViolation() {
        User u = new User("John","password123","john@ucll.be",150);
        Assertions.assertFalse(validator.validate(u).isEmpty());
    }
}
