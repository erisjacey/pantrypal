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

@Data
@Entity
@Table(name = Constants.GROCERY_ITEM_TABLE_NAME)
public class GroceryItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = Constants.PRODUCT_TABLE_ID_NAME, nullable = false)
    private Product product;

    private double amount;
    private Unit unit;
    private LocalDate purchasedDate;
    private LocalDate expirationDate;
}
