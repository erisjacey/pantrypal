package com.pantrypal.grocerytracker.serialization;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.pantrypal.grocerytracker.model.unit.Unit;

import java.io.IOException;

/**
 * Custom JSON serializer for the {@link Unit} class.
 * It serializes a {@link Unit} object into its string representation.
 */
public class UnitSerializer extends JsonSerializer<Unit> {
    /**
     * Serializes a {@link Unit} object into its string representation.
     *
     * @param unit               The {@link Unit} object to serialize.
     * @param jsonGenerator      The JSON generator.
     * @param serializerProvider The serializer provider.
     * @throws IOException If an I/O error occurs.
     */
    @Override
    public void serialize(Unit unit, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeString(unit.getUnitString());
    }
}
