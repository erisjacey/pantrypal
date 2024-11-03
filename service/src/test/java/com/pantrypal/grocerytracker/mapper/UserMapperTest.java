package com.pantrypal.grocerytracker.mapper;

import com.pantrypal.grocerytracker.dto.auth.AuthRegisterRequest;
import com.pantrypal.grocerytracker.model.User;
import com.pantrypal.grocerytracker.util.TestModels;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class UserMapperTest {
    private UserMapper mapper;

    @Autowired
    private Validator validator;

    @BeforeEach
    void setUp() {
        this.mapper = new UserMapper();
    }

    @Test
    @DisplayName("Test validation - null fields")
    public void testValidation_NullFields() {
        // Arrange: Create a DTO with null values
        AuthRegisterRequest request = TestModels.getAuthRegisterRequest();
        request.setUsername(null);
        request.setEmail(null);
        request.setPassword(null);

        // Act: Validate the DTO using Spring's validator
        Set<ConstraintViolation<AuthRegisterRequest>> violations = validator.validate(request);

        // Assert: Expecting ConstraintViolationException
        assertEquals(3, violations.size(), "Expected validation violations but found none.");
    }

    @Test
    @DisplayName("Test map to entity")
    void mapToEntity() {
        // Assemble
        AuthRegisterRequest request = TestModels.getAuthRegisterRequest();
        String encodedPassword = TestModels.USER_ENCODED_PASSWORD;

        // Act
        User user = mapper.mapToEntity(request, encodedPassword);

        // Assert
        assertNotNull(user);
        assertEquals(request.getUsername(), user.getUsername());
        assertEquals(request.getEmail(), user.getEmail());
        assertEquals(encodedPassword, user.getPassword());
    }
}