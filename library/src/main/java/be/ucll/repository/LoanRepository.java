package be.ucll.repository;

import be.ucll.model.Loan;
import be.ucll.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LoanRepository extends JpaRepository<Loan, Long> {

    List<Loan> findByUser(User user);

    /** helper used for the free-loan calculation */
    int countByUserAndReturnDateIsNull(User user);
}
