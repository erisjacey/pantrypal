package com.pantrypal.grocerytracker.model;

import com.pantrypal.grocerytracker.constants.Constants;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

/**
 * Represents a product entity.
 */
@Data
@RequiredArgsConstructor
@NoArgsConstructor(force = true)
@Entity
@Table(name = Constants.PRODUCT_TABLE_NAME)
public class Product {
    /**
     * The unique identifier for the product.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The name of the product.
     */
    @NonNull
    @Column(unique = true)
    private final String name;
}
