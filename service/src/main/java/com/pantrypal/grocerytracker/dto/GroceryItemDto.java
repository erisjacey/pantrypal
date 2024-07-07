package com.pantrypal.grocerytracker.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pantrypal.grocerytracker.constants.Constants;
import com.pantrypal.grocerytracker.converter.UnitConverter;
import com.pantrypal.grocerytracker.model.enums.GroceryType;
import com.pantrypal.grocerytracker.model.unit.Unit;
import jakarta.persistence.Convert;
import lombok.Data;

import java.time.LocalDate;

/**
 * Data Transfer Object (DTO) representing a grocery item.
 */
@Data
public class GroceryItemDto {
    /**
     * The unique identifier for the grocery item.
     */
    private Long id;

    /**
     * The name of the product associated with the grocery item.
     */
    private String name;

    /**
     * The amount of the grocery item.
     */
    private double amount;

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

    /**
     * The type of the grocery item.
     */
    private GroceryType groceryType;
}
