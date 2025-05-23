package be.ucll.repository;

import be.ucll.model.Publication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Shared JPA repository for the single-table “publications” entity.
 * <p>
 * **Important :** Do <b>not</b> declare Book- or Magazine-specific columns
 * here – those go in <code>BookRepository</code> / <code>MagazineRepository</code>.
 */
@Repository
public interface PublicationRepository extends JpaRepository<Publication, Long> {

    /* copies > minCopies */
    List<Publication> findByNumberOfCopiesGreaterThan(int minCopies);
}
