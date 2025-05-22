package be.ucll.controller;

import be.ucll.model.Membership;
import be.ucll.model.User;
import be.ucll.service.MembershipService;
import be.ucll.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST endpoints for memberships.
 */
@RestController
@RequestMapping("/memberships")
public class MembershipRestController {

    private final MembershipService memberships;
    private final UserService        users;

    public MembershipRestController(MembershipService memberships,
                                    UserService users) {
        this.memberships = memberships;
        this.users       = users;
    }

    /* ------------------------------------------------------------------
       POST /memberships/{email}
       Body : Membership JSON
       ------------------------------------------------------------------ */
    @PostMapping("/{email}")
    public ResponseEntity<Membership> registerMembership(@PathVariable String email,
                                                         @Valid @RequestBody Membership dto) {

        User user = users.findByEmail(email); // throws if absent

        Membership created = memberships.registerForUser(user, dto);
        users.save(user);                     // persist FK change

        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }
}
