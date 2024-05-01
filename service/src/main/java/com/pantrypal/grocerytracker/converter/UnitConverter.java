package com.pantrypal.grocerytracker.converter;

import com.pantrypal.grocerytracker.model.unit.Unit;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

/**
 * JPA AttributeConverter for converting {@link Unit} objects to and from their corresponding string representation
 * when storing/retrieving them from the database.
 */
@Converter(autoApply = true)
public class UnitConverter implements AttributeConverter<Unit, String> {
    /**
     * Converts a {@link Unit} object to its string representation for database storage.
     *
     * @param unit The {@link Unit} object to convert.
     * @return The string representation of the {@link Unit} object.
     */
    @Override
    public String convertToDatabaseColumn(Unit unit) {
        return unit.getUnitString();
    }

    /**
     * Converts a string representation from the database to a {@link Unit} object.
     *
     * @param unitString The string representation of the {@link Unit} object.
     * @return The {@link Unit} object.
     */
    @Override
    public Unit convertToEntityAttribute(String unitString) {
        return Unit.fromString(unitString);
    }
}
