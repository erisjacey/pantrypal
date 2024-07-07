package com.pantrypal.grocerytracker.dto;

import com.pantrypal.grocerytracker.model.enums.GroceryType;
import com.pantrypal.grocerytracker.model.unit.Gram;
import com.pantrypal.grocerytracker.model.unit.Unit;
import com.pantrypal.grocerytracker.util.TestModels;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PantryItemDtoTest {
    @Test
    @DisplayName("Test getters and setters")
    void gettersAndSetters() {
        // Arrange
        PantryItemDto pantryItemDto = new PantryItemDto();
        Long id = 1L;
        String name = TestModels.PRODUCT_NAME_BUTTER;
        double initialAmount = TestModels.AMOUNT_500;
        double currentAmount = TestModels.AMOUNT_300;
        Unit unit = new Gram();
        LocalDate purchasedDate = TestModels.DATE_DAY_AFTER_NOW;
        LocalDate expirationDate = TestModels.DATE_MONTH_AFTER_NOW;
        GroceryType groceryType = GroceryType.DAIRY;

        // Act
        pantryItemDto.setId(id);
        pantryItemDto.setName(name);
        pantryItemDto.setInitialAmount(initialAmount);
        pantryItemDto.setCurrentAmount(currentAmount);
        pantryItemDto.setUnit(unit);
        pantryItemDto.setPurchasedDate(purchasedDate);
        pantryItemDto.setExpirationDate(expirationDate);
        pantryItemDto.setGroceryType(groceryType);

        // Assert
        assertEquals(id, pantryItemDto.getId());
        assertEquals(name, pantryItemDto.getName());
        assertEquals(initialAmount, pantryItemDto.getInitialAmount());
        assertEquals(currentAmount, pantryItemDto.getCurrentAmount());
        assertEquals(unit, pantryItemDto.getUnit());
        assertEquals(purchasedDate, pantryItemDto.getPurchasedDate());
        assertEquals(expirationDate, pantryItemDto.getExpirationDate());
        assertEquals(groceryType, pantryItemDto.getGroceryType());
    }
}
