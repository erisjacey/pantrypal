package com.pantrypal.grocerytracker.dto;

import com.pantrypal.grocerytracker.model.unit.Milliliter;
import com.pantrypal.grocerytracker.model.unit.Unit;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GroceryItemDtoTest {
    @Test
    @DisplayName("Test getters and setters")
    public void gettersAndSetters() {
        // Arrange
        GroceryItemDto groceryItemDto = new GroceryItemDto();
        Long id = 1L;
        String name = "Test grocery item";
        double amount = 200.0;
        Unit unit = new Milliliter();
        LocalDate purchasedDate = LocalDate.now();
        LocalDate expirationDate = LocalDate.MAX;

        // Act
        groceryItemDto.setId(id);
        groceryItemDto.setName(name);
        groceryItemDto.setAmount(amount);
        groceryItemDto.setUnit(unit);
        groceryItemDto.setPurchasedDate(purchasedDate);
        groceryItemDto.setExpirationDate(expirationDate);

        // Assert
        assertEquals(id, groceryItemDto.getId());
        assertEquals(name, groceryItemDto.getName());
        assertEquals(amount, groceryItemDto.getAmount());
        assertEquals(unit, groceryItemDto.getUnit());
        assertEquals(purchasedDate, groceryItemDto.getPurchasedDate());
        assertEquals(expirationDate, groceryItemDto.getExpirationDate());
    }
}