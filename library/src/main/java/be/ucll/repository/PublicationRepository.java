package be.ucll.repository;

import be.ucll.model.Publication;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PublicationRepository extends JpaRepository<Publication,Long> {

    /* title filter */
    List<Publication> findByTitleContainingIgnoreCase(String title);

    /* discriminator handled by JPA, query by subclass type */
    List<Publication> findByAuthorNotNull();      // books
    List<Publication> findByEditorNotNull();      // magazines

    /* copies filter */
    List<Publication> findByNumberOfCopiesGreaterThanEqual(int min);
}
