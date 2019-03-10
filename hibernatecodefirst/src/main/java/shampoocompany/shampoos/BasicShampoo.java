package shampoocompany.shampoos;

import shampoocompany.ingredients.BasicIngredient;
import shampoocompany.labels.BasicLabel;
import shampoocompany.labels.ProductionBatch;
import shampoocompany.labels.Size;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "shampoos")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "shampoo_type",
        discriminatorType = DiscriminatorType.STRING)
public class BasicShampoo implements Shampoo {
    private long id;
    private String brand;
    private BigDecimal price;
    private Size size;
    private BasicLabel label;
    private Set<BasicIngredient> ingredients;

    protected BasicShampoo() {
        this.setIngredients(new HashSet<>());
    }

    BasicShampoo(String brand,
                        BigDecimal price,
                        Size size,
                        BasicLabel label) {
        setBrand(brand);
        setPrice(price);
        setSize(size);
        setLabel(label);
        setIngredients(new HashSet<>());
    }

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Override
    public long getId() {
        return this.id;
    }

    @Override
    public void setId(long id) {
        this.id = id;
    }

    @Column
    @Override
    public String getBrand()  {
        return this.brand;
    }

    @Override
    public void setBrand(String brand) {
        this.brand = brand;
    }

    @Column
    @Override
    public BigDecimal getPrice() {
        return this.price;
    }

    @Override
    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Enumerated
    @Override
    public Size getSize() {
        return this.size;
    }

    @Override
    public void setSize(Size size) {
        this.size = size;
    }

    @OneToOne(optional = true, cascade = CascadeType.ALL,
            fetch = FetchType.LAZY)
    @JoinColumn(name = "label",
            referencedColumnName = "id")
    @Override
    public BasicLabel getLabel() {
        return this.label;
    }

    @Override
    public void setLabel(BasicLabel label) {
        this.label = label;
    }

    @ManyToMany(cascade = CascadeType.ALL,
            fetch = FetchType.LAZY)
    @JoinTable(name = "shampoos_ingredients",
            joinColumns = @JoinColumn(name = "shampoo_id",
                    referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "ingredient_id",
                    referencedColumnName = "id"))
    @Override
    public Set<BasicIngredient> getIngredients() {
        return this.ingredients;
    }

    @Override
    public void setIngredients(Set<BasicIngredient> ingredients) {
        this.ingredients = ingredients;
    }
}
