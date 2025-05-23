package be.ucll.unit.model;

import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;

public class LoanTest {
    @Test
    void givenUserAndBook_whenLoan_thenCorrectDatesAndStock() {
        User u = new User("J", 20, "j@u.be", "password");
        Book b = new Book("T","A","978-0-545-01022-1",2001,5);
        Loan loan = new Loan(LocalDate.of(2025,1,1), b, u, 1.5);
        assertEquals(LocalDate.of(2025,1,1).plusDays(7), loan.getEndDate());
        assertEquals(4, b.getAvailableCopies());
    }
    // ... other loan scenarios
}