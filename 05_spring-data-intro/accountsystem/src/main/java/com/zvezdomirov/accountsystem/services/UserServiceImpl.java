package com.zvezdomirov.accountsystem2.services;

import com.zvezdomirov.accountsystem2.models.User;
import com.zvezdomirov.accountsystem2.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void registerUser(User user) {
        if (!userRepository.existsById(user.getId())) {
            userRepository.save(user);
        }
    }
}
