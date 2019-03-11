package com.zvezdomirov.accountsystem2.repositories;

import com.zvezdomirov.accountsystem2.models.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    User getByAge(int age);
    User getByUsername(String username);
}
