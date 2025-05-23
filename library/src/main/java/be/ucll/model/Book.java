package be.ucll.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotBlank;

@Entity
@DiscriminatorValue("BOOK")
public class Book extends Publication {

    @NotBlank(message = "Author is required.")
    private String author;

    @NotBlank(message = "ISBN is required.")
    private String isbn;

    protected Book() { }   // JPA

    public Book(String title, int year, int copies,
                String author, String isbn) {
        super(title, year, copies);
        this.author = author;
        this.isbn   = isbn;
    }

    public String getAuthor() { return author; }
    public String getIsbn()   { return isbn;   }
}
