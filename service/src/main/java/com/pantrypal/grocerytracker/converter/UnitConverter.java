package com.pantrypal.grocerytracker.converter;

import com.pantrypal.grocerytracker.model.unit.Unit;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class UnitConverter implements AttributeConverter<Unit, String> {

    @Override
    public String convertToDatabaseColumn(Unit unit) {
        return unit.getUnitString();
    }

    @Override
    public Unit convertToEntityAttribute(String unitString) {
        return Unit.fromString(unitString);
    }
}
