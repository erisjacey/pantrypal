package com.pantrypal.grocerytracker.mapper;

import com.pantrypal.grocerytracker.model.GroceryItem;
import com.pantrypal.grocerytracker.model.PantryItem;
import org.springframework.stereotype.Component;

@Component
public class PantryItemMapper {
    public PantryItem mapToEntity(GroceryItem groceryItem) {
        PantryItem pantryItem = new PantryItem();
        pantryItem.setGroceryItem(groceryItem);
        pantryItem.setQuantityInStock(groceryItem.getAmount()); // The initial quantity is the same as the amount bought
        return pantryItem;
    }
}
