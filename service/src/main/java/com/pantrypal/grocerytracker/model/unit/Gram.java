package com.pantrypal.grocerytracker.model.unit;

import com.pantrypal.grocerytracker.constants.Constants;

public class Gram extends Unit {
    @Override
    public String getUnitString() {
        return Constants.UNIT_GRAM;
    }

    @Override
    public double convertToBaseUnit(double amount) {
        return amount; // Gram is already the base unit
    }

    @Override
    public double convertFromBaseUnit(double amount) {
        return amount; // No conversion needed for gram
    }
}
