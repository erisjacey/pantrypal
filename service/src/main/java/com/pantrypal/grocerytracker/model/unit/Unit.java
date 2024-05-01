package com.pantrypal.grocerytracker.model.unit;

import com.pantrypal.grocerytracker.constants.Constants;

import java.util.Objects;

/**
 * Represents a unit of measurement abstract class.
 * Subclasses of this class define specific units, such as {@code Gram}, {@code Milliliter}, or {@code Liter}.
 */
public abstract class Unit {
    /**
     * Creates a unit instance based on the provided unit string.
     *
     * @param unitString The string representation of the unit.
     * @return An instance of the specific unit corresponding to the provided unit string.
     * @throws IllegalArgumentException If the provided unit string is not recognized.
     */
    public static Unit fromString(String unitString) {
        return switch (unitString) {
            case Constants.UNIT_GRAM -> new Gram();
            case Constants.UNIT_MILLILITER -> new Milliliter();
            case Constants.UNIT_LITER -> new Liter();
            default -> throw new IllegalArgumentException(
                    Constants.ILLEGAL_ARGUMENT_EXCEPTION_UNKNOWN_UNIT_STRING + unitString);
        };
    }

    /**
     * Gets the string representation of the unit.
     *
     * @return The string representation of the unit.
     */
    public abstract String getUnitString();

    /**
     * Converts the given amount to the base unit (grams) of the specific unit.
     *
     * @param amount The amount to convert.
     * @return The converted amount in the base unit.
     */
    public abstract double convertToBaseUnit(double amount);

    /**
     * Converts the given amount from the base unit (grams) to the specific unit.
     *
     * @param amount The amount in the base unit to convert.
     * @return The converted amount in the specific unit.
     */
    public abstract double convertFromBaseUnit(double amount);

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Unit unit = (Unit) o;
        // Compare the content of Unit objects here
        return Objects.equals(getUnitString(), unit.getUnitString());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUnitString());
    }

    @Override
    public String toString() {
        return getUnitString();
    }
}
