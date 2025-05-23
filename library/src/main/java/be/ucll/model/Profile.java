package be.ucll.model;

import jakarta.persistence.*;

@Entity
public class Profile {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String interests;
    private String location;

    @OneToOne(mappedBy = "profile")
    private User user;

    protected Profile() {}

    public Profile(String interests, String location) {
        this.interests = interests;
        this.location  = location;
    }

    /* link helper */
    public void setUser(User u) { this.user = u; }
}
