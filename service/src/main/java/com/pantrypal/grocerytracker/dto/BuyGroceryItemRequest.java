package com.pantrypal.grocerytracker.dto;

import com.pantrypal.grocerytracker.converter.UnitConverter;
import com.pantrypal.grocerytracker.model.unit.Unit;
import jakarta.persistence.Convert;
import lombok.Data;

import java.time.LocalDate;

@Data
public class BuyGroceryItemRequest {
    private String name;
    private double amount;
    @Convert(converter = UnitConverter.class)
    private Unit unit;
    private LocalDate purchasedDate;
    private LocalDate expirationDate;
}
