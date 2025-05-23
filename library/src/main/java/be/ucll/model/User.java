package be.ucll.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
@Table(name = "users")
public class User {

    /* ---------- columns ---------- */
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Name is required.")
    private String name;

    @Email(message = "Email must be valid.")
    @Column(unique = true, nullable = false)
    private String email;

    @Size(min = 8, message = "Password must be at least 8 characters long.")
    private String password;

    @Min(0) @Max(101)
    private int age;

    /* profile (story 22) */
    @OneToOne(cascade = CascadeType.ALL)
    private Profile profile;

    /* membership (story 27) */
    @OneToOne(cascade = CascadeType.ALL)
    private Membership membership;

    /* ---------- constructors ---------- */
    protected User() {/* JPA */}

    public User(String name, String email, String password, int age) {
        this.name     = name.trim();
        this.email    = email.trim();
        this.password = password;
        this.age      = age;
    }

    /* ---------- helpers ---------- */
    public void setProfile(Profile p) {        // bidirectional link helper
        this.profile = p;
        p.setUser(this);
    }

    public void setMembership(Membership m) {  // bidirectional link helper
        this.membership = m;
        m.setUser(this);
    }

    /* ---------- getters used by services ---------- */
    public int        getAge()       { return age; }
    public String     getEmail()     { return email; }
    public Profile    getProfile()   { return profile; }
    public Membership getMembership(){ return membership; }
}
