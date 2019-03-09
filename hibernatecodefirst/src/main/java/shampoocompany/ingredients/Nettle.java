package shampoocompany.ingredients;

import java.math.BigDecimal;

public class Nettle extends BasicIngredient {

    private static final String NAME = "Nettle";
    private static final BigDecimal PRICE = new BigDecimal("6.12");

    public Nettle() {
        super(NAME, PRICE);
    }
}
