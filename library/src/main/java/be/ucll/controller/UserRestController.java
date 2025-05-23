package be.ucll.controller;

import be.ucll.model.User;
import be.ucll.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserRestController {

    private final UserService service;

    public UserRestController(UserService service) { this.service = service; }

    /* ---------------- GET collection ---------------- */
    @GetMapping
    public List<User> getUsers(@RequestParam(required = false) String name) {
        return name == null ? service.getAllUsers()
                : service.filterByName(name);
    }

    @GetMapping("/adults")
    public List<User> adults() { return service.getAdults(); }

    @GetMapping("/age/{min}/{max}")
    public List<User> range(@PathVariable int min, @PathVariable int max) {
        return service.getUsersInAgeRange(min, max);
    }

    @GetMapping("/oldest")
    public User oldest() { return service.getOldestUser(); }

    @GetMapping("/interest/{tag}")
    public List<User> byInterest(@PathVariable String tag) {
        return service.findByInterest(tag);
    }

    @GetMapping("/interest/{tag}/older-than/{age}")
    public List<User> interestAndAge(@PathVariable String tag,
                                     @PathVariable int age) {
        return service.olderThanWithInterestSorted(age, tag);
    }

    /* ---------------- POST / PUT / DELETE ------------ */
    @PostMapping
    public ResponseEntity<User> add(@Valid @RequestBody User u) {
        return ResponseEntity.ok(service.addUser(u));
    }

    @PutMapping("/{email}")
    public ResponseEntity<User> update(@PathVariable String email,
                                       @Valid @RequestBody User u) {
        return ResponseEntity.ok(service.updateUser(email, u));
    }

    @DeleteMapping("/{email}")
    public ResponseEntity<Void> delete(@PathVariable String email) {
        service.deleteUser(email);
        return ResponseEntity.ok().build();
    }
}
