package com.pantrypal.grocerytracker.dto;

import com.pantrypal.grocerytracker.model.enums.GroceryType;
import com.pantrypal.grocerytracker.model.unit.Milliliter;
import com.pantrypal.grocerytracker.model.unit.Unit;
import com.pantrypal.grocerytracker.util.TestModels;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GroceryItemDtoTest {
    @Test
    @DisplayName("Test getters and setters")
    void gettersAndSetters() {
        // Arrange
        GroceryItemDto groceryItemDto = new GroceryItemDto();
        Long id = 1L;
        String name = TestModels.PRODUCT_NAME_BUTTER;
        double amount = TestModels.AMOUNT_500;
        Unit unit = new Milliliter();
        LocalDate purchasedDate = TestModels.DATE_NOW;
        LocalDate expirationDate = TestModels.DATE_YEAR_AFTER_NOW;
        GroceryType groceryType = GroceryType.DAIRY;

        // Act
        groceryItemDto.setId(id);
        groceryItemDto.setName(name);
        groceryItemDto.setAmount(amount);
        groceryItemDto.setUnit(unit);
        groceryItemDto.setPurchasedDate(purchasedDate);
        groceryItemDto.setExpirationDate(expirationDate);
        groceryItemDto.setGroceryType(groceryType);

        // Assert
        assertEquals(id, groceryItemDto.getId());
        assertEquals(name, groceryItemDto.getName());
        assertEquals(amount, groceryItemDto.getAmount());
        assertEquals(unit, groceryItemDto.getUnit());
        assertEquals(purchasedDate, groceryItemDto.getPurchasedDate());
        assertEquals(expirationDate, groceryItemDto.getExpirationDate());
        assertEquals(groceryType, groceryItemDto.getGroceryType());
    }
}
