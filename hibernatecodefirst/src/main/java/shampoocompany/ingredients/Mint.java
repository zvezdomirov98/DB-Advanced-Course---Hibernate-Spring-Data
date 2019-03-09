package shampoocompany.ingredients;

import java.math.BigDecimal;

public class Mint extends BasicIngredient {

    private static final String NAME = "Mint";
    private static final BigDecimal PRICE = new BigDecimal("3.54");

    public Mint() {
        super(NAME, PRICE);
    }
}
