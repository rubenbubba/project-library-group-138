package be.ucll.repository;

import be.ucll.model.Profile;
import be.ucll.model.User;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

@Component
public class DbInitializer {

    private final UserRepository users;

    public DbInitializer(UserRepository users) { this.users = users; }

    @PostConstruct
    public void seed() {

        if (users.count() > 0) return;          // already seeded

        User alice = new User("Alice", "alice@ucll.be", "alice1thtrh", 22);
        alice.setProfile(new Profile("Reading,Travel", "Leuven"));

        User bob   = new User("Bob",   "bob@ucll.be",   "bob123htr4",   35);

        User dave  = new User("Dave",  "dave@ucll.be",  "dave12htrhr34",  45);
        dave.setProfile(new Profile("Travel,Cycling", "Antwerp"));

        users.save(alice);
        users.save(bob);
        users.save(dave);
    }
}
