package com.pantrypal.grocerytracker.model.unit;

import com.pantrypal.grocerytracker.constants.Constants;

public class Liter extends Unit {
    @Override
    public String getUnitString() {
        return Constants.UNIT_LITER;
    }

    @Override
    public double convertToBaseUnit(double amount) {
        // Assuming 1 liter is equivalent to 1000 grams for simplicity
        return amount * 1000; // Convert liter to grams (base unit)
    }

    // Add other methods specific to the Liter unit if needed
}
