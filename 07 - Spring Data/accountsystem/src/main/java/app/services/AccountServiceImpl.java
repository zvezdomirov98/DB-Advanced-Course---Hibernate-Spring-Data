package app.services;

import app.models.Account;
import app.repositories.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@Primary
public class AccountServiceImpl implements AccountService {

    private AccountRepository accountRepository;

    @Autowired
    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public void withdrawMoney(BigDecimal money, long id) {
        Account requestAcc = accountRepository.getById(id);
        if (requestAcc != null &&
                requestAcc.getUser() != null &&
                requestAcc.getBalance().compareTo(money) >= 0) {
            requestAcc.setBalance(
                    requestAcc.getBalance().subtract(money));
            accountRepository.save(requestAcc);
        }
    }

    @Override
    public void transferMoney(BigDecimal money, long id) {
        Account requestAcc = accountRepository.getById(id);
        if (requestAcc != null &&
                money.compareTo(new BigDecimal(0)) > 0) {
            requestAcc.setBalance(
                    requestAcc.getBalance().add(money));
            accountRepository.save(requestAcc);
        }
    }
}
