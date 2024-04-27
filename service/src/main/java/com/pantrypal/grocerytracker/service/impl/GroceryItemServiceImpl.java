package com.pantrypal.grocerytracker.service.impl;

import com.pantrypal.grocerytracker.dto.BuyGroceryItemRequest;
import com.pantrypal.grocerytracker.mapper.GroceryItemMapper;
import com.pantrypal.grocerytracker.model.GroceryItem;
import com.pantrypal.grocerytracker.model.Product;
import com.pantrypal.grocerytracker.repository.GroceryItemRepository;
import com.pantrypal.grocerytracker.service.GroceryItemService;
import com.pantrypal.grocerytracker.service.PantryItemService;
import com.pantrypal.grocerytracker.service.ProductService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GroceryItemServiceImpl implements GroceryItemService {
    private final GroceryItemRepository groceryItemRepository;
    private final GroceryItemMapper groceryItemMapper;
    private final ProductService productService;
    private final PantryItemService pantryItemService;

    @Autowired
    public GroceryItemServiceImpl(
            GroceryItemRepository groceryItemRepository,
            GroceryItemMapper groceryItemMapper,
            ProductService productService,
            PantryItemService pantryItemService
    ) {
        this.groceryItemRepository = groceryItemRepository;
        this.groceryItemMapper = groceryItemMapper;
        this.productService = productService;
        this.pantryItemService = pantryItemService;
    }

    @Override
    public List<GroceryItem> getAllGroceryItems() {
        return groceryItemRepository.findAll();
    }

    @Override
    public Optional<GroceryItem> getGroceryItemById(Long id) {
        return groceryItemRepository.findById(id);
    }

    @Override
    @Transactional
    public GroceryItem createGroceryItem(BuyGroceryItemRequest request) {
        // Check if product exists, create if not found
        Product product = productService.findOrCreateProduct(request.getName());

        // Map DTO to grocery item entity
        GroceryItem groceryItem = groceryItemMapper.mapToEntity(request, product);

        // Save grocery item
        groceryItemRepository.save(groceryItem);

        // Save pantry item
        pantryItemService.addGroceryItemToPantry(groceryItem);

        // Return grocery item
        return groceryItem;
    }

    @Override
    public GroceryItem updateGroceryItem(GroceryItem updatedItem) {
        return groceryItemRepository.save(updatedItem);
    }

    @Override
    public void deleteGroceryItem(Long id) {
        groceryItemRepository.deleteById(id);
    }
}
