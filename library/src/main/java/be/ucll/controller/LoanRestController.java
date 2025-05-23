package be.ucll.controller;

import be.ucll.model.Loan;
import be.ucll.service.LoanService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/loans")
public class LoanRestController {

    private final LoanService loans;

    public LoanRestController(LoanService loans) {
        this.loans = loans;
    }

    /* POST  /loans            body={ "email":"a@b.com", "publicationId": 1 } */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Loan lend(@Valid @RequestBody Map<String, String> body) {
        return loans.lend(body.get("email"), Long.parseLong(body.get("publicationId")));
    }

    /* PUT   /loans/{id}/return  */
    @PutMapping("/{id}/return")
    public Loan close(@PathVariable Long id) {
        return loans.returnLoan(id);
    }

    /* GET   /loans/user/{email} */
    @GetMapping("/user/{email}")
    public java.util.List<Loan> byUser(@PathVariable String email) {
        return loans.getLoansForUser(email);
    }
}
