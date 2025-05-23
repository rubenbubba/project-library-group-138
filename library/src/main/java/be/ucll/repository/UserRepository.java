package be.ucll.repository;

import be.ucll.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    /* single ---- */
    Optional<User> findByEmail(String email);
    User           findFirstByOrderByAgeDesc();               // oldest

    /* filters ---- */
    List<User> findByNameContainingIgnoreCase(String part);

    List<User> findByAgeGreaterThanEqualAndAgeLessThanEqual(int min, int max);

    List<User> findByAgeGreaterThanEqual(int minAge);

    List<User> findByProfile_InterestsContainingIgnoreCase(String interest);

    List<User> findByAgeGreaterThanEqualAndProfile_InterestsContainingIgnoreCaseOrderByProfile_LocationAsc(
            int minAge, String interest);
}
