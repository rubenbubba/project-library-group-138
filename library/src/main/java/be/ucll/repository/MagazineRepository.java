package be.ucll.repository;

import be.ucll.model.Magazine;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Repository dedicated to {@link Magazine}.
 */
public interface MagazineRepository extends JpaRepository<Magazine, Long> {

    /** Magazines that can still be borrowed. */
    List<Magazine> findByNumberOfCopiesGreaterThan(int numberOfCopies);
}
