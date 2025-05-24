package be.ucll.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import be.ucll.model.Loan;
import be.ucll.model.User;
import be.ucll.repository.LoanRepository;
import be.ucll.repository.UserRepository;

@Service
public class LoanService {

    private final LoanRepository loans;
    private final UserRepository users;

    public LoanService(LoanRepository loans, UserRepository users) {
        this.loans = loans;
        this.users = users;
    }

    @Transactional
    public Loan createLoan(String userEmail, Long publicationId, LocalDate dueDate) {
        User user = users.findByEmailIgnoreCase(userEmail)
                .orElseThrow(() -> new RuntimeException("No user with email: " + userEmail));

        // ... assume you validate publication existence elsewhere

        Loan loan = new Loan();
        loan.setUser(user);
        loan.setPublicationId(publicationId);
        loan.setDueDate(dueDate);
        return loans.save(loan);
    }

    public List<Loan> getLoansByUser(String userEmail) {
        User user = users.findByEmailIgnoreCase(userEmail)
                .orElseThrow(() -> new RuntimeException("No user with email: " + userEmail));
        return loans.findByUser(user);
    }

    @Transactional
    public void deleteLoansByUser(String userEmail) {
        User user = users.findByEmailIgnoreCase(userEmail)
                .orElseThrow(() -> new RuntimeException("No user with email: " + userEmail));
        loans.deleteByUser(user);
    }
}
