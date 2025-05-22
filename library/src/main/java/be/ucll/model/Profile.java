package be.ucll.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "profile")
public class Profile {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String location;

    private String interests;

    /* JPA needs the default ctor */
    protected Profile() {}

    public Profile(String location, String interests) {
        this.location   = location;
        this.interests  = interests;
    }

    /* getters */
    public Long getId()           { return id; }
    public String getLocation()   { return location; }
    public String getInterests()  { return interests; }
}
