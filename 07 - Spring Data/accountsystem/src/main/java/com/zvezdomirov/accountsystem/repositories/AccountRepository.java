package com.zvezdomirov.accountsystem2.repositories;

import com.zvezdomirov.accountsystem2.models.Account;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.math.BigDecimal;

@Repository
public interface AccountRepository extends CrudRepository<Account, Long> {
    Account getByBalance(BigDecimal balance);

    Account getById(long id);
}
