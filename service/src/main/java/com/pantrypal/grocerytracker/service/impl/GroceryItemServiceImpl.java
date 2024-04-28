package com.pantrypal.grocerytracker.service.impl;

import com.pantrypal.grocerytracker.dto.GroceryItemDto;
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
    public List<GroceryItemDto> getAllGroceryItems() {
        List<GroceryItem> groceryItems = groceryItemRepository.findAll();

        // Map each grocery item to DTO and return
        return groceryItems.stream()
                .map(groceryItemMapper::mapToDto)
                .toList();
    }

    @Override
    public Optional<GroceryItemDto> getGroceryItemById(Long id) {
        Optional<GroceryItem> optionalGroceryItem = groceryItemRepository.findById(id);
        return optionalGroceryItem.map(groceryItemMapper::mapToDto);
    }

    @Override
    @Transactional
    public GroceryItemDto createGroceryItem(GroceryItemDto groceryItem) {
        // Check if product exists, create if not found
        Product product = productService.findOrCreateProduct(groceryItem.getName());

        // Map DTO to entity to save
        GroceryItem groceryItemToSave = groceryItemMapper.mapToEntity(groceryItem, product);

        // Save grocery item
        GroceryItem savedGroceryItem = groceryItemRepository.save(groceryItemToSave);

        // Save pantry item
        pantryItemService.addGroceryItemToPantry(savedGroceryItem);

        // Map saved entity to DTO and return
        return groceryItemMapper.mapToDto(savedGroceryItem);
    }

    @Override
    @Transactional
    public GroceryItemDto updateGroceryItem(GroceryItemDto updatedItem) {
        // Check if product exists, create if not found
        Product product = productService.findOrCreateProduct(updatedItem.getName());

        // Map DTO to entity (including ID) to save
        GroceryItem updatedItemToSave = groceryItemMapper.mapToEntityWithId(updatedItem, product);

        // Save grocery item
        GroceryItem savedUpdatedItem = groceryItemRepository.save(updatedItemToSave);

        // Map saved entity to DTO and return
        return groceryItemMapper.mapToDto(savedUpdatedItem);
    }

    @Override
    public void deleteGroceryItem(Long id) {
        groceryItemRepository.deleteById(id);
    }
}
