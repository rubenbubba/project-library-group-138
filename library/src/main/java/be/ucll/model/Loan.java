package be.ucll.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Entity
public class Loan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate borrowedDate;
    private LocalDate returnedDate;
    private double    price;          // € charged at return
    private double    fine;           // late-return fine

    /* ---------- relations ---------- */
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    private Publication publication;

    /* ---------- ctor ---------- */
    protected Loan() { }

    public Loan(User user, Publication pub) {
        this.user          = user;
        this.publication   = pub;
        this.borrowedDate  = LocalDate.now();
    }

    /* ---------- domain behaviour ---------- */
    public void registerReturn(double price, double fine) {
        if (returnedDate != null) throw new RuntimeException("Loan already returned.");
        this.returnedDate = LocalDate.now();
        this.price        = price;
        this.fine         = fine;
    }

    /* getters */
    public Long getId() { return id; }
    public LocalDate getBorrowedDate() { return borrowedDate; }
    public LocalDate getReturnedDate() { return returnedDate; }
    public double getPrice() { return price; }
    public double getFine() { return fine; }

    /* helpers */
    public long daysKept() {
        LocalDate end = (returnedDate == null ? LocalDate.now() : returnedDate);
        return ChronoUnit.DAYS.between(borrowedDate, end);
    }
}
