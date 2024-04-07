package com.pantrypal.grocerytracker.serialization;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.pantrypal.grocerytracker.model.unit.Unit;

import java.io.IOException;

public class UnitSerializer extends JsonSerializer<Unit> {
    @Override
    public void serialize(Unit unit, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeString(unit.getUnitString());
    }
}
