package com.pantrypal.grocerytracker.serialization;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.pantrypal.grocerytracker.constants.Constants;
import com.pantrypal.grocerytracker.model.unit.Gram;
import com.pantrypal.grocerytracker.model.unit.Liter;
import com.pantrypal.grocerytracker.model.unit.Milliliter;
import com.pantrypal.grocerytracker.model.unit.Unit;

import java.io.IOException;

public class UnitSerializer extends JsonSerializer<Unit> {
    @Override
    public void serialize(Unit unit, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        if (unit instanceof Gram) {
            jsonGenerator.writeString(Constants.UNIT_GRAM);
        } else if (unit instanceof Milliliter) {
            jsonGenerator.writeString(Constants.UNIT_MILLILITER);
        } else if (unit instanceof Liter) {
            jsonGenerator.writeString(Constants.UNIT_LITER);
        } else {
            throw new IOException(Constants.IO_EXCEPTION_UNKNOWN_UNIT_TYPE + unit.getClass().getSimpleName());
        }
    }
}
