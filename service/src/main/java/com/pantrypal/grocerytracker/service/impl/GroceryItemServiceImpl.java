package com.pantrypal.grocerytracker.service.impl;

import com.pantrypal.grocerytracker.dto.GroceryItemDto;
import com.pantrypal.grocerytracker.mapper.GroceryItemMapper;
import com.pantrypal.grocerytracker.model.GroceryItem;
import com.pantrypal.grocerytracker.model.Product;
import com.pantrypal.grocerytracker.model.User;
import com.pantrypal.grocerytracker.repository.GroceryItemRepository;
import com.pantrypal.grocerytracker.service.AuthService;
import com.pantrypal.grocerytracker.service.GroceryItemService;
import com.pantrypal.grocerytracker.service.PantryItemService;
import com.pantrypal.grocerytracker.service.ProductService;
import com.pantrypal.grocerytracker.service.UserService;
import jakarta.persistence.EntityNotFoundException;
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
    private final AuthService authService;
    private final UserService userService;

    @Autowired
    public GroceryItemServiceImpl(
            GroceryItemRepository groceryItemRepository,
            GroceryItemMapper groceryItemMapper,
            ProductService productService,
            PantryItemService pantryItemService,
            AuthService authService,
            UserService userService
    ) {
        this.groceryItemRepository = groceryItemRepository;
        this.groceryItemMapper = groceryItemMapper;
        this.productService = productService;
        this.pantryItemService = pantryItemService;
        this.authService = authService;
        this.userService = userService;
    }

    @Override
    public List<GroceryItemDto> getAllGroceryItems() {
        Long userId = authService.getCurrentUserId();
        List<GroceryItem> groceryItems = groceryItemRepository.findByUserId(userId);

        // Map each grocery item to DTO and return
        return groceryItems.stream()
                .map(groceryItemMapper::mapToDto)
                .toList();
    }

    @Override
    public Optional<GroceryItemDto> getGroceryItemById(Long id) {
        Long userId = authService.getCurrentUserId();
        Optional<GroceryItem> optionalGroceryItem = groceryItemRepository.findByIdAndUserId(id, userId);
        return optionalGroceryItem.map(groceryItemMapper::mapToDto);
    }

    @Override
    @Transactional
    public GroceryItemDto createGroceryItem(GroceryItemDto groceryItem) {
        // Check if product exists, create if not found
        Product product = productService.findOrCreateProduct(groceryItem.getName());

        // Get current user ID
        Long userId = authService.getCurrentUserId();

        // Fetch the full User entity from UserService
        User user = userService.findUserById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        // Map DTO to entity to save
        GroceryItem groceryItemToSave = groceryItemMapper.mapToEntity(groceryItem, user, product);

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

        // Get current user ID
        Long userId = authService.getCurrentUserId();

        // TODO: Need to ensure updatedItem has the same user as current userId

        // Fetch the full User entity from UserService
        User user = userService.findUserById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        // Map DTO to entity (including ID) to save
        GroceryItem updatedItemToSave = groceryItemMapper.mapToEntityWithId(updatedItem, user, product);

        // Save grocery item
        GroceryItem savedUpdatedItem = groceryItemRepository.save(updatedItemToSave);

        // Propagate update to pantry item
        pantryItemService.updateGroceryItemInPantry(savedUpdatedItem, userId);

        // Map saved entity to DTO and return
        return groceryItemMapper.mapToDto(savedUpdatedItem);
    }

    @Override
    @Transactional
    public void deleteGroceryItem(Long id) {
        // Get current user ID
        Long userId = authService.getCurrentUserId();

        // Propagate deletion of pantry item first
        pantryItemService.deleteGroceryItemInPantry(id, userId);

        // Then delete grocery item
        groceryItemRepository.deleteByIdAndUserId(id, userId);
    }
}
