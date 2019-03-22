package app.models;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.File;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "users")
public class User {
    private Long id;
    private String username;
    private String password;
    private String email;
    private File profilePicture;
    private Timestamp registeredOn;
    private Timestamp lastTimeLoggedIn;
    private Integer age;
    private Boolean isDeleted;
    private Town bornTown;
    private Town currentlyLiving;
    private String firstName;
    private String lastName;
    private String fullName;
    private Set<User> friends;
    private Set<User> persons;

    public User() {

    }

    public User(String username,
                String password,
                String email,
                Timestamp registeredOn,
                Integer age) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.registeredOn = registeredOn;
        this.age = age;
        this.registeredOn = new Timestamp(
                new Date().getTime());
        this.isDeleted = false;
    }

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Min(1)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column
    @NotNull
    @Size(min = 4, max = 30)
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Column
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Column
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Column(name = "profile_picture")
    public File getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(File profilePicture) {
        this.profilePicture = profilePicture;
    }

    @Column(name = "registered_on")
    public Timestamp getRegisteredOn() {
        return registeredOn;
    }

    public void setRegisteredOn(Timestamp registeredOn) {
        this.registeredOn = registeredOn;
    }

    @Column(name = "last_time_logged_in")
    public Timestamp getLastTimeLoggedIn() {
        return lastTimeLoggedIn;
    }

    public void setLastTimeLoggedIn(Timestamp lastTimeLoggedIn) {
        this.lastTimeLoggedIn = lastTimeLoggedIn;
    }

    @Column
    @Min(1)
    @Max(120)
    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @Column(name = "is_deleted")
    public Boolean getDeleted() {
        return isDeleted;
    }

    public void setDeleted(Boolean deleted) {
        isDeleted = deleted;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "born_town_id")
    public Town getBornTown() {
        return bornTown;
    }

    public void setBornTown(Town bornTown) {
        this.bornTown = bornTown;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "currently_living_id")
    public Town getCurrentlyLiving() {
        return currentlyLiving;
    }

    public void setCurrentlyLiving(Town currentlyLiving) {
        this.currentlyLiving = currentlyLiving;
    }

    @Column(name = "first_name")
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Column(name = "last_name")
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    //TODO: Find how to exclude the full_name column from DB
    public String getFullName() {
        return this.fullName;
    }

    public void setFullName() {
        this.fullName = firstName + " " + lastName;
    }

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "friends",
    joinColumns = @JoinColumn(name = "friend_id"),
    inverseJoinColumns = @JoinColumn(name = "person_id"))
    public Set<User> getFriends() {
        return friends;
    }

    public void setFriends(Set<User> friends) {
        this.friends = friends;
    }

    @ManyToMany(mappedBy = "friends")
    public Set<User> getPersons() {
        return persons;
    }

    public void setPersons(Set<User> persons) {
        this.persons = persons;
    }
}
