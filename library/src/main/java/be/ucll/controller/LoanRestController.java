package be.ucll.controller;

import be.ucll.model.Loan;
import be.ucll.service.LoanService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping
public class LoanRestController {

    private final LoanService service;

    public LoanRestController(LoanService service) { this.service = service; }

    /* --------------- create --------------- */
    @PostMapping("/loans")
    @ResponseStatus(HttpStatus.CREATED)
    public Loan lend(@RequestParam String userEmail, @RequestParam long publicationId) {
        return service.lend(userEmail, publicationId);
    }

    /* --------------- return --------------- */
    @PutMapping("/loans/{id}/return")
    public Loan returnLoan(@PathVariable long id) {
        return service.returnLoan(id);
    }

    /* --------------- by-user -------------- */
    @GetMapping("/users/{email}/loans")
    public List<Loan> byUser(@PathVariable String email) {
        return service.loansForUser(email);
    }

    @DeleteMapping("/users/{email}/loans")
    public void deleteForUser(@PathVariable String email) {
        service.deleteLoansForUser(email);
    }
}
