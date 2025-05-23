package be.ucll.repository;

import be.ucll.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Repository dedicated to {@link Book}.  Keeps the API clean and avoids
 * subclass-specific properties in the root entity queries.
 */
public interface BookRepository extends JpaRepository<Book, Long> {

    /** Books that can still be borrowed. */
    List<Book> findByNumberOfCopiesGreaterThan(int numberOfCopies);
}
