package com.pantrypal.grocerytracker.service;

import com.pantrypal.grocerytracker.dto.auth.AuthRegisterRequest;
import com.pantrypal.grocerytracker.model.User;

import java.util.Optional;

public interface UserService {
    void registerUser(AuthRegisterRequest request, String encodedPassword);

    Optional<User> getUserById(Long id);

    Optional<User> getUserByUsername(String username);

    Optional<User> getUserByEmail(String email);
}
