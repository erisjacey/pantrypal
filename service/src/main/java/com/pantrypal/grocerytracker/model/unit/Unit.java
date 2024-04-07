package com.pantrypal.grocerytracker.model.unit;

import com.pantrypal.grocerytracker.constants.Constants;

public abstract class Unit {
    // Static method to create unit instance from unit string
    public static Unit fromString(String unitString) {
        return switch (unitString) {
            case Constants.UNIT_GRAM -> new Gram();
            case Constants.UNIT_MILLILITER -> new Milliliter();
            case Constants.UNIT_LITER -> new Liter();
            default -> throw new IllegalArgumentException(
                    Constants.ILLEGAL_ARGUMENT_EXCEPTION_UNKNOWN_UNIT_STRING + unitString);
        };
    }

    // Method to get the string representation of the unit
    public abstract String getUnitString();

    // Abstract method to convert amount to base unit
    public abstract double convertToBaseUnit(double amount);

    // Abstract method to convert amount from base unit to specific unit
    public abstract double convertFromBaseUnit(double amount);
}
