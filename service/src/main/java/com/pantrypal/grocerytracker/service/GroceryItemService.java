package com.pantrypal.grocerytracker.service;

import com.pantrypal.grocerytracker.dto.BuyGroceryItemRequest;
import com.pantrypal.grocerytracker.model.GroceryItem;

import java.util.List;
import java.util.Optional;

public interface GroceryItemService {
    List<GroceryItem> getAllGroceryItems();

    Optional<GroceryItem> getGroceryItemById(Long id);

    GroceryItem createGroceryItem(BuyGroceryItemRequest groceryItem);

    GroceryItem updateGroceryItem(GroceryItem updatedItem);

    void deleteGroceryItem(Long id);
}
