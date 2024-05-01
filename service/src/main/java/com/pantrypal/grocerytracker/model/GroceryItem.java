package com.pantrypal.grocerytracker.model;

import com.pantrypal.grocerytracker.constants.Constants;
import com.pantrypal.grocerytracker.model.unit.Unit;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

import java.time.LocalDate;

/**
 * Represents a grocery item entity at the point of purchase.
 */
@Data
@Entity
@Table(name = Constants.GROCERY_ITEM_TABLE_NAME)
public class GroceryItem {
    /**
     * The unique identifier for the grocery item.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The product associated with the grocery item.
     */
    @ManyToOne
    @JoinColumn(name = Constants.PRODUCT_TABLE_ID_NAME, nullable = false)
    private Product product;

    /**
     * The amount of the grocery item.
     */
    private double amount;

    /**
     * The unit of measurement for the amount.
     */
    private Unit unit;

    /**
     * The date the grocery item was purchased.
     */
    private LocalDate purchasedDate;

    /**
     * The expiration date of the grocery item.
     */
    private LocalDate expirationDate;
}
