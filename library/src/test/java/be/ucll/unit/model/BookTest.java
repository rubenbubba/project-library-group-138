package be.ucll.unit.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class BookTest {
    @Test
    void givenValidBook_whenCreate_thenFieldsAreSet() {
        Book b = new Book("Title", "Author", "978-0-545-01022-1", 2005, 3);
        assertEquals("Title", b.getTitle());
        assertEquals("Author", b.getAuthor());
        assertEquals("978-0-545-01022-1", b.getIsbn());
        assertEquals(2005, b.getPublicationYear());
        assertEquals(3, b.getAvailableCopies());
    }

    @Test
    void givenFutureYear_whenCreate_thenException() {
        assertThrows(RuntimeException.class,
                () -> new Book("T", "A", "978-0-545-01022-1", LocalDate.now().getYear() + 1, 1)
        );
    }
    // ... other unhappy-paths
}