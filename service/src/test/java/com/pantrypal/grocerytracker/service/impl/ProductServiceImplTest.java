package com.pantrypal.grocerytracker.service.impl;

import com.pantrypal.grocerytracker.model.Product;
import com.pantrypal.grocerytracker.repository.ProductRepository;
import com.pantrypal.grocerytracker.service.ProductService;
import com.pantrypal.grocerytracker.util.TestModels;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ProductServiceImplTest {

    private ProductRepository productRepository;
    private ProductService productService;

    @BeforeEach
    void setUp() {
        productRepository = mock(ProductRepository.class);
        productService = new ProductServiceImpl(productRepository);
    }

    @Test
    @DisplayName("Test find or create product - product exists")
    void findOrCreateProduct_productExists() {
        // Arrange
        Product existingProduct = TestModels.getMilkProduct();
        String productName = existingProduct.getName();
        when(productRepository.findByName(productName)).thenReturn(Optional.of(existingProduct));

        // Act
        Product result = productService.findOrCreateProduct(productName);

        // Assert
        assertEquals(existingProduct, result);
        verify(productRepository).findByName(productName);
        verify(productRepository, never()).save(any());
    }

    @Test
    @DisplayName("Test find or create product - product does not exist")
    void findOrCreateProduct_productDoesNotExist() {
        // Arrange
        String productName = "Non-existing product";
        when(productRepository.findByName(productName)).thenReturn(Optional.empty());
        when(productRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        Product result = productService.findOrCreateProduct(productName);

        // Assert
        assertEquals(productName, result.getName());
        verify(productRepository).findByName(productName);
        verify(productRepository).save(any());
    }
}
