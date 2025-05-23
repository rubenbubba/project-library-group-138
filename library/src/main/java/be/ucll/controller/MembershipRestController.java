package be.ucll.controller;

import be.ucll.model.Membership;
import be.ucll.service.MembershipService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/memberships")
public class MembershipRestController {

    private final MembershipService memberships;

    public MembershipRestController(MembershipService memberships) {
        this.memberships = memberships;
    }

    /** POST /memberships/{email}  – assign / renew membership for a user */
    @PostMapping("/{email}")
    @ResponseStatus(HttpStatus.CREATED)
    public Membership register(@PathVariable String email,
                               @Valid @RequestBody Membership body) {
        return memberships.registerForUser(email, body);
    }
}
