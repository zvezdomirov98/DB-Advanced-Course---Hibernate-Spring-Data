package shampoocompany.ingredients;

import shampoocompany.shampoos.Shampoo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.List;

@Entity()
@Table(name = "ingredients")
public class BasicIngredient {
    private long id;
    private String name;
    private BigDecimal price;
    private List<Shampoo> shampoos;

    public BasicIngredient() {

    }

    public BasicIngredient(String name, BigDecimal price) {
        setName(name);
        setPrice(price);
    }

    @Id
    @Column
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

    public List<Shampoo> getShampoos() {
        return shampoos;
    }

    public void setShampoos(List<Shampoo> shampoos) {
        this.shampoos = shampoos;
    }
}
