package be.ucll.unit;

import be.ucll.repository.UserRepository;
import be.ucll.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class UserServiceTest {

    private UserService svc() { return new UserService(new UserRepository()); }

    @Test
    void getAdults_only18Plus() {
        Assertions.assertTrue(svc().getAdults().stream().allMatch(u -> u.getAge() >= 18));
    }

    @Test
    void getUsersInRange_boundsValid() {
        Assertions.assertFalse(svc().getUsersInAgeRange(16, 25).isEmpty());
    }

    /* ---------- Story 10 happy path ---------- */
    @Test
    void getUsersByName_partialMatch_caseInsensitive() {
        var list = svc().getUsersByName("ali");   // matches "Alice"
        Assertions.assertEquals(1, list.size());
        Assertions.assertEquals("Alice", list.get(0).getName());
    }

    /* ---------- Story 10 empty param returns all ---------- */
    @Test
    void getUsersByName_blank_returnsAll() {
        int total = svc().getAllUsers().size();
        Assertions.assertEquals(total, svc().getUsersByName("").size());
    }
}
