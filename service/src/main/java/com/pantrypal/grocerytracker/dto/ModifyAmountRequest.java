package com.pantrypal.grocerytracker.dto;

import com.pantrypal.grocerytracker.model.unit.Unit;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ModifyAmountRequest {
    private double amount;
    private Unit unit;
}
