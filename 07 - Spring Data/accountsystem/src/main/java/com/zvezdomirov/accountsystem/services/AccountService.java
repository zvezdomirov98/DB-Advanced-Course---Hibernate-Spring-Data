package com.zvezdomirov.accountsystem2.services;

import com.zvezdomirov.accountsystem2.models.Account;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

public interface AccountService{
    void withdrawMoney(BigDecimal money, long id);

    void transferMoney(BigDecimal money, long id);
}
