package com.pantrypal.grocerytracker.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pantrypal.grocerytracker.constants.Constants;
import com.pantrypal.grocerytracker.converter.UnitConverter;
import com.pantrypal.grocerytracker.model.unit.Unit;
import jakarta.persistence.Convert;
import lombok.Data;

import java.time.LocalDate;

/**
 * Data Transfer Object (DTO) representing a pantry item.
 */
@Data
public class PantryItemDto {
    /**
     * The unique identifier for the pantry item.
     */
    private Long id;

    /**
     * The name of the product associated with the pantry item.
     */
    private String name;

    /**
     * The initial amount of the grocery item added to the pantry.
     */
    private double initialAmount;

    /**
     * The current amount of the grocery item in the pantry.
     */
    private double currentAmount;

    /**
     * The unit of measurement for the amount.
     */
    @Convert(converter = UnitConverter.class)
    private Unit unit;

    /**
     * The date the grocery item was purchased.
     */
    @JsonFormat(pattern = Constants.DATE_FORMAT_YYYY_MM_DD)
    private LocalDate purchasedDate;

    /**
     * The expiration date of the grocery item.
     */
    @JsonFormat(pattern = Constants.DATE_FORMAT_YYYY_MM_DD)
    private LocalDate expirationDate;
}
