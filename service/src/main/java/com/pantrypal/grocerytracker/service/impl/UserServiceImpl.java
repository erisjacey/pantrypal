package com.pantrypal.grocerytracker.service.impl;

import com.pantrypal.grocerytracker.dto.auth.AuthRegisterRequest;
import com.pantrypal.grocerytracker.exception.custom.EmailAlreadyRegisteredException;
import com.pantrypal.grocerytracker.exception.custom.UsernameAlreadyExistsException;
import com.pantrypal.grocerytracker.mapper.UserMapper;
import com.pantrypal.grocerytracker.model.User;
import com.pantrypal.grocerytracker.repository.UserRepository;
import com.pantrypal.grocerytracker.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    public void registerUser(AuthRegisterRequest request, String encodedPassword) {
        // Check if the username or email already exists
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new UsernameAlreadyExistsException();
        }

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new EmailAlreadyRegisteredException();
        }

        // Map the request and encoded password to the entity to save
        User user = userMapper.mapToEntity(request, encodedPassword);

        // Save the user to the database
        userRepository.save(user);
    }

    @Override
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public Optional<User> getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
