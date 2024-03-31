package com.pantrypal.grocerytracker.serialization;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.pantrypal.grocerytracker.constants.Constants;
import com.pantrypal.grocerytracker.model.unit.Gram;
import com.pantrypal.grocerytracker.model.unit.Liter;
import com.pantrypal.grocerytracker.model.unit.Milliliter;
import com.pantrypal.grocerytracker.model.unit.Unit;

import java.io.IOException;

public class UnitDeserializer extends JsonDeserializer<Unit> {
    @Override
    public Unit deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        String className = jsonParser.getText(); // Deserialize class name
        try {
            return switch (className) {
                case Constants.UNIT_GRAM -> new Gram();
                case Constants.UNIT_MILLILITER -> new Milliliter();
                case Constants.UNIT_LITER -> new Liter();
                default -> throw new IOException(Constants.IO_EXCEPTION_UNKNOWN_UNIT_TYPE + className);
            };
        } catch (Exception e) {
            throw new IOException(Constants.IO_EXCEPTION_DESERIALIZING_UNIT, e);
        }
    }
}
