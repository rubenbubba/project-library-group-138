package be.ucll.model;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class Loan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long publicationId;
    private LocalDate dueDate;

    @ManyToOne
    private User user;

    public Loan() {}  // <-- This must be public

    // Getters and Setters
    public void setUser(User user) {
        this.user = user;
    }

    public void setPublicationId(Long publicationId) {
        this.publicationId = publicationId;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    // Add getters if needed
}
