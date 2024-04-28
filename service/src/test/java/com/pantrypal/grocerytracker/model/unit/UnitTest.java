package com.pantrypal.grocerytracker.model.unit;

import com.pantrypal.grocerytracker.constants.Constants;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class UnitTest {
    @DisplayName("Get unit string with valid unit")
    @Test
    public void fromStringWithValidUnit() {
        assertEquals(Gram.class, Unit.fromString(Constants.UNIT_GRAM).getClass());
        assertEquals(Milliliter.class, Unit.fromString(Constants.UNIT_MILLILITER).getClass());
        assertEquals(Liter.class, Unit.fromString(Constants.UNIT_LITER).getClass());
    }

    @DisplayName("Get unit string with invalid unit")
    @Test
    public void fromStringWithInvalidUnit() {
        String invalidUnit = "invalid_unit";
        Throwable exception = assertThrows(
                IllegalArgumentException.class, () -> Unit.fromString("invalid_unit")
        );
        assertEquals(
                Constants.ILLEGAL_ARGUMENT_EXCEPTION_UNKNOWN_UNIT_STRING + invalidUnit,
                exception.getMessage()
        );
    }
}