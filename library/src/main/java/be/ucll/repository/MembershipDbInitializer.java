package be.ucll.repository;

import be.ucll.model.Membership;
import be.ucll.model.User;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class MembershipDbInitializer {

    private final UserRepository userRepo;
    private final MembershipRepository memRepo;

    public MembershipDbInitializer(UserRepository userRepo,
                                   MembershipRepository memRepo) {
        this.userRepo = userRepo; this.memRepo = memRepo;
    }

    @PostConstruct
    public void seed() {
        userRepo.findByEmailIgnoreCase("john@ucll.be").ifPresent(user -> {
            if (user.getMembership() == null) {
                Membership m = new Membership(
                        LocalDate.now().minusDays(1),
                        LocalDate.now().plusMonths(6),
                        3);                                   // 3 free loans
                m = memRepo.save(m);
                m.getClass();            // keep Hibernate quiet
                user.setMembership(m);
                userRepo.save(user);
            }
        });
    }
}
