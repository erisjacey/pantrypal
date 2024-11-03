package com.pantrypal.grocerytracker.service.impl;

import com.pantrypal.grocerytracker.constants.Constants;
import com.pantrypal.grocerytracker.dto.auth.AuthRegisterRequest;
import com.pantrypal.grocerytracker.exception.custom.EmailAlreadyRegisteredException;
import com.pantrypal.grocerytracker.exception.custom.UsernameAlreadyExistsException;
import com.pantrypal.grocerytracker.mapper.UserMapper;
import com.pantrypal.grocerytracker.model.User;
import com.pantrypal.grocerytracker.repository.UserRepository;
import com.pantrypal.grocerytracker.service.UserService;
import com.pantrypal.grocerytracker.util.TestModels;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class UserServiceImplTest {
    private UserRepository userRepository;
    private UserMapper userMapper;
    private UserService userService;

    @BeforeEach
    void setUp() {
        userRepository = mock(UserRepository.class);
        userMapper = mock(UserMapper.class);
        userService = new UserServiceImpl(userRepository, userMapper);
    }

    @Test
    @DisplayName("Test register user - success")
    void registerUser_success() {
        // Arrange
        User user = TestModels.getUser();
        AuthRegisterRequest request = TestModels.getAuthRegisterRequest();
        String encodedPassword = TestModels.USER_ENCODED_PASSWORD;

        // Mock repository behavior
        when(userRepository.existsByUsername(request.getUsername())).thenReturn(false);
        when(userRepository.existsByEmail(request.getEmail())).thenReturn(false);

        // Mock mapper behavior
        when(userMapper.mapToEntity(request, encodedPassword)).thenReturn(user);

        // Act
        userService.registerUser(request, encodedPassword);

        // Assert
        verify(userRepository).save(user);
    }

    @Test
    @DisplayName("Test register user - username already exists")
    void registerUser_usernameAlreadyExists() {
        // Arrange
        AuthRegisterRequest request = TestModels.getAuthRegisterRequest();
        String encodedPassword = TestModels.USER_ENCODED_PASSWORD;

        // Mock repository behavior
        when(userRepository.existsByUsername(request.getUsername())).thenReturn(true);

        // Act
        Throwable exception = assertThrows(UsernameAlreadyExistsException.class, () ->
                userService.registerUser(request, encodedPassword)
        );

        assertEquals(Constants.ERROR_MESSAGE_USERNAME_TAKEN, exception.getMessage());
        verify(userRepository, never()).existsByEmail(anyString());
        verify(userMapper, never()).mapToEntity(any(), anyString());
        verify(userRepository, never()).save(any());
    }

    @Test
    @DisplayName("Test register user - email already registered")
    void registerUser_emailAlreadyRegistered() {
        // Arrange
        AuthRegisterRequest request = TestModels.getAuthRegisterRequest();
        String encodedPassword = TestModels.USER_ENCODED_PASSWORD;

        // Mock repository behavior
        when(userRepository.existsByUsername(request.getUsername())).thenReturn(false);
        when(userRepository.existsByEmail(request.getEmail())).thenReturn(true);

        // Act
        Throwable exception = assertThrows(EmailAlreadyRegisteredException.class, () ->
                userService.registerUser(request, encodedPassword)
        );

        assertEquals(Constants.ERROR_MESSAGE_EMAIL_REGISTERED, exception.getMessage());
        verify(userMapper, never()).mapToEntity(any(), anyString());
        verify(userRepository, never()).save(any());
    }

    @Test
    @DisplayName("Test get user by ID")
    void getUserById() {
        // Arrange
        User user = TestModels.getUser();
        long id = TestModels.ID_1;

        // Mock repository behavior
        when(userRepository.findById(id)).thenReturn(Optional.of(user));

        // Act
        Optional<User> result = userService.getUserById(id);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(user, result.get());
    }

    @Test
    @DisplayName("Test get user by username")
    void getUserByUsername() {
        // Arrange
        User user = TestModels.getUser();
        String username = TestModels.USER_USERNAME;

        // Mock repository behavior
        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));

        // Act
        Optional<User> result = userService.getUserByUsername(username);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(user, result.get());
    }

    @Test
    @DisplayName("Test get user by email")
    void getUserByEmail() {
        // Arrange
        User user = TestModels.getUser();
        String email = TestModels.USER_EMAIL;

        // Mock repository behavior
        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));

        // Act
        Optional<User> result = userService.getUserByEmail(email);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(user, result.get());
    }
}