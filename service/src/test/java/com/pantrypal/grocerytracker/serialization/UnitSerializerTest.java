package com.pantrypal.grocerytracker.serialization;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.pantrypal.grocerytracker.constants.Constants;
import com.pantrypal.grocerytracker.model.unit.Gram;
import com.pantrypal.grocerytracker.model.unit.Liter;
import com.pantrypal.grocerytracker.model.unit.Milliliter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class UnitSerializerTest {
    private UnitSerializer serializer;
    private JsonGenerator jsonGenerator;
    private SerializerProvider serializerProvider;

    @BeforeEach
    void setUp() {
        this.serializer = new UnitSerializer();
        this.jsonGenerator = mock(JsonGenerator.class);
        this.serializerProvider = mock(SerializerProvider.class);
    }

    @Test
    @DisplayName("Test serialize for Gram")
    void serialize_Gram() throws IOException {
        // Arrange
        Gram gram = new Gram();
        String expectedUnitString = Constants.UNIT_GRAM;

        // Act
        serializer.serialize(gram, jsonGenerator, serializerProvider);

        // Assert
        verify(jsonGenerator).writeString(expectedUnitString);
    }

    @Test
    @DisplayName("Test serialize for Liter")
    void serialize_Liter() throws IOException {
        // Arrange
        Liter liter = new Liter();
        String expectedUnitString = Constants.UNIT_LITER;

        // Act
        serializer.serialize(liter, jsonGenerator, serializerProvider);

        // Assert
        verify(jsonGenerator).writeString(expectedUnitString);
    }

    @Test
    @DisplayName("Test serialize for Milliliter")
    void serialize_Milliliter() throws IOException {
        // Arrange
        Milliliter milliliter = new Milliliter();
        String expectedUnitString = Constants.UNIT_MILLILITER;

        // Act
        serializer.serialize(milliliter, jsonGenerator, serializerProvider);

        // Assert
        verify(jsonGenerator).writeString(expectedUnitString);
    }
}
