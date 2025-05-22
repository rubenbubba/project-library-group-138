package be.ucll.unit;

import be.ucll.model.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

class LoanTest {

    /* helper */
    private Book bookWithCopies(int copies) {
        return new Book("George Orwell", "9780451524935", "1984", 1949, copies);
    }
    private User alice() {
        return new User("Alice", "password123", "alice@ucll.be", 25);
    }

    @Test
    void loanLendsCopies_andAutoEndDate() {
        Book b = bookWithCopies(2);
        LocalDate start = LocalDate.now();
        Loan loan = new Loan(alice(), List.of(b), start);

        Assertions.assertEquals(start.plusDays(21), loan.getEndDate());
        Assertions.assertEquals(1, b.getNumberOfCopies());
    }

    @Test
    void loanWithNoCopies_rollsBackAndThrows() {
        Book bOk  = bookWithCopies(2);
        Book b0   = bookWithCopies(1);
        b0.lendPublication();                 // now 0 copies

        RuntimeException ex = Assertions.assertThrows(RuntimeException.class,
                () -> new Loan(alice(), List.of(bOk, b0), LocalDate.now()));

        Assertions.assertEquals("No copies available.", ex.getMessage());
        Assertions.assertEquals(2, bOk.getNumberOfCopies());  // rolled back
    }
}
