package com.pantrypal.grocerytracker.dto;

import com.pantrypal.grocerytracker.model.unit.Unit;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
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
    @NotNull
    @PositiveOrZero
    private Double amount;

    /**
     * The unit of measurement for the amount.
     */
    @NotNull
    private Unit unit;
}
