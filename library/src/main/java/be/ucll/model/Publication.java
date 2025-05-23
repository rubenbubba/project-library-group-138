package be.ucll.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "publication")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "dtype")
public abstract class Publication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String title;

    @Min(1)
    private int publicationYear;

    @Min(0)
    private int numberOfCopies;

    /* ---------- ctors ---------- */
    protected Publication() { }

    protected Publication(String title, int year, int copies) {
        this.title = title;
        this.publicationYear = year;
        this.numberOfCopies  = copies;
    }

    /* ---------- getters ---------- */
    public Long   getId()             { return id; }
    public String getTitle()          { return title; }
    public int    getPublicationYear(){ return publicationYear; }
    public int    getNumberOfCopies() { return numberOfCopies; }

    /* ---------- helpers ---------- */
    public void changeStock(int delta) {
        this.numberOfCopies += delta;
        if (numberOfCopies < 0)
            throw new RuntimeException("Negative stock not allowed");
    }
}
