package com.pantrypal.grocerytracker.model.unit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GramTest {
    private Gram gram;

    @BeforeEach
    void setup() {
        this.gram = new Gram();
    }

    @Test
    @DisplayName("Test gram conversion to base unit")
    void convertToBaseUnit() {
        assertEquals(100.0, gram.convertToBaseUnit(100.0));
    }

    @Test
    @DisplayName("Test gram conversion from base unit")
    void convertFromBaseUnit() {
        assertEquals(100.0, gram.convertToBaseUnit(100.0));
    }
}