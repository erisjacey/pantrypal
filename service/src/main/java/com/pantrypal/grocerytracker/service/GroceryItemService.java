package com.pantrypal.grocerytracker.service;

import com.pantrypal.grocerytracker.dto.GroceryItemDto;

import java.util.List;
import java.util.Optional;

public interface GroceryItemService {
    List<GroceryItemDto> getAllGroceryItems();

    Optional<GroceryItemDto> getGroceryItemById(Long id);

    GroceryItemDto createGroceryItem(GroceryItemDto groceryItem);

    GroceryItemDto updateGroceryItem(GroceryItemDto updatedItem);

    void deleteGroceryItem(Long id);
}
