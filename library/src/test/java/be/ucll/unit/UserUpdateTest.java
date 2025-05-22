package be.ucll.unit;

import be.ucll.model.User;
import be.ucll.repository.UserRepository;
import be.ucll.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class UserUpdateTest {

    private UserService svc() { return new UserService(new UserRepository()); }

    @Test
    void updateUser_changesNamePasswordAge() {
        User changes = new User("Alice Updated","newpass123","alice@ucll.be",28);
        User updated = svc().updateUser("alice@ucll.be", changes);

        Assertions.assertEquals("Alice Updated", updated.getName());
        Assertions.assertEquals("newpass123",    updated.getPassword());
        Assertions.assertEquals(28,              updated.getAge());
    }

    @Test
    void updateUser_emailChangeNotAllowed() {
        User changes = new User("Alice","password123","new@ucll.be",30);
        Assertions.assertThrows(RuntimeException.class,
                () -> svc().updateUser("alice@ucll.be", changes));
    }

    @Test
    void updateUser_nonExistingEmail_throws() {
        User changes = new User("Ghost","pass1234","ghost@ucll.be",40);
        Assertions.assertThrows(RuntimeException.class,
                () -> svc().updateUser("ghost@ucll.be", changes));
    }
}
