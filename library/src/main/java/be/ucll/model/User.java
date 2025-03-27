package be.ucll.model;

public class User {
    private String name;
    private String password;
    private String email;
    private int age;


    public User(String name, String password, String email, int age) {
        setName(name);
        setPassword(password);
        setEmail(email);
        setAge(age);
    }


    /***  GETTERS  ***/
    public String getName() {
        return this.name;
    }
    public String getPassword() {
        return this.password;
    }
    public String getEmail() {
        return this.email;
    }
    public int getAge() {
        return this.age;
    }



    /***  SETTERS  ***/
    public void setName(String name) {
        if(name == null || name.trim().isEmpty()){
            throw new RuntimeException("Name is required.");
        }
        this.name = name;
    }
    public void setPassword(String password) {
        if(password == null || password.trim().length() < 8) {
            throw new RuntimeException("Password must be at least 8 characters long.");
        }
        this.password = password;
    }
    public void setEmail(String email) {
        // https://stackoverflow.com/a/5955780
        if(email == null || !email.contains("@") || !email.contains(".")) {
            throw new RuntimeException("E-mail must be a valid email format.");
        }
        this.email = email;
    }
    public void setAge(int age) {
        if (age < 0 || age > 101) {
            throw new RuntimeException("Age must be a positive integer between 0 and 101.");
        }
        this.age = age;
    }
}
