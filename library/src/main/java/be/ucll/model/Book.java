package be.ucll.model;

import java.time.LocalDate;

public class Book {
    private String author;
    private String isbn;
    private  String title;
    private int publicationYear;


    public Book(String author, String isbn, String title) {
        setAuthor(author);
        setIsbn(isbn);
        setTitle(title);
    }

    public String getAuthor() {
        return this.author;
    }
    public void setAuthor(String author) {
        if(author == null || author.trim().isEmpty()){
            throw new RuntimeException("Author is required.");
        }
        this.author = author;
    }

    public String getIsbn() {
        return this.isbn;
    }
    public void setIsbn(String isbn) {
        String trimmedIsbn = isbn.replace("-", "");

        if(isbn == null || isbn.trim().isEmpty()) {
            throw new RuntimeException("Isbn is required.");
        } else if (trimmedIsbn.matches("\\d+")) { // Check if isbn contains only digits
            throw new RuntimeException("Isbn can only contain digits.");
        } else if (trimmedIsbn.length() != 13) {
            throw new RuntimeException("Isbn requires exactly 13 digits.");
        }
        this.isbn = isbn;
    }

    public String getTitle() {
        return this.title;
    }
    public void setTitle(String title) {
        if(title == null || title.trim().isEmpty()){
            throw new RuntimeException("Title is required.");
        }
        this.title = title;
    }

    public int getPublicationYear() {
        return this.publicationYear;
    }
    public void setPublicationYear(int publicationYear) {
        if(publicationYear < 0) {
            throw new RuntimeException("Publication year must be positive.");
        } else if(publicationYear > LocalDate.now().getYear()) {
            throw new RuntimeException("Publication year cannot be in the future.");
        }
        this.publicationYear = publicationYear;
    }
}
