package com.pantrypal.grocerytracker.mapper;

import com.pantrypal.grocerytracker.dto.PantryItemDto;
import com.pantrypal.grocerytracker.model.GroceryItem;
import com.pantrypal.grocerytracker.model.PantryItem;
import org.springframework.stereotype.Component;

/**
 * Mapper class for converting between {@link PantryItem} or {@link GroceryItem} entities
 * and {@link PantryItemDto} DTOs.
 * This class provides methods to map DTOs to entities and vice versa.
 */
@Component
public class PantryItemMapper {
    /**
     * Maps a {@link GroceryItem} entity to a {@link PantryItem} entity.
     * The initial quantity in stock is set to the amount of the grocery item.
     *
     * @param groceryItem The entity representing the grocery item.
     * @return The mapped {@link PantryItem} entity.
     */
    public PantryItem mapToEntity(GroceryItem groceryItem) {
        PantryItem pantryItem = new PantryItem();
        pantryItem.setGroceryItem(groceryItem);
        pantryItem.setQuantityInStock(groceryItem.getAmount());
        return pantryItem;
    }

    /**
     * Maps a {@link GroceryItem} entity to a {@link PantryItem} entity with the provided ID.
     * The initial quantity in stock is set to the amount of the grocery item.
     *
     * @param groceryItem   The entity representing the grocery item.
     * @param pantryItemId  The ID to assign to the pantry item.
     * @return The mapped {@link PantryItem} entity with the provided ID.
     */
    public PantryItem mapToEntityWithId(GroceryItem groceryItem, Long pantryItemId) {
        PantryItem pantryItem = new PantryItem();
        pantryItem.setId(pantryItemId);
        pantryItem.setGroceryItem(groceryItem);
        pantryItem.setQuantityInStock(groceryItem.getAmount());
        return pantryItem;
    }

    /**
     * Maps a {@link PantryItem} entity to a {@link PantryItemDto} DTO.
     *
     * @param pantryItem The entity representing the pantry item.
     * @return The mapped {@link PantryItemDto} DTO.
     */
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
