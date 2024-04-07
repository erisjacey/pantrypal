package com.pantrypal.grocerytracker.model.unit;

import com.pantrypal.grocerytracker.constants.Constants;

public class Milliliter extends Unit {
    @Override
    public String getUnitString() {
        return Constants.UNIT_MILLILITER;
    }

    @Override
    public double convertToBaseUnit(double amount) {
        // Assuming 1 milliliter is equivalent to 1 gram for simplicity
        return amount; // Milliliter converted to gram (base unit)
    }

    // Other methods specific to Milliliter unit
}
