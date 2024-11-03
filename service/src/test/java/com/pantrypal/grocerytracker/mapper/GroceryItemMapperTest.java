package com.pantrypal.grocerytracker.mapper;

import com.pantrypal.grocerytracker.dto.GroceryItemDto;
import com.pantrypal.grocerytracker.model.GroceryItem;
import com.pantrypal.grocerytracker.model.Product;
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
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
class GroceryItemMapperTest {
    private GroceryItemMapper mapper;

    @BeforeEach
    void setUp() {
        this.mapper = new GroceryItemMapper();
    }

    @Autowired
    private Validator validator;

    @Test
    public void testValidation_NullFields() {
        // Arrange: Create a DTO with null values
        GroceryItemDto dto = TestModels.getButterGroceryItemDto();
        dto.setAmount(null);
        dto.setGroceryType(null);

        // Act: Validate the DTO using Spring's validator
        Set<ConstraintViolation<GroceryItemDto>> violations = validator.validate(dto);

        // Assert: Expecting ConstraintViolationException
        assertEquals(2, violations.size(), "Expected validation violations but found none.");
    }

    @Test
    @DisplayName("Test map to entity")
    void mapToEntity() {
        // Assemble
        GroceryItemDto dto = TestModels.getButterGroceryItemDto();
        User user = TestModels.getUser();
        Product product = TestModels.getButterProduct();

        // Act
        GroceryItem groceryItem = mapper.mapToEntity(dto, user, product);

        // Assert
        assertNotNull(groceryItem);
        assertNotNull(groceryItem.getUser());
        assertNotNull(groceryItem.getProduct());
        assertNull(groceryItem.getId());
        assertEquals(dto.getName(), groceryItem.getProduct().getName());
        assertEquals(dto.getAmount(), groceryItem.getAmount());
        assertEquals(dto.getUnit(), groceryItem.getUnit());
        assertEquals(dto.getPurchasedDate(), groceryItem.getPurchasedDate());
        assertEquals(dto.getExpirationDate(), groceryItem.getExpirationDate());
        assertEquals(dto.getGroceryType(), groceryItem.getGroceryType());
        assertEquals(user, groceryItem.getUser());
        assertEquals(product, groceryItem.getProduct());
    }

    @Test
    @DisplayName("Test map to entity with ID")
    void mapToEntityWithId() {
        // Assemble
        GroceryItemDto dto = TestModels.getMilkGroceryItemDto();
        User user = TestModels.getUser();
        Product product = TestModels.getMilkProduct();

        // Act
        GroceryItem groceryItem = mapper.mapToEntityWithId(dto, user, product);

        // Assert
        assertNotNull(groceryItem);
        assertNotNull(groceryItem.getUser());
        assertNotNull(groceryItem.getProduct());
        assertEquals(dto.getId(), groceryItem.getId());
        assertEquals(dto.getName(), groceryItem.getProduct().getName());
        assertEquals(dto.getAmount(), groceryItem.getAmount());
        assertEquals(dto.getUnit(), groceryItem.getUnit());
        assertEquals(dto.getPurchasedDate(), groceryItem.getPurchasedDate());
        assertEquals(dto.getExpirationDate(), groceryItem.getExpirationDate());
        assertEquals(dto.getGroceryType(), groceryItem.getGroceryType());
        assertEquals(user, groceryItem.getUser());
        assertEquals(product, groceryItem.getProduct());
    }

    @Test
    @DisplayName("Test map to DTO")
    void mapToDto() {
        // Assemble
        GroceryItem groceryItem = TestModels.getButterGroceryItem();

        // Act
        GroceryItemDto dto = mapper.mapToDto(groceryItem);

        // Assert
        assertNotNull(dto);
        assertEquals(groceryItem.getId(), dto.getId());
        assertEquals(groceryItem.getProduct().getName(), dto.getName());
        assertEquals(groceryItem.getAmount(), dto.getAmount());
        assertEquals(groceryItem.getPurchasedDate(), dto.getPurchasedDate());
        assertEquals(groceryItem.getExpirationDate(), dto.getExpirationDate());
        assertEquals(groceryItem.getGroceryType(), dto.getGroceryType());
    }
}
