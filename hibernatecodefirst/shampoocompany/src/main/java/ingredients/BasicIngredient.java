package ingredients;

import shampoos.BasicShampoo;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Entity()
@Table(name = "ingredients")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "ingredient_type",
        discriminatorType = DiscriminatorType.STRING)
public class BasicIngredient {
    private long id;
    private String name;
    private BigDecimal price;
    private List<BasicShampoo> shampoos;

    public BasicIngredient() {

    }

    protected BasicIngredient(String name, BigDecimal price) {
        setName(name);
        setPrice(price);
    }

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Column
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column
    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @ManyToMany(mappedBy = "ingredients",
            cascade = CascadeType.ALL)
    public List<BasicShampoo> getShampoos() {
        return shampoos;
    }

    public void setShampoos(List<BasicShampoo> shampoos) {
        this.shampoos = shampoos;
    }
}
