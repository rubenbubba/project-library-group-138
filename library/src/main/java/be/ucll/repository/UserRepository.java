package be.ucll.repository;

import be.ucll.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmailIgnoreCase(String email);
    List<User> findByNameContainingIgnoreCase(String name);
    List<User> findByAgeGreaterThanEqual(int age);
    List<User> findByAgeGreaterThanEqualAndAgeLessThanEqual(int minAge, int maxAge);
    User findFirstByOrderByAgeDesc();
    List<User> findByProfile_InterestsContainingIgnoreCase(String interest);
    List<User> findByAgeGreaterThanEqualAndProfile_InterestsContainingIgnoreCaseOrderByProfile_LocationAsc(int age, String interest);
}
