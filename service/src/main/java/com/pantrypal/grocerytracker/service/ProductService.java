package com.pantrypal.grocerytracker.service;

import com.pantrypal.grocerytracker.model.Product;

public interface ProductService {
    Product findOrCreateProduct(String name);
}
