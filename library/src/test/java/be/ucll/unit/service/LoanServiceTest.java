package be.ucll.unit.service;

import be.ucll.model.*;
import be.ucll.repository.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

class LoanServiceTest {

    private UserRepository        userRepo;
    private PublicationRepository pubRepo;
    private LoanRepository        loanRepo;
    private LoanService           service;

    private User user;
    private Book book;

    @BeforeEach
    void setUp() {
        userRepo = mock(UserRepository.class);
        pubRepo  = mock(PublicationRepository.class);
        loanRepo = mock(LoanRepository.class);

        service  = new LoanService(loanRepo, pubRepo, userRepo);

        user = new User("John",25,"john@ucll.be","john123456");
        book = new Book("Design Patterns",1994,1,"GoF","9780201633610");
    }

    /* --------------- lend ---------------- */

    @Test
    void lend_reducesStockAndSavesLoan() {
        when(userRepo.findByEmailIgnoreCase("john@ucll.be"))
                .thenReturn(Optional.of(user));
        when(pubRepo.findById(1L)).thenReturn(Optional.of(book));
        when(loanRepo.save(any())).thenAnswer(inv -> inv.getArgument(0));

        Loan loan = service.lend("john@ucll.be",1);

        verify(pubRepo).save(book);
        assertThat(book.getNumberOfCopies()).isZero();
        assertThat(loan.getPublication()).isSameAs(book);
    }

    /* --------------- return -------------- */

    @Test
    void returnLoan_withMembershipFreeLoan_priceIsZero() {
        Membership m = new Membership(
                java.time.LocalDate.now().minusDays(1),
                java.time.LocalDate.now().plusDays(10),
                3);
        // wire up user<->membership
        user.setMembership(m);

        Loan loan = new Loan(user,book);

        when(loanRepo.findById(1L)).thenReturn(Optional.of(loan));

        Loan returned = service.returnLoan(1);

        assertThat(returned.getPrice()).isZero();
    }
}
