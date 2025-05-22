package be.ucll.repository;

import be.ucll.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.List;

public interface UserRepository extends JpaRepository<User,Long> {

    Optional<User> findByEmailIgnoreCase(String email);

    List<User> findByAgeGreaterThanEqual(int min);

    List<User> findByAgeBetween(int min, int max);

    List<User> findByNameContainingIgnoreCase(String name);
}
