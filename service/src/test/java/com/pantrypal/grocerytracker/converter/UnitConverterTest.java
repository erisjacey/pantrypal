package com.pantrypal.grocerytracker.converter;

import com.pantrypal.grocerytracker.constants.Constants;
import com.pantrypal.grocerytracker.model.unit.Gram;
import com.pantrypal.grocerytracker.model.unit.Liter;
import com.pantrypal.grocerytracker.model.unit.Milliliter;
import com.pantrypal.grocerytracker.model.unit.Unit;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class UnitConverterTest {
    @Test
    @DisplayName("Test convert to database column")
    void convertToDatabaseColumn() {
        // Arrange
        UnitConverter converter = new UnitConverter();
        Unit gram = new Gram();
        Unit liter = new Liter();
        Unit milliliter = new Milliliter();

        // Act
        String gramUnitString = converter.convertToDatabaseColumn(gram);
        String literUnitString = converter.convertToDatabaseColumn(liter);
        String milliliterUnitString = converter.convertToDatabaseColumn(milliliter);

        // Assert
        assertNotNull(gramUnitString);
        assertNotNull(literUnitString);
        assertNotNull(milliliterUnitString);
        assertEquals(Constants.UNIT_GRAM, gramUnitString);
        assertEquals(Constants.UNIT_LITER, literUnitString);
        assertEquals(Constants.UNIT_MILLILITER, milliliterUnitString);
    }

    @Test
    @DisplayName("Test convert to entity attribute")
    void convertToEntityAttribute() {
        // Arrange
        UnitConverter converter = new UnitConverter();
        String gramUnitString = Constants.UNIT_GRAM;
        String literUnitString = Constants.UNIT_LITER;
        String milliliterUnitString = Constants.UNIT_MILLILITER;

        // Act
        Unit gram = converter.convertToEntityAttribute(gramUnitString);
        Unit liter = converter.convertToEntityAttribute(literUnitString);
        Unit milliliter = converter.convertToEntityAttribute(milliliterUnitString);

        // Assert
        assertNotNull(gram);
        assertNotNull(liter);
        assertNotNull(milliliter);
        assertEquals(Gram.class, gram.getClass());
        assertEquals(Liter.class, liter.getClass());
        assertEquals(Milliliter.class, milliliter.getClass());
    }
}
