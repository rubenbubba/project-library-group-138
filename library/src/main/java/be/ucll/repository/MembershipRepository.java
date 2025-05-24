package be.ucll.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import be.ucll.model.Membership;

@Repository
public interface MembershipRepository extends JpaRepository<Membership, Long> {
    // no extra methods needed—save(), delete(), findAll(), etc. come from JpaRepository
}
