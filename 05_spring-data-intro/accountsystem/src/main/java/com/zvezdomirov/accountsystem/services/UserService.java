package com.zvezdomirov.accountsystem2.services;

import com.zvezdomirov.accountsystem2.models.User;
import org.springframework.stereotype.Service;

public interface UserService {
    void registerUser(User user);
}
