package be.ucll.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
public class Membership {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate start;
    private LocalDate end;
    private int freeLoans;

    public Membership() {}  // <-- This must be public

    public void setStart(LocalDate start) {
        this.start = start;
    }

    public void setEnd(LocalDate end) {
        this.end = end;
    }

    public void setFreeLoans(int freeLoans) {
        this.freeLoans = freeLoans;
    }

    // Add any needed getters
}
