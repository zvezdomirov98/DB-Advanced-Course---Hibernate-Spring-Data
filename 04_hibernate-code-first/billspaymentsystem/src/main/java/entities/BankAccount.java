package entities;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class BankAccount extends BillingDetail {
    private String bankName;
    private String swiftCode;

    public BankAccount() {
        super();
    }

    public BankAccount(String number,
                       User owner,
                       String bankName,
                       String swiftCode) {
        super(number, owner);
        setBankName(bankName);
        setSwiftCode(swiftCode);
    }

    @Column(name = "bank_name",
            nullable = false,
            columnDefinition = "VARCHAR(50) default '-'")
    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    @Column(name = "swift_code",
            unique = true,
            nullable = false,
            columnDefinition = "VARCHAR(50) default '-1'")
    public String getSwiftCode() {
        return swiftCode;
    }

    public void setSwiftCode(String swiftCode) {
        this.swiftCode = swiftCode;
    }
}
