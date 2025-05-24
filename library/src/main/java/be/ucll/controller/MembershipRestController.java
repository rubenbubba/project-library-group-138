package be.ucll.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import be.ucll.model.Membership;
import be.ucll.service.MembershipService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/memberships")
@Validated
public class MembershipRestController {

    private final MembershipService memberships;

    public MembershipRestController(MembershipService memberships) {
        this.memberships = memberships;
    }

    @PostMapping("/{email}")
    public ResponseEntity<Membership> registerForUser(
            @PathVariable String email,
            @Valid @RequestBody Membership membership)
    {
        Membership created = memberships.registerForUser(email, membership);
        return ResponseEntity.ok(created);
    }

    // … other endpoints
}
