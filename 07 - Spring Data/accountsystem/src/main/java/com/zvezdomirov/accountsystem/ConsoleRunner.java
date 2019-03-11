package com.zvezdomirov.accountsystem2;

import com.zvezdomirov.accountsystem2.models.Account;
import com.zvezdomirov.accountsystem2.models.User;
import com.zvezdomirov.accountsystem2.services.AccountServiceImpl;
import com.zvezdomirov.accountsystem2.services.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@SpringBootApplication
@Component
public class ConsoleRunner implements CommandLineRunner {
    private UserServiceImpl userService;
    private AccountServiceImpl accountService;

    @Autowired
    public ConsoleRunner(UserServiceImpl userService,
                         AccountServiceImpl accountService) {
        this.userService = userService;
        this.accountService = accountService;
    }

    @Override
    public void run(String... args) throws Exception {
        User pesho = new User("pesho1", 20);
        Account account = new Account(new BigDecimal("25000"), pesho);
        pesho.getAccounts().add(account);
        userService.registerUser(pesho);

        accountService.withdrawMoney(new BigDecimal(20000), account.getId());
        accountService.transferMoney(new BigDecimal(20000), 1L);
    }
}
