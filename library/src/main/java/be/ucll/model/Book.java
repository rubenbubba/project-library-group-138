package be.ucll.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;

@Entity
@DiscriminatorValue("BOOK")
public class Book extends Publication {

    @NotBlank(message = "Author is required.")
    private String author;

    @Pattern(regexp = "\\d{13}", message = "Isbn requires exactly 13 digits.")
    private String isbn;

    protected Book() {}    // JPA

    public Book(String author, String isbn, String title, int year, @Positive int copies) {
        super(title, year, copies);
        this.author = author;
        this.isbn   = isbn;
    }

    public String getAuthor() { return author; }
    public String getIsbn()   { return isbn; }
}
