package com.pantrypal.grocerytracker.model.unit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MilliliterTest {
    private Milliliter milliliter;

    @BeforeEach
    void setup() {
        this.milliliter = new Milliliter();
    }

    @Test
    @DisplayName("Test milliliter conversion to base unit")
    void convertToBaseUnit() {
        assertEquals(100.0, milliliter.convertToBaseUnit(100.0)); // 100 milliliters to grams
    }

    @Test
    @DisplayName("Test milliliter conversion from base unit")
    void convertFromBaseUnit() {
        assertEquals(100.0, milliliter.convertToBaseUnit(100.0)); // 100 grams to milliliters
    }
}