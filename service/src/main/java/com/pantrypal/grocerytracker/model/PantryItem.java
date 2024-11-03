package com.pantrypal.grocerytracker.model;

import com.pantrypal.grocerytracker.constants.Constants;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * Represents a pantry item entity in stock.
 */
@Data
@Entity
@Table(name = Constants.PANTRY_ITEM_TABLE_NAME)
public class PantryItem {
    /**
     * The unique identifier for the pantry item.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The user who owns the pantry item.
     */
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = Constants.USER_TABLE_ID_NAME, nullable = false)
    private User user;

    /**
     * The grocery item associated with the pantry item.
     */
    @OneToOne(optional = false)
    @JoinColumn(name = Constants.GROCERY_ITEM_TABLE_ID_NAME, nullable = false)
    private GroceryItem groceryItem;

    /**
     * The quantity of the item in stock in the pantry.
     */
    @NotNull
    private Double quantityInStock;
}
