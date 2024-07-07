package com.pantrypal.grocerytracker.mapper;

import com.pantrypal.grocerytracker.dto.GroceryItemDto;
import com.pantrypal.grocerytracker.model.GroceryItem;
import com.pantrypal.grocerytracker.model.Product;
import com.pantrypal.grocerytracker.util.TestModels;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

class GroceryItemMapperTest {
    private GroceryItemMapper mapper;

    @BeforeEach
    void setUp() {
        this.mapper = new GroceryItemMapper();
    }

    @Test
    @DisplayName("Test map to entity")
    void mapToEntity() {
        // Assemble
        GroceryItemDto dto = TestModels.getButterGroceryItemDto();
        Product product = TestModels.getButterProduct();

        // Act
        GroceryItem groceryItem = mapper.mapToEntity(dto, product);

        // Assert
        assertNotNull(groceryItem);
        assertNotNull(groceryItem.getProduct());
        assertNull(groceryItem.getId());
        assertEquals(dto.getName(), groceryItem.getProduct().getName());
        assertEquals(dto.getAmount(), groceryItem.getAmount());
        assertEquals(dto.getUnit(), groceryItem.getUnit());
        assertEquals(dto.getPurchasedDate(), groceryItem.getPurchasedDate());
        assertEquals(dto.getExpirationDate(), groceryItem.getExpirationDate());
        assertEquals(dto.getGroceryType(), groceryItem.getGroceryType());
        assertEquals(product, groceryItem.getProduct());
    }

    @Test
    @DisplayName("Test map to entity with ID")
    void mapToEntityWithId() {
        // Assemble
        GroceryItemDto dto = TestModels.getMilkGroceryItemDto();
        Product product = TestModels.getMilkProduct();

        // Act
        GroceryItem groceryItem = mapper.mapToEntityWithId(dto, product);

        // Assert
        assertNotNull(groceryItem);
        assertNotNull(groceryItem.getProduct());
        assertEquals(dto.getId(), groceryItem.getId());
        assertEquals(dto.getName(), groceryItem.getProduct().getName());
        assertEquals(dto.getAmount(), groceryItem.getAmount());
        assertEquals(dto.getUnit(), groceryItem.getUnit());
        assertEquals(dto.getPurchasedDate(), groceryItem.getPurchasedDate());
        assertEquals(dto.getExpirationDate(), groceryItem.getExpirationDate());
        assertEquals(dto.getGroceryType(), groceryItem.getGroceryType());
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
