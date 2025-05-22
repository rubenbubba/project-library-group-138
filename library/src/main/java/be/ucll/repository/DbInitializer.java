package be.ucll.repository;

import be.ucll.model.Profile;
import be.ucll.model.User;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

@Component
public class DbInitializer {

    private final UserRepository userRepo;

    public DbInitializer(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    @PostConstruct
    public void populate() {

        // Alice – 25y, java interest, Leuven
        User alice = new User("Alice","password123","alice@ucll.be",25);
        alice.setProfile(new Profile("Leuven","hiking,java"));
        userRepo.save(alice);

        // Bob – 15y, no profile
        userRepo.save(new User("Bob","password123","bob@ucll.be",15));

        // Carl – 68y (oldest)
        userRepo.save(new User("Carl","password123","carl@ucll.be",68));

        // Dave – 40y, java interest, Antwerp
        User dave = new User("Dave","password123","dave@ucll.be",40);
        dave.setProfile(new Profile("Antwerp","java,cycling"));
        userRepo.save(dave);
    }
}
