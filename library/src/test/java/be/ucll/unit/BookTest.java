package be.ucll.unit;

import be.ucll.model.Book;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.*;

class BookTest {

    private static final Validator validator =
            Validation.buildDefaultValidatorFactory().getValidator();

    @Test
    void validBook_noViolations() {
        Book b = new Book("Author","9780545010221","HP",2007,3);
        Assertions.assertTrue(validator.validate(b).isEmpty());
    }

    @Test
    void shortIsbn_violation() {
        Book b = new Book("Author","123","HP",2007,3);
        Assertions.assertFalse(validator.validate(b).isEmpty());
    }
}
