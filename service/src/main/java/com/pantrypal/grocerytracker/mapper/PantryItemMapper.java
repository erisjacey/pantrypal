package com.pantrypal.grocerytracker.mapper;

import com.pantrypal.grocerytracker.dto.PantryItemDto;
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

    public PantryItem mapToEntityWithId(GroceryItem groceryItem, Long pantryItemId) {
        PantryItem pantryItem = new PantryItem();
        pantryItem.setId(pantryItemId);
        pantryItem.setGroceryItem(groceryItem);
        pantryItem.setQuantityInStock(groceryItem.getAmount()); // The initial quantity is the same as the amount bought
        return pantryItem;
    }

    public PantryItemDto mapToDto(PantryItem pantryItem) {
        PantryItemDto dto = new PantryItemDto();
        dto.setId(pantryItem.getId());
        dto.setName(pantryItem.getGroceryItem().getProduct().getName());
        dto.setInitialAmount(pantryItem.getGroceryItem().getAmount());
        dto.setCurrentAmount(pantryItem.getQuantityInStock());
        dto.setUnit(pantryItem.getGroceryItem().getUnit());
        dto.setPurchasedDate(pantryItem.getGroceryItem().getPurchasedDate());
        dto.setExpirationDate(pantryItem.getGroceryItem().getExpirationDate());
        return dto;
    }
}
