package com.zvezdomirov.bookshopsystem.modules;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "books")
public class Book {
    private long id;
    private String title;
    private String description;
    private EditionType editionType;
    private BigDecimal price;
    private long copies;
    private Date releaseDate;
    private AgeRestriction ageRestriction;
    private Set<Category> categories;
    private Author author;

    public Book() {

    }

    public Book(String title,
                String description,
                EditionType editionType,
                BigDecimal price,
                long copies,
                AgeRestriction ageRestriction) {
        setTitle(title);
        setDescription(description);
        setEditionType(editionType);
        setPrice(price);
        setCopies(copies);
        setAgeRestriction(ageRestriction);
        setCategories(new HashSet<>());
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

    @Column(length = 50,
            nullable = false)
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Column(length = 1000,
            nullable = true)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Column(name = "edition_type",
            nullable = false)
    @Enumerated
    public EditionType getEditionType() {
        return editionType;
    }

    public void setEditionType(EditionType editionType) {
        this.editionType = editionType;
    }

    @Column(nullable = false)
    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Column(nullable = false)
    public long getCopies() {
        return copies;
    }

    public void setCopies(long copies) {
        this.copies = copies;
    }


    @Column(name = "release_date",
            nullable = true)
    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    @Column(name = "age_restriction",
            nullable = false)
    @Enumerated
    public AgeRestriction getAgeRestriction() {
        return ageRestriction;
    }

    public void setAgeRestriction(AgeRestriction ageRestriction) {
        this.ageRestriction = ageRestriction;
    }

    @ManyToMany
    @JoinTable(name = "books_categories",
            joinColumns = @JoinColumn(name = "book_id",
                    referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "category_id",
                    referencedColumnName = "id"))
    public Set<Category> getCategories() {
        return categories;
    }

    public void setCategories(Set<Category> categories) {
        this.categories = categories;
    }

    @ManyToOne(targetEntity = Author.class,
        fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id",
    referencedColumnName = "id")
    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }
}
