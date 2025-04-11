package be.ucll.unit;

import be.ucll.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class UserTest {

    @Test
    void givenValidValues_whenCreatingUser_thenUserIsCreatedWithThoseValues() {
        // Valid user
        User user = new User("test", "test123456", "test@gmail.com", 32);

        // Testing getters
        String name = user.getName();
        String password = user.getPassword();
        String email = user.getEmail();
        int age = user.getAge();

        // Testing values match expected
        Assertions.assertEquals("test", name);
        Assertions.assertEquals("test123456", password);
        Assertions.assertEquals("test@gmail.com", email);
        Assertions.assertEquals(32, age);
    }

    @Test
    void givenValues_whenNameIsNull_thenThrowRuntimeException() {
        Exception exception = Assertions.assertThrows(RuntimeException.class, () -> {
            new User("", "test123456", "test@gmail.com", 32);
        });
        Assertions.assertEquals("Name is required.", exception.getMessage());
    }

    @Test
    void givenValues_whenNameIsSpaces_thenThrowRuntimeException() {
        Exception exception = Assertions.assertThrows(RuntimeException.class, () -> {
            new User("       ", "test123456", "test@gmail.com", 32);
        });
        Assertions.assertEquals("Name is required.", exception.getMessage());
    }

    @Test
    void givenValues_whenPasswordIsNull_thenThrowRuntimeException() {
        Exception exception = Assertions.assertThrows(RuntimeException.class, () -> {
            new User("test", "", "test@gmail.com", 32);
        });
        Assertions.assertEquals("Password must be at least 8 characters long.", exception.getMessage());
    }

    @Test
    void givenValues_whenPasswordIsShorterThan8Characters_thenThrowRuntimeException() {
        Exception exception = Assertions.assertThrows(RuntimeException.class, () -> {
            new User("test", "123", "test@gmail.com", 32);
        });
        Assertions.assertEquals("Password must be at least 8 characters long.", exception.getMessage());
    }

    @Test
    void givenValues_whenEmailIsNull_thenThrowRuntimeException() {
        Exception exception = Assertions.assertThrows(RuntimeException.class, () -> {
            new User("test", "test123456", "", 32);
        });
        Assertions.assertEquals("E-mail must be a valid email format.", exception.getMessage());
    }

    @Test
    void givenValues_whenEmailDoesNotContainAt_thenThrowRuntimeException() {
        Exception exception = Assertions.assertThrows(RuntimeException.class, () -> {
            new User("test", "test123456", "testgmail.com", 32);
        });
        Assertions.assertEquals("E-mail must be a valid email format.", exception.getMessage());
    }

    @Test
    void givenValues_whenAgeIsNotAValueBetween0and101_thenThrowRuntimeException() {
        Exception exception = Assertions.assertThrows(RuntimeException.class, () -> {
            new User("test", "test123456", "test@gmail.com", 252);
        });
        Assertions.assertEquals("Age must be a positive integer between 0 and 101.", exception.getMessage());
    }

}

