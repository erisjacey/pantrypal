package com.pantrypal.grocerytracker.model.unit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LiterTest {
    private Liter liter;

    @BeforeEach
    void setUp() {
        this.liter = new Liter();
    }

    @Test
    @DisplayName("Test liter conversion to base unit")
    void convertToBaseUnit() {
        assertEquals(500.0, liter.convertToBaseUnit(0.5)); // 0.5 liters to grams
    }

    @Test
    @DisplayName("Test liter conversion from base unit")
    void convertFromBaseUnit() {
        assertEquals(0.5, liter.convertFromBaseUnit(500.0)); // 500 grams to liters
    }
}
