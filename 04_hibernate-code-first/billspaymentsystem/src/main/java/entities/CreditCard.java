package entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.time.Month;
import java.time.Year;
import java.util.Date;

@Entity
public class CreditCard extends BillingDetail {
    private String cardType;
    private Month expirationMonth;
    private Year expirationYear;

    public CreditCard() {
        super();
    }

    public CreditCard(String number,
                      User owner,
                      String cardType,
                      Month expirationMonth,
                      Year expirationYear) {
        super(number, owner);
        setCardType(cardType);
        setExpirationMonth(expirationMonth);
        setExpirationYear(expirationYear);
    }

    @Column(name = "card_type",
            nullable = false)
    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    //TODO: Fix the bug with wrong month & year display in DB
    @Column(name = "expiration_month",
            nullable = false)
    public Month getExpirationMonth() {
        return expirationMonth;
    }

    public void setExpirationMonth(Month expirationMonth) {
        this.expirationMonth = expirationMonth;
    }

    @Column(name = "expiration_year",
            nullable = false)
    public Year getExpirationYear() {
        return expirationYear;
    }

    public void setExpirationYear(Year expirationYear) {
        this.expirationYear = expirationYear;
    }
}
