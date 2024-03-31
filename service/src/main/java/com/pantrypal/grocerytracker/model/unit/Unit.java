package com.pantrypal.grocerytracker.model.unit;

import jakarta.persistence.Embeddable;

@Embeddable
public abstract class Unit {
    // Abstract method to convert amount to base unit
    public abstract double convertToBaseUnit(double amount);
}
