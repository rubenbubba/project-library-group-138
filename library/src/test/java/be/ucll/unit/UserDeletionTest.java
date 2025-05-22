package be.ucll.unit;

import be.ucll.repository.*;
import be.ucll.service.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class UserDeletionTest {

    @Test
    void deleteUser_removesUserAndTheirLoans() {
        UserRepository  ur = new UserRepository();
        LoanRepository  lr = new LoanRepository();
        UserService  us = new UserService(ur);
        LoanService  ls = new LoanService(lr);

        int loansBefore = lr.findByUserEmail("alice@ucll.be").size();
        Assertions.assertTrue(loansBefore > 0);

        // controller logic: delete loans first then user
        ls.deleteLoansOfUser("alice@ucll.be");
        us.deleteUser("alice@ucll.be");

        Assertions.assertTrue(ur.findByEmail("alice@ucll.be").isEmpty());
        Assertions.assertEquals(0, lr.findByUserEmail("alice@ucll.be").size());
    }

    @Test
    void deleteUser_nonExisting_throws() {
        Assertions.assertThrows(RuntimeException.class,
                () -> new UserService(new UserRepository()).deleteUser("ghost@ucll.be"));
    }
}
