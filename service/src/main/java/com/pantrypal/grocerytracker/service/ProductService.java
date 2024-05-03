package com.pantrypal.grocerytracker.service;

import com.pantrypal.grocerytracker.model.Product;

/**
 * Service interface for managing products.
 * Provides a method to find or create a product by name.
 */
public interface ProductService {
    /**
     * Finds an existing product by name or creates a new product if not found.
     *
     * @param name The name of the product to find or create.
     * @return The {@link Product} found or created.
     */
    Product findOrCreateProduct(String name);
}
