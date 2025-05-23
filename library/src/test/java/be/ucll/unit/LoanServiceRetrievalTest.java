package be.ucll.unit;

import be.ucll.repository.LoanRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class LoanServiceRetrievalTest {

    private LoanService svc() { return new LoanService(new LoanRepository()); }

    @Test
    void loansForUser_returnsAll() {
        var list = svc().getLoansForUser("alice@ucll.be", false);
        Assertions.assertFalse(list.isEmpty());
    }

    @Test
    void loansForUser_onlyActive_filtersReturned() {
        var all  = svc().getLoansForUser("bob@ucll.be", false);
        var act  = svc().getLoansForUser("bob@ucll.be", true);
        Assertions.assertTrue(act.size() <= all.size());
        Assertions.assertTrue(act.stream().allMatch(l -> l.isActive()));
    }

    @Test
    void nullEmail_throws() {
        Assertions.assertThrows(RuntimeException.class,
                () -> svc().getLoansForUser("", false));
    }
}
