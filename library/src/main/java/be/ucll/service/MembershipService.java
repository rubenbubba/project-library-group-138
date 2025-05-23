package be.ucll.service;

import be.ucll.model.Membership;
import be.ucll.repository.MembershipRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MembershipService {
    private final MembershipRepository membershipRepository;

    public MembershipService(MembershipRepository membershipRepository) {
        this.membershipRepository = membershipRepository;
    }

    /**
     * Retrieve all memberships.
     */
    public List<Membership> getAllMemberships() {
        return membershipRepository.findAll();
    }

    /**
     * Create a new membership.
     */
    public Membership addMembership(Membership membership) {
        return membershipRepository.save(membership);
    }

    /**
     * Find a membership by its database id.
     */
    public Membership getMembershipById(Long id) {
        return membershipRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Membership not found: " + id));
    }

    /**
     * Update an existing membership.
     */
    public Membership updateMembership(Long id, Membership updated) {
        Membership existing = getMembershipById(id);
        existing.setStart(updated.getStart());
        existing.setEnd(updated.getEnd());
        existing.setFreeLoans(updated.getFreeLoans());
        return membershipRepository.save(existing);
    }

    /**
     * Delete a membership by id.
     */
    public void deleteMembership(Long id) {
        membershipRepository.deleteById(id);
    }
}
