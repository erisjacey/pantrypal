package com.pantrypal.grocerytracker.service;

import com.pantrypal.grocerytracker.dto.ModifyAmountRequest;
import com.pantrypal.grocerytracker.dto.PantryItemDto;
import com.pantrypal.grocerytracker.model.GroceryItem;

import java.util.List;
import java.util.Optional;

public interface PantryItemService {
    List<PantryItemDto> getAllPantryItems();

    Optional<PantryItemDto> getPantryItemById(Long id);

    PantryItemDto addGroceryItemToPantry(GroceryItem groceryItem);

    PantryItemDto modifyPantryItemQuantity(Long id, ModifyAmountRequest request);
}
