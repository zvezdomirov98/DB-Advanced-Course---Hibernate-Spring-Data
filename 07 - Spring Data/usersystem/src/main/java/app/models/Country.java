package app.models;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "countries")
public class Country {
    private Long id;
    private String name;
    private Set<Town> towns;

    public Country() {
    }

    public Country(String name) {
        this.name = name;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @OneToMany(mappedBy = "country")
    public Set<Town> getTowns() {
        return towns;
    }

    public void setTowns(Set<Town> towns) {
        this.towns = towns;
    }
}
