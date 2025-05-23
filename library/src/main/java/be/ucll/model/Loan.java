package be.ucll.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "loan")
public class Loan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate loanDate;
    private LocalDate returnDate;
    private double    price;

    /* relations */
    @ManyToOne(optional = false)
    private Publication publication;

    @ManyToOne(optional = false)
    private User user;

    /* ---------- ctors ---------- */
    protected Loan() { }

    public Loan(LocalDate loanDate, Publication pub, User user, double price) {
        this.loanDate   = loanDate;
        this.publication = pub;
        this.user        = user;
        this.price       = price;
    }

    /* ---------- getters/setters ---------- */
    public Long       getId()         { return id; }
    public LocalDate  getLoanDate()   { return loanDate; }
    public LocalDate  getReturnDate() { return returnDate; }
    public double     getPrice()      { return price; }
    public Publication getPublication(){ return publication; }
    public User        getUser()      { return user; }

    public void setReturnDate(LocalDate d) { this.returnDate = d; }
    public void setPrice(double p)         { this.price      = p; }
}
