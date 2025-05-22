package be.ucll.repository;

import be.ucll.model.Loan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LoanRepository extends JpaRepository<Loan,Long> {

    List<Loan> findByUser_EmailIgnoreCase(String email);
    void deleteByUser_EmailIgnoreCase(String email);
}
