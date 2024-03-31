package com.pantrypal.grocerytracker.model.unit;

public class Gram extends Unit {
    @Override
    public double convertToBaseUnit(double amount) {
        return amount; // Gram is already the base unit
    }

    // Other methods specific to Gram unit
}
