package entities;

import db.annotations.Column;
import db.annotations.Entity;
import db.annotations.Primary;

import java.sql.Date;
import java.text.DateFormat;

@Entity(name = "users")
public class User {
    @Primary(name = "id")
    private long id;

    @Column(name = "username")
    private String username;

    @Column(name = "age")
    private long age;

    @Column(name = "registration_date")
    private Date registrationDate;

    public User() {
    }

    public User(String username, int age,
                Date registrationDate) {
        setUsername(username);
        setAge(age);
        setRegistrationDate(registrationDate);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public long getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Date getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(Date registrationDate) {
        this.registrationDate = registrationDate;
    }

    @Override
    public String toString() {
        String registrationDate =
                getRegistrationDate() == null ?
                        "Not registered yet":
                        getRegistrationDate().toString();
        return String.format("%d | %s | %d | %s",
                getId(),
                getUsername(),
                getAge(),
                registrationDate);
    }
}
