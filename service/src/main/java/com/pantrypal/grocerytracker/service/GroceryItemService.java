package com.pantrypal.grocerytracker.service;

import com.pantrypal.grocerytracker.dto.ModifyAmountRequest;
import com.pantrypal.grocerytracker.model.GroceryItem;

import java.util.List;
import java.util.Optional;

public interface GroceryItemService {
    List<GroceryItem> getAllGroceryItems();

    Optional<GroceryItem> getGroceryItemById(Long id);

    GroceryItem createGroceryItem(GroceryItem groceryItem);

    GroceryItem updateGroceryItem(GroceryItem updatedItem);

    GroceryItem modifyGroceryItemQuantity(Long id, ModifyAmountRequest request);

    void deleteGroceryItem(Long id);
}
