package com.pantrypal.grocerytracker.serialization;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.pantrypal.grocerytracker.model.unit.Unit;

import java.io.IOException;

/**
 * Custom JSON deserializer for the {@link Unit} class.
 * It deserializes a JSON string representing a unit into the corresponding {@link Unit} object.
 */
public class UnitDeserializer extends JsonDeserializer<Unit> {
    /**
     * Deserializes a JSON string into the corresponding {@link Unit} object.
     *
     * @param jsonParser             The JSON parser.
     * @param deserializationContext The deserialization context.
     * @return The {@link Unit} object.
     * @throws IOException If an I/O error occurs.
     */
    @Override
    public Unit deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        String unitString = jsonParser.getText(); // Deserialize unit string
        return Unit.fromString(unitString);
    }
}
