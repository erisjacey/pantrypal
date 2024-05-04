package com.pantrypal.grocerytracker.service.impl;

import com.pantrypal.grocerytracker.model.Product;
import com.pantrypal.grocerytracker.repository.ProductRepository;
import com.pantrypal.grocerytracker.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Product findOrCreateProduct(String name) {
        Optional<Product> existingProduct = productRepository.findByName(name);
        return existingProduct.orElseGet(() -> productRepository.save(new Product(name)));
    }
}
