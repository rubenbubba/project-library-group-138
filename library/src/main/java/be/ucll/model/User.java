package be.ucll.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
@Table(name = "users")
public class User {

    /* ---------- fields ---------- */
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Name is required.")
    private String name;

    @Size(min = 8, message = "Password must be at least 8 characters long.")
    private String password;

    @Email(message = "E-mail must be a valid email format.")
    private String email;

    @Min(value = 0)  @Max(value = 101)
    private int age;

    /* ---------- relations ---------- */
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "profile_id")
    private Profile profile;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private Membership membership;

    /* ---------- ctor ---------- */
    protected User() { }

    public User(String name,int age,String email,String password) {
        setName(name); setAge(age); setEmail(email); setPassword(password);
    }

    /* ---------- setters with basic validation ---------- */
    public void setName(String name)       { this.name = name; }
    public void setPassword(String pw)     { this.password = pw; }
    public void setEmail(String email)     { this.email = email; }
    public void setAge(int age)            { this.age = age; }

    /* ---------- getters ---------- */
    public String getName() { return name; }
    public String getEmail() { return email; }
    public int    getAge() { return age; }
    public Membership getMembership() { return membership; }
}
