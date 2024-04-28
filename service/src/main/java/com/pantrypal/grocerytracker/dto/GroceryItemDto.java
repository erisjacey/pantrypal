package com.pantrypal.grocerytracker.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pantrypal.grocerytracker.constants.Constants;
import com.pantrypal.grocerytracker.converter.UnitConverter;
import com.pantrypal.grocerytracker.model.unit.Unit;
import jakarta.persistence.Convert;
import lombok.Data;

import java.time.LocalDate;

@Data
public class GroceryItemDto {
    private Long id;
    private String name;
    private double amount;

    @Convert(converter = UnitConverter.class)
    private Unit unit;

    @JsonFormat(pattern = Constants.DATE_FORMAT_YYYY_MM_DD)
    private LocalDate purchasedDate;

    @JsonFormat(pattern = Constants.DATE_FORMAT_YYYY_MM_DD)
    private LocalDate expirationDate;
}
