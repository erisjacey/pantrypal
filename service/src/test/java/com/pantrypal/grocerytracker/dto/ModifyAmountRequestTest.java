package com.pantrypal.grocerytracker.dto;

import com.pantrypal.grocerytracker.model.unit.Liter;
import com.pantrypal.grocerytracker.model.unit.Unit;
import com.pantrypal.grocerytracker.util.TestModels;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ModifyAmountRequestTest {
    @Test
    @DisplayName("Test getters and setters")
    void gettersAndSetters() {
        // Arrange
        double amount = TestModels.AMOUNT_2_POINT_5;
        Unit unit = new Liter();

        // Act
        ModifyAmountRequest modifyAmountRequest = new ModifyAmountRequest();
        modifyAmountRequest.setAmount(amount);
        modifyAmountRequest.setUnit(unit);

        // Assert
        assertEquals(amount, modifyAmountRequest.getAmount());
        assertEquals(unit, modifyAmountRequest.getUnit());
    }
}
