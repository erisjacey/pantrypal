package com.pantrypal.grocerytracker.dto;

import com.pantrypal.grocerytracker.model.unit.Unit;
import lombok.Getter;
import lombok.Setter;

/**
 * Data Transfer Object (DTO) representing a request to modify the amount of a pantry item.
 */
@Setter
@Getter
public class ModifyAmountRequest {
    /**
     * The amount to modify.
     */
    private double amount;

    /**
     * The unit of measurement for the amount.
     */
    private Unit unit;
}
