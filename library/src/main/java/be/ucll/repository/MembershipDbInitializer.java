package be.ucll.repository;

import be.ucll.model.Membership;
import be.ucll.model.User;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class MembershipDbInitializer {

    private final MembershipRepository memberships;
    private final UserRepository       users;

    public MembershipDbInitializer(MembershipRepository mRepo, UserRepository uRepo) {
        this.memberships = mRepo;
        this.users       = uRepo;
    }

    @PostConstruct
    public void seed() {

        User user = users.findByEmail("john@ucll.be").orElse(null);
        if (user == null) return;

        Membership m = new Membership(LocalDate.now(),
                LocalDate.now().plusYears(1),
                5);
        m.setUser(user);
        user.setMembership(m);

        memberships.save(m);
    }
}
