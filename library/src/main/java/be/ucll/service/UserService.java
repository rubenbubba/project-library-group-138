package be.ucll.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import be.ucll.model.User;
import be.ucll.repository.UserRepository;

@Service
public class UserService {

    private final UserRepository repo;

    public UserService(UserRepository repo) {
        this.repo = repo;
    }

    public List<User> getAllUsers() {
        return repo.findAll();
    }

    public List<User> filterByName(String nameSubstring) {
        return repo.findByNameContainingIgnoreCase(nameSubstring);
    }

    public List<User> getAdults(int minAge) {
        return repo.findByAgeGreaterThanEqual(minAge);
    }

    public List<User> getUsersInRange(int minAge, int maxAge) {
        return repo.findByAgeGreaterThanEqualAndAgeLessThanEqual(minAge, maxAge);
    }

    public User getOldestUser() {
        return repo.findFirstByOrderByAgeDesc();
    }

    public List<User> findByInterest(String interest) {
        return repo.findByProfile_InterestsContainingIgnoreCase(interest);
    }

    public List<User> findByAgeAndInterestSorted(int minAge, String interest) {
        return repo.findByAgeGreaterThanEqualAndProfile_InterestsContainingIgnoreCaseOrderByProfile_LocationAsc(
                minAge, interest);
    }

    @Transactional
    public User addUser(User user) {
        repo.findByEmailIgnoreCase(user.getEmail()).ifPresent(u ->
        { throw new RuntimeException("User already exists: " + user.getEmail()); });
        return repo.save(user);
    }

    @Transactional
    public User updateUser(String email, User updated) {
        User existing = repo.findByEmailIgnoreCase(email)
                .orElseThrow(() -> new RuntimeException("No user with email: " + email));
        // ensure email cannot change
        if (!existing.getEmail().equalsIgnoreCase(updated.getEmail())) {
            throw new RuntimeException("Email cannot be changed");
        }
        existing.setName(updated.getName());
        existing.setAge(updated.getAge());
        existing.setPassword(updated.getPassword());
        return repo.save(existing);
    }

    @Transactional
    public void deleteUser(String email) {
        User existing = repo.findByEmailIgnoreCase(email)
                .orElseThrow(() -> new RuntimeException("No user with email: " + email));
        repo.delete(existing);
    }
}
