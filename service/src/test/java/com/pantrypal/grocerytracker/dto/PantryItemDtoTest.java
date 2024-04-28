package com.pantrypal.grocerytracker.dto;

import com.pantrypal.grocerytracker.model.unit.Gram;
import com.pantrypal.grocerytracker.model.unit.Unit;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PantryItemDtoTest {
    @Test
    @DisplayName("Test getters and setters")
    public void gettersAndSetters() {
        // Arrange
        PantryItemDto pantryItemDto = new PantryItemDto();
        Long id = 1L;
        String name = "Test pantry item";
        double initialAmount = 500.0;
        double currentAmount = 300.0;
        Unit unit = new Gram();
        LocalDate purchasedDate = LocalDate.now();
        LocalDate expirationDate = LocalDate.MAX;

        // Act
        pantryItemDto.setId(id);
        pantryItemDto.setName(name);
        pantryItemDto.setInitialAmount(initialAmount);
        pantryItemDto.setCurrentAmount(currentAmount);
        pantryItemDto.setUnit(unit);
        pantryItemDto.setPurchasedDate(purchasedDate);
        pantryItemDto.setExpirationDate(expirationDate);

        // Assert
        assertEquals(id, pantryItemDto.getId());
        assertEquals(name, pantryItemDto.getName());
        assertEquals(initialAmount, pantryItemDto.getInitialAmount());
        assertEquals(currentAmount, pantryItemDto.getCurrentAmount());
        assertEquals(unit, pantryItemDto.getUnit());
        assertEquals(purchasedDate, pantryItemDto.getPurchasedDate());
        assertEquals(expirationDate, pantryItemDto.getExpirationDate());
    }
}