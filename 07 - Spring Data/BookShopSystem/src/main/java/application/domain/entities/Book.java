package application.domain.entities;

import application.domain.enums.AgeRestriction;
import application.domain.enums.EditionType;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

@Entity(name = "books")
public class Book extends BaseEntity {
    private String title;
    private String description;
    private BigDecimal price;
    private Long copies;
    private AgeRestriction ageRestriction;
    private LocalDate releaseDate;
    private EditionType editionType;
    private Author author;
    private Set<Category> categories;

    public Book() {

    }

    @Column(length = 50,
            nullable = false)
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Column(columnDefinition = "text",
            length = 1000)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Column(nullable = false)
    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Column(nullable = false)
    public Long getCopies() {
        return copies;
    }

    public void setCopies(Long copies) {
        this.copies = copies;
    }

    @Enumerated(value = EnumType.ORDINAL)
    @Column(name = "age_restriction",
            nullable = false)
    public AgeRestriction getAgeRestriction() {
        return ageRestriction;
    }

    public void setAgeRestriction(AgeRestriction ageRestriction) {
        this.ageRestriction = ageRestriction;
    }

    @Column(name = "release_date")
    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    @Enumerated(value = EnumType.ORDINAL)
    @Column(name = "edition_type",
            nullable = false)
    public EditionType getEditionType() {
        return editionType;
    }

    public void setEditionType(EditionType editionType) {
        this.editionType = editionType;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id",
            referencedColumnName = "id")
    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "books_categories",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id"))
    public Set<Category> getCategories() {
        return categories;
    }

    public void setCategories(Set<Category> categories) {
        this.categories = categories;
    }
}
