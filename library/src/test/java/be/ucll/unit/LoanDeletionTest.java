package be.ucll.unit;

import be.ucll.repository.LoanRepository;
import be.ucll.service.LoanService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class LoanDeletionTest {

    private LoanRepository repo() { return new LoanRepository(); }

    @Test
    void deleteLoansOfUser_removesAllTheirLoans() {
        LoanRepository r = repo();
        LoanService    s = new LoanService(r);

        int before = r.findByUserEmail("alice@ucll.be").size();
        Assertions.assertTrue(before > 0);

        s.deleteLoansOfUser("alice@ucll.be");

        int after = r.findByUserEmail("alice@ucll.be").size();
        Assertions.assertEquals(0, after);
    }

    @Test
    void deleteLoansWithNoLoans_isIdempotent() {
        LoanRepository r = repo();
        LoanService    s = new LoanService(r);

        // Bob's loans returned – delete once
        s.deleteLoansOfUser("bob@ucll.be");
        // delete again – should not throw
        s.deleteLoansOfUser("bob@ucll.be");
        Assertions.assertEquals(0, r.findByUserEmail("bob@ucll.be").size());
    }

    @Test
    void deleteLoans_nullEmail_throws() {
        Assertions.assertThrows(RuntimeException.class,
                () -> new LoanService(repo()).deleteLoansOfUser(""));
    }
}
