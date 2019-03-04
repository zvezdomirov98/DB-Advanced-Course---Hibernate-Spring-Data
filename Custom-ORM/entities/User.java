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
        setRegistrationDate();
    }

    public User(String username, int age) {
        setUsername(username);
        setAge(age);
        setRegistrationDate();
    }

    public long getId() {
        return id;
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

    private void setRegistrationDate() {
        this.registrationDate = new java.sql.Date(new java.util.Date().getTime());
    }

    @Override
    public String toString() {
        return String.format("%d | %s | %d | %s",
                getId(),
                getUsername(),
                getAge(),
                getRegistrationDate().toString());
    }
}
