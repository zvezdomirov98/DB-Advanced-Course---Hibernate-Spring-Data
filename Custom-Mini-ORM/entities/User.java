package entities;

import orm.Column;
import orm.Entity;
import orm.Id;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.Field;
import java.sql.Date;


@Entity(name = "users")
public class User {
    @Id(name = "id")
    private int id;

    @Column(name = "username")
    private String username;

    @Column(name = "age")
    private int age;

    @Column(name = "registration_date")
    private Date registrationDate;

    public User(String username, int age, Date registrationDate) {
        setUsername(username);
        setAge(age);
        setRegistrationDate(registrationDate);
    }

    public int getId() {
        return this.id;
    }
    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getAge() {
        return this.age;
    }
    public void setAge(int age) {
        this.age = age;
    }

    public Date getDate() {
        return this.registrationDate;
    }

    public void setRegistrationDate(Date registrationDate) {
        this.registrationDate = registrationDate;
    }
}
