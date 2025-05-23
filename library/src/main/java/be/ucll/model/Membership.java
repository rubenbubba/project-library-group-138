package be.ucll.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
public class Membership {

    /* ------------------------------------------------------------------ */
    /* fields                                                             */
    /* ------------------------------------------------------------------ */

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private LocalDate start;

    @NotNull
    private LocalDate end;

    @Min(0)
    private int freeLoans;

    @OneToOne(mappedBy = "membership")
    private User user;                       // inverse side

    /* ------------------------------------------------------------------ */
    /* constructors                                                       */
    /* ------------------------------------------------------------------ */

    protected Membership() { }               // JPA

    public Membership(LocalDate start, LocalDate end) {
        this(start, end, 0);
    }

    public Membership(LocalDate start, LocalDate end, int freeLoans) {
        if (end.isBefore(start))
            throw new IllegalArgumentException("End date must be after start");
        this.start     = start;
        this.end       = end;
        this.freeLoans = freeLoans;
    }

    /* ------------------------------------------------------------------ */
    /* getters / setters                                                  */
    /* ------------------------------------------------------------------ */

    public Long       getId()        { return id; }
    public LocalDate  getStart()     { return start; }
    public LocalDate  getEnd()       { return end; }
    public int        getFreeLoans() { return freeLoans; }
    public User       getUser()      { return user; }

    public void setStart(LocalDate start) {
        if (end != null && start.isAfter(end))
            throw new IllegalArgumentException("Start must be before end");
        this.start = start;
    }

    public void setEnd(LocalDate end) {
        if (start != null && end.isBefore(start))
            throw new IllegalArgumentException("End must be after start");
        this.end = end;
    }

    public void setFreeLoans(int freeLoans) {
        if (freeLoans < 0) throw new IllegalArgumentException("freeLoans ≥ 0");
        this.freeLoans = freeLoans;
    }

    /** Hook used by MembershipService / Initializer to set the owning user. */
    public void setUser(User user) {
        this.user = user;
    }

    /* ------------------------------------------------------------------ */
    /* domain helpers                                                     */
    /* ------------------------------------------------------------------ */

    /** True when the membership is valid on the given date. */
    public boolean isActive(LocalDate date) {
        return (date.isAfter(start) || date.isEqual(start))
                && (date.isBefore(end)  || date.isEqual(end));
    }
}
