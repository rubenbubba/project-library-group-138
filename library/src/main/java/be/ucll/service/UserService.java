package be.ucll.service;

import be.ucll.model.User;
import be.ucll.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Facade for all user-related business logic.
 */
@Service
public class UserService {

    private final UserRepository repo;

    public UserService(UserRepository repo) {
        this.repo = repo;
    }

    /* ---------- Queries ---------- */

    public List<User> getAll()              { return repo.findAll(); }

    public User findByEmail(String email) {
        return repo.findByEmailIgnoreCase(email)
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + email));
    }

    public boolean exists(String email) { return repo.findByEmailIgnoreCase(email).isPresent(); }

    /* ---------- Commands ---------- */

    @Transactional
    public User add(User u) {
        if (exists(u.getEmail())) throw new IllegalStateException("User already exists");
        return repo.save(u);
    }

    @Transactional
    public User save(User u) {                       // used by MembershipService etc.
        return repo.save(u);
    }

    @Transactional
    public void delete(String email) {
        repo.delete(findByEmail(email));
    }
}
