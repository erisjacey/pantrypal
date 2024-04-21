package com.pantrypal.grocerytracker.service;

import com.pantrypal.grocerytracker.dto.ModifyAmountRequest;
import com.pantrypal.grocerytracker.model.PantryItem;

import java.util.List;
import java.util.Optional;

public interface PantryItemService {
    List<PantryItem> getAllPantryItems();

    Optional<PantryItem> getPantryItemById(Long id);

    PantryItem createPantryItem(PantryItem pantryItem);

    PantryItem updatePantryItem(PantryItem updatedItem);

    PantryItem modifyPantryItemQuantity(Long id, ModifyAmountRequest request);
}
