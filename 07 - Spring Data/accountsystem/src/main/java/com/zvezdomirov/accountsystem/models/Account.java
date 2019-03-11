package com.zvezdomirov.accountsystem2.models;

import org.hibernate.annotations.Check;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "accounts")
//@Check(constraints = "balance >= 0")
public class Account {
    private long id;
    private BigDecimal balance;
    private User user;

    public Account() {

    }

    public Account(BigDecimal balance,
                   User user) {
        setBalance(balance);
        setUser(user);
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
    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id",
            referencedColumnName = "id")
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
