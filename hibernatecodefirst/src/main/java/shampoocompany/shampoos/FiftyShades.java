package shampoocompany.shampoos;

import org.hibernate.engine.jdbc.Size;

import java.math.BigDecimal;

public class FiftyShades extends BasicShampoo {
    private static final String BRAND = "Fifty Shades";
    private static final BigDecimal PRICE = new BigDecimal("6.69");
    private static final Size SIZE = new Size();


    public FiftyShades(BasicLabel classicLabel) {
        super(BRAND, PRICE, SIZE, classicLabel);
    }
}
