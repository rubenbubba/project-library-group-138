package be.ucll.service;

import be.ucll.model.*;
import be.ucll.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class LoanService {

    private final LoanRepository loanRepo;
    private final PublicationRepository pubRepo;
    private final UserRepository userRepo;

    public LoanService(LoanRepository loanRepo,
                       PublicationRepository pubRepo,
                       UserRepository userRepo) {
        this.loanRepo = loanRepo;
        this.pubRepo  = pubRepo;
        this.userRepo = userRepo;
    }

    /* -----------------------------------------------------  create  */
    @Transactional
    public Loan lend(String userEmail, long publicationId) {

        User        user = userRepo.findByEmailIgnoreCase(userEmail)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Publication pub  = pubRepo.findById(publicationId)
                .orElseThrow(() -> new RuntimeException("Publication not found"));

        if (pub.getNumberOfCopies() <= 0)
            throw new RuntimeException("No copies available");

        pub.changeStock(-1);                      // ↓ copies
        Loan loan = loanRepo.save(new Loan(user, pub));
        return loan;
    }

    /* -----------------------------------------------------  return */
    @Transactional
    public Loan returnLoan(long loanId) {
        Loan loan = loanRepo.findById(loanId)
                .orElseThrow(() -> new RuntimeException("Loan not found"));
        if (loan.getReturnedDate() != null)
            throw new RuntimeException("Already returned");

        Publication pub  = loan.getPublication();
        User        user = loan.getUser();

        // ------- price calculation ----------
        double price = 2.0;          // default price

        Membership m = user.getMembership();
        if (m != null && m.isValidOn(LocalDate.now()) && m.hasFreeLoansLeft()) {
            price = 0.0;
            m.consumeFreeLoan();
        }

        // ------- fine calculation -----------
        long overdue = loan.daysKept() - 14;
        double fine  = overdue > 0 ? overdue * 0.5 : 0.0;

        loan.registerReturn(price, fine);
        pub.changeStock(+1);         // put copy back

        return loan;
    }

    /* -----------------------------------------------------  queries */
    public List<Loan> loansForUser(String email) {
        return loanRepo.findByUser_EmailIgnoreCase(email);
    }

    public void deleteLoansForUser(String email) {
        loanRepo.deleteByUser_EmailIgnoreCase(email);
    }
}
