package be.ucll.controller;

import be.ucll.model.User;
import be.ucll.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserRestController {

    private final UserService service;     // injected bean

    public UserRestController(UserService service) {
        this.service = service;
    }

    /* ---------- GET ---------- */

    @GetMapping
    public List<User> all(@RequestParam(required = false) String name) {
        return (name == null) ? service.getAllUsers() : service.filterByName(name);
    }

    @GetMapping("/adults")
    public List<User> adults() { return service.getAdults(); }

    @GetMapping("/age/{min}/{max}")
    public List<User> range(@PathVariable int min, @PathVariable int max) {
        return service.getUsersInAgeRange(min, max);
    }

    @GetMapping("/oldest")
    public User oldest() { return service.getOldestUser(); }

    /* --- NEW endpoints --- */

    @GetMapping("/interests")
    public List<User> byInterest(@RequestParam String interest) {
        return service.findByInterest(interest);
    }

    @GetMapping("/interests/location")
    public List<User> interestAndAgeSorted(@RequestParam String interest,
                                           @RequestParam int age) {
        return service.olderThanWithInterestSorted(age, interest);
    }

    /* ---------- POST / PUT / DELETE ---------- */

    @PostMapping
    public ResponseEntity<User> add(@Valid @RequestBody User user) {
        return new ResponseEntity<>(service.addUser(user), HttpStatus.OK);
    }

    @PutMapping("/{email}")
    public ResponseEntity<User> update(@PathVariable String email,
                                       @Valid @RequestBody User changes) {
        return new ResponseEntity<>(service.updateUser(email, changes), HttpStatus.OK);
    }

    @DeleteMapping("/{email}")
    public ResponseEntity<Void> delete(@PathVariable String email) {
        service.deleteUser(email);
        return ResponseEntity.ok().build();
    }
}
