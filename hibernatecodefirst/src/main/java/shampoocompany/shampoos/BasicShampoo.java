package shampoocompany.shampoos;

import org.hibernate.engine.jdbc.Size;
import shampoocompany.ingredients.BasicIngredient;

import java.math.BigDecimal;
import java.util.Set;

public class BasicShampoo implements Shampoo {
    private long id;
    private String brand;
    private BigDecimal price;
    private Size size;
    private BasicLabel label;
    private ProductionBatch batch;
    private Set<BasicIngredient> ingredients;

    public BasicShampoo() {

    }

    public BasicShampoo(String brand,
                        BigDecimal price,
                        Size size,
                        BasicLabel label) {
        setBrand(brand);
        setPrice(price);
        setSize(size);
        setLabel(label);
    }

    @Override
    public long getId() {
        return this.id;
    }

    @Override
    public void setId(long id) {
        this.id = id;
    }

    @Override
    public String getBrand() {
        return this.brand;
    }

    @Override
    public void setBrand(String brand) {
        this.brand = brand;
    }

    @Override
    public BigDecimal getPrice() {
        return this.price;
    }

    @Override
    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Override
    public Size getSize() {
        return this.size;
    }

    @Override
    public void setSize(Size size) {
        this.size = size;
    }

    @Override
    public BasicLabel getLabel() {
        return this.label;
    }

    @Override
    public void setLabel(BasicLabel label) {
        this.label = label;
    }

    @Override
    public ProductionBatch getBatch() {
        return this.batch;
    }

    @Override
    public void setBatch(ProductionBatch batch) {
        this.batch = batch;
    }

    @Override
    public Set<BasicIngredient> getIngredients() {
        return this.ingredients;
    }

    @Override
    public void setIngredients(Set<BasicIngredient> ingredients) {
        this.ingredients = ingredients;
    }
}
