package shampoocompany.ingredients;

import shampoocompany.shampoos.Shampoo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;

public interface Ingredient extends Serializable {
    String getName();
    void setName(String name);
    long getId();
    void setId(long id);
    BigDecimal getPrice();
    void setPrice(BigDecimal price);
    Collection<Shampoo> getShampoos();
    void setShampoos(Collection<Shampoo> shampoos);
}
