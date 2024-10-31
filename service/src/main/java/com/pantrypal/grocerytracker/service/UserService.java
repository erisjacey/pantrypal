package com.pantrypal.grocerytracker.service;

import com.pantrypal.grocerytracker.model.User;

import java.util.Optional;

public interface UserService {
    User registerUser(User user);

    Optional<User> findUserByUsername(String username);

    Optional<User> findUserByEmail(String email);
}
