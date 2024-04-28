package com.pantrypal.grocerytracker.serialization;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.pantrypal.grocerytracker.constants.Constants;
import com.pantrypal.grocerytracker.model.unit.Gram;
import com.pantrypal.grocerytracker.model.unit.Liter;
import com.pantrypal.grocerytracker.model.unit.Milliliter;
import com.pantrypal.grocerytracker.model.unit.Unit;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class UnitDeserializerTest {
    private UnitDeserializer deserializer;
    private JsonParser jsonParser;
    private DeserializationContext deserializationContext;

    @BeforeEach
    void setUp() {
        this.deserializer = new UnitDeserializer();
        this.jsonParser = mock(JsonParser.class);
        this.deserializationContext = mock(DeserializationContext.class);
    }

    @Test
    @DisplayName("Test deserialize for Gram")
    void deserialize_Gram() throws IOException {
        // Arrange
        String unitString = Constants.UNIT_GRAM;
        when(jsonParser.getText()).thenReturn(unitString);

        // Act
        Unit unit = deserializer.deserialize(jsonParser, deserializationContext);

        // Assert
        assertEquals(Gram.class, unit.getClass());
        assertEquals(unitString, unit.getUnitString());
    }

    @Test
    @DisplayName("Test deserialize for Liter")
    void deserialize_Liter() throws IOException {
        // Arrange
        String unitString = Constants.UNIT_LITER;
        when(jsonParser.getText()).thenReturn(unitString);

        // Act
        Unit unit = deserializer.deserialize(jsonParser, deserializationContext);

        // Assert
        assertEquals(Liter.class, unit.getClass());
        assertEquals(unitString, unit.getUnitString());
    }

    @Test
    @DisplayName("Test deserialize for Milliliter")
    void deserialize_Milliliter() throws IOException {
        // Arrange
        String unitString = Constants.UNIT_MILLILITER;
        when(jsonParser.getText()).thenReturn(unitString);

        // Act
        Unit unit = deserializer.deserialize(jsonParser, deserializationContext);

        // Assert
        assertEquals(Milliliter.class, unit.getClass());
        assertEquals(unitString, unit.getUnitString());
    }
}
