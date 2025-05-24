package be.ucll.service;

import java.time.LocalDate;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import be.ucll.model.Membership;
import be.ucll.model.User;
import be.ucll.repository.MembershipRepository;
import be.ucll.repository.UserRepository;

@Service
public class MembershipService {

    private final UserRepository userRepo;
    private final MembershipRepository membershipRepo;

    public MembershipService(UserRepository userRepo,
                             MembershipRepository membershipRepo) {
        this.userRepo = userRepo;
        this.membershipRepo = membershipRepo;
    }

    @Transactional
    public Membership registerForUser(String email, Membership membershipRequest) {
        User user = userRepo.findByEmailIgnoreCase(email)
                .orElseThrow(() -> new RuntimeException("No user found with email: " + email));

        // Link to user
        membershipRequest.setUser(user);
        // Save & return
        return membershipRepo.save(membershipRequest);
    }
}
