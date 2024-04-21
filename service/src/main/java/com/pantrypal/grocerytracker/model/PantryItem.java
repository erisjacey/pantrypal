package com.pantrypal.grocerytracker.model;

import com.pantrypal.grocerytracker.constants.Constants;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
@Table(name = Constants.PANTRY_ITEM_TABLE_NAME)
public class PantryItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = Constants.GROCERY_ITEM_TABLE_ID_NAME)
    private GroceryItem groceryItem;

    private double quantityInStock;
    private LocalDate expirationDate;
    private String locationInPantry;
}
