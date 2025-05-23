package be.ucll.service;

import be.ucll.model.*;
import be.ucll.repository.LoanRepository;
import be.ucll.repository.PublicationRepository;
import be.ucll.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class LoanService {

    private static final double BASE_PRICE = 2.00;
    private static final double LATE_FEE_PER_DAY = 0.50;     // after 30-day period

    private final LoanRepository        repo;
    private final UserRepository        users;
    private final PublicationRepository pubs;

    public LoanService(LoanRepository repo,
                       UserRepository users,
                       PublicationRepository pubs) {
        this.repo  = repo;
        this.users = users;
        this.pubs  = pubs;
    }

    /* ---------------- create ---------------- */

    @Transactional
    public Loan lend(String email, Long pubId) {

        User user = users.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Publication pub = pubs.findById(pubId)
                .orElseThrow(() -> new RuntimeException("Publication not found"));

        if (pub.getNumberOfCopies() <= 0)
            throw new RuntimeException("No stock left");

        double price = BASE_PRICE;
        if (user.getMembership() != null &&
                user.getMembership().isActive(LocalDate.now())) {

            int activeLoans = repo.countByUserAndReturnDateIsNull(user);
            if (activeLoans < 2) price = 0.0;          // first two concurrent loans free
        }

        pub.changeStock(-1);
        Loan loan = new Loan(LocalDate.now(), pub, user, price);
        return repo.save(loan);
    }

    /* ---------------- return ---------------- */

    @Transactional
    public Loan returnLoan(Long loanId) {
        Loan loan = repo.findById(loanId)
                .orElseThrow(() -> new RuntimeException("Loan not found"));
        if (loan.getReturnDate() != null) return loan;        // already closed

        loan.setReturnDate(LocalDate.now());
        loan.getPublication().changeStock(+1);

        long days = ChronoUnit.DAYS.between(loan.getLoanDate(), loan.getReturnDate());
        if (days > 30) {
            double extra = (days - 30) * LATE_FEE_PER_DAY;
            loan.setPrice(loan.getPrice() + extra);
        }
        return loan;
    }

    /* ---------------- queries ---------------- */

    public List<Loan> getLoansForUser(String email) {
        User user = users.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return repo.findByUser(user);
    }
}
