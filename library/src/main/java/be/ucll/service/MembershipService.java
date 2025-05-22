package be.ucll.service;

import be.ucll.model.Membership;
import be.ucll.model.User;
import be.ucll.repository.MembershipRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MembershipService {

    private final MembershipRepository repo;

    public MembershipService(MembershipRepository repo) {
        this.repo = repo;
    }

    @Transactional
    public Membership registerForUser(User user, Membership incoming) {
        // associate both sides
        incoming.setUser(user);
        user.setMembership(incoming);

        return repo.save(incoming);
    }
}
