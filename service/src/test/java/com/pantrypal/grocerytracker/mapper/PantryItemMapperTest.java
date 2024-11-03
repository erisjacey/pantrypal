package com.pantrypal.grocerytracker.mapper;

import com.pantrypal.grocerytracker.dto.PantryItemDto;
import com.pantrypal.grocerytracker.model.GroceryItem;
import com.pantrypal.grocerytracker.model.PantryItem;
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
class PantryItemMapperTest {
    private PantryItemMapper mapper;

    @Autowired
    private Validator validator;

    @BeforeEach
    void setUp() {
        this.mapper = new PantryItemMapper();
    }

    @Test
    @DisplayName("Test validation - null fields")
    public void testValidation_NullFields() {
        // Arrange: Create a DTO with null values
        PantryItemDto dto = TestModels.getButterPantryItemDto();
        dto.setInitialAmount(null);
        dto.setCurrentAmount(null);
        dto.setGroceryType(null);

        // Act: Validate the DTO using Spring's validator
        Set<ConstraintViolation<PantryItemDto>> violations = validator.validate(dto);

        // Assert: Expecting ConstraintViolationException
        assertEquals(3, violations.size(), "Expected validation violations but found none.");
    }

    @Test
    @DisplayName("Test map to entity")
    void mapToEntity() {
        // Assemble
        GroceryItem groceryItem = TestModels.getMilkGroceryItem();

        // Act
        PantryItem pantryItem = mapper.mapToEntity(groceryItem);

        // Assert
        assertNotNull(pantryItem);
        assertNotNull(pantryItem.getGroceryItem());
        assertEquals(groceryItem, pantryItem.getGroceryItem());
        assertEquals(groceryItem.getAmount(), pantryItem.getQuantityInStock());
    }

    @Test
    @DisplayName("Test map to entity with ID")
    void mapToEntityWithId() {
        // Assemble
        GroceryItem groceryItem = TestModels.getButterGroceryItem();
        Long pantryItemId = TestModels.ID_3;

        // Act
        PantryItem pantryItem = mapper.mapToEntityWithId(groceryItem, pantryItemId);

        // Assert
        assertNotNull(pantryItem);
        assertNotNull(pantryItem.getGroceryItem());
        assertEquals(pantryItemId, pantryItem.getId());
        assertEquals(groceryItem, pantryItem.getGroceryItem());
        assertEquals(groceryItem.getAmount(), pantryItem.getQuantityInStock());
    }

    @Test
    @DisplayName("Test map to DTO")
    void mapToDto() {
        // Assemble
        PantryItem pantryItem = TestModels.getMilkPantryItem();

        // Act
        PantryItemDto dto = mapper.mapToDto(pantryItem);

        // Assert
        assertNotNull(dto);
        assertEquals(pantryItem.getId(), dto.getId());
        assertEquals(pantryItem.getGroceryItem().getProduct().getName(), dto.getName());
        assertEquals(pantryItem.getQuantityInStock(), dto.getInitialAmount());
        assertEquals(pantryItem.getQuantityInStock(), dto.getCurrentAmount());
        assertEquals(pantryItem.getGroceryItem().getUnit(), dto.getUnit());
        assertEquals(pantryItem.getGroceryItem().getPurchasedDate(), dto.getPurchasedDate());
        assertEquals(pantryItem.getGroceryItem().getExpirationDate(), dto.getExpirationDate());
        assertEquals(pantryItem.getGroceryItem().getGroceryType(), dto.getGroceryType());
    }
}
