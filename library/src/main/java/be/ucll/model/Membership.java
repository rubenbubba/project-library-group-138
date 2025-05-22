package be.ucll.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
public class Membership {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate startDate;
    private LocalDate endDate;
    private int freeLoans;                 // number of free loans in the period

    /* ---------- relations ---------- */
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    /* ---------- ctor ---------- */
    protected Membership() { }            // JPA

    public Membership(LocalDate start, LocalDate end, int freeLoans) {
        this.startDate = start;
        this.endDate   = end;
        this.freeLoans = freeLoans;
    }

    /* ---------- domain helpers ---------- */
    public boolean isValidOn(LocalDate date) {
        return (date.isEqual(startDate) || date.isAfter(startDate))
                && (date.isEqual(endDate)   || date.isBefore(endDate));
    }

    public boolean hasFreeLoansLeft()       { return freeLoans > 0; }
    public void consumeFreeLoan()           { if (freeLoans > 0) freeLoans--; }

    /* getters */
    public int      getFreeLoans() { return freeLoans; }
    public LocalDate getEndDate()  { return endDate;  }
}
