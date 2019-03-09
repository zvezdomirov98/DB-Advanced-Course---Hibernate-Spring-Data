package shampoocompany.ingredients;

import java.math.BigDecimal;

public class Strawberry extends BasicIngredient {

    private static final String NAME = "Strawberry";
    private static final BigDecimal PRICE = new BigDecimal("4.85");

    public Strawberry() {
        super(NAME, PRICE);
    }
}
