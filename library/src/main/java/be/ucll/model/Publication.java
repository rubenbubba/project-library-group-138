package be.ucll.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import java.time.LocalDate;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "dtype")          // Book or Magazine
public abstract class Publication {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Title is required.")
    private String title;

    @Positive(message = "Publication year must be a positive integer.")
    @Column(name = "publication_year")
    private int publicationYear;

    @Positive(message = "Number of copies must be a positive integer.")
    @Column(name = "number_of_copies")
    private int numberOfCopies;

    protected Publication() {}

    protected Publication(String title, int year, int copies) {
        if (year > LocalDate.now().getYear())
            throw new RuntimeException("Publication year cannot be in the future.");
        this.title = title;
        this.publicationYear = year;
        this.numberOfCopies = copies;
    }

    /* getters */
    public Long   getId()             { return id; }
    public String getTitle()          { return title; }
    public int    getPublicationYear(){ return publicationYear; }
    public int    getNumberOfCopies() { return numberOfCopies; }

    /* business */
    public void lendPublication() {
        if (numberOfCopies == 0) throw new RuntimeException("No copies available.");
        numberOfCopies--;
    }
    public void returnPublication() { numberOfCopies++; }
}
