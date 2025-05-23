package be.ucll.service;

import be.ucll.model.User;
import be.ucll.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class UserService {

    private final UserRepository repo;

    public UserService(UserRepository repo) { this.repo = repo; }

    /* ---------- queries ---------- */
    public List<User> getAllUsers()             { return repo.findAll(); }
    public List<User> filterByName(String s)    { return repo.findByNameContainingIgnoreCase(s); }
    public List<User> getAdults()               { return repo.findByAgeGreaterThanEqual(18); }
    public List<User> getUsersInAgeRange(int a, int b) {
        if (a > b) throw new RuntimeException("Min age > max age");
        return repo.findByAgeGreaterThanEqualAndAgeLessThanEqual(a, b);
    }
    public User getOldestUser()                 { return repo.findFirstByOrderByAgeDesc(); }
    public List<User> findByInterest(String i)  { return repo.findByProfile_InterestsContainingIgnoreCase(i); }

    public List<User> olderThanWithInterestSorted(int minAge, String interest) {
        return repo.findByAgeGreaterThanEqualAndProfile_InterestsContainingIgnoreCaseOrderByProfile_LocationAsc(
                minAge, interest);
    }

    /* ---------- CRUD ---------- */
    public User addUser(@Valid User u) {
        if (repo.findByEmail(u.getEmail()).isPresent())
            throw new RuntimeException("User already exists");
        return repo.save(u);
    }

    public User updateUser(String email, @Valid User updated) {
        User existing = repo.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        if (!existing.getEmail().equals(updated.getEmail()))
            throw new RuntimeException("Email cannot be changed");

        /* simple field updates */
        existing.setProfile(updated.getProfile());   // deep copy would be nicer
        existing.setMembership(updated.getMembership());
        return repo.save(existing);
    }

    public void deleteUser(String email) {
        User user = repo.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        repo.delete(user);
    }
}
