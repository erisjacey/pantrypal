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

    @DisplayName("Milliliter conversion to base unit")
    @Test
    void convertToBaseUnit() {
        assertEquals(100.0, milliliter.convertToBaseUnit(100.0)); // 100 milliliters to grams
    }

    @DisplayName("Milliliter conversion from base unit")
    @Test
    void convertFromBaseUnit() {
        assertEquals(100.0, milliliter.convertToBaseUnit(100.0)); // 100 grams to milliliters
    }
}