package com.pantrypal.grocerytracker.serialization;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.pantrypal.grocerytracker.model.unit.Unit;

import java.io.IOException;

public class UnitDeserializer extends JsonDeserializer<Unit> {
    @Override
    public Unit deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        String unitString = jsonParser.getText(); // Deserialize unit string
        return Unit.fromString(unitString);
    }
}
