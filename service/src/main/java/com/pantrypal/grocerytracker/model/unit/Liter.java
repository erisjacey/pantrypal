package com.pantrypal.grocerytracker.model.unit;

public class Liter extends Unit {
    @Override
    public double convertToBaseUnit(double amount) {
        // Assuming 1 liter is equivalent to 1000 grams for simplicity
        return amount * 1000; // Convert liter to grams (base unit)
    }

    // Add other methods specific to the Liter unit if needed
}
