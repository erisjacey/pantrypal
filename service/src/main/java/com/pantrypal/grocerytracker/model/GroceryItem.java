package com.pantrypal.grocerytracker.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.pantrypal.grocerytracker.constants.Constants;
import com.pantrypal.grocerytracker.converter.UnitConverter;
import com.pantrypal.grocerytracker.model.unit.Unit;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = Constants.GROCERY_ITEM_TABLE_NAME)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = Constants.PROPERTY_ID)
public class GroceryItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private double amount;

    @Convert(converter = UnitConverter.class)
    private Unit unit;
}
