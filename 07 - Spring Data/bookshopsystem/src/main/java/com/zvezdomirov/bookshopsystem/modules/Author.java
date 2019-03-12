package com.zvezdomirov.bookshopsystem.modules;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "authors")
public class Author {
    private long id;
    private String firstName;
    private String lastName;
    private Set<Book> books;

    public Author() {

    }

    public Author(String firstName,
                  String lastName) {
        setFirstName(firstName);
        setLastName(lastName);
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Column(name = "first_name",
            nullable = true)
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Column(name = "last_name",
            nullable = false)
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @OneToMany(mappedBy = "author")
    public Set<Book> getBooks() {
        return books;
    }

    public void setBooks(Set<Book> books) {
        this.books = books;
    }
}
