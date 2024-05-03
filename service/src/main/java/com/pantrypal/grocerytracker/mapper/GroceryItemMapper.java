package com.pantrypal.grocerytracker.mapper;

import com.pantrypal.grocerytracker.dto.GroceryItemDto;
import com.pantrypal.grocerytracker.model.GroceryItem;
import com.pantrypal.grocerytracker.model.Product;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

/**
 * Mapper class for converting between {@link GroceryItem} entities and {@link GroceryItemDto} DTOs.
 * This class provides methods to map DTOs to entities and vice versa.
 */
@Component
public class GroceryItemMapper {
    /**
     * Maps a {@link GroceryItemDto} and a {@link Product} to a {@link GroceryItem} entity.
     *
     * @param dto     The DTO containing information about the grocery item.
     * @param product The associated product of the grocery item.
     * @return The mapped {@link GroceryItem} entity.
     */
    public GroceryItem mapToEntity(GroceryItemDto dto, Product product) {
        GroceryItem groceryItem = new GroceryItem();
        groceryItem.setProduct(product);
        groceryItem.setAmount(dto.getAmount());
        groceryItem.setUnit(dto.getUnit());
        groceryItem.setPurchasedDate(dto.getPurchasedDate() != null ? dto.getPurchasedDate() : LocalDate.now());
        groceryItem.setExpirationDate(dto.getExpirationDate());
        return groceryItem;
    }

    /**
     * Maps a {@link GroceryItemDto} and a {@link Product} to a {@link GroceryItem} entity with the provided ID.
     *
     * @param dto     The DTO containing information about the grocery item.
     * @param product The associated product of the grocery item.
     * @return The mapped {@link GroceryItem} entity with the provided ID.
     */
    public GroceryItem mapToEntityWithId(GroceryItemDto dto, Product product) {
        GroceryItem groceryItem = mapToEntity(dto, product);
        groceryItem.setId(dto.getId());
        return groceryItem;
    }

    /**
     * Maps a {@link GroceryItem} entity to a {@link GroceryItemDto} DTO.
     *
     * @param groceryItem The entity representing the grocery item.
     * @return The mapped {@link GroceryItemDto} DTO.
     */
    public GroceryItemDto mapToDto(GroceryItem groceryItem) {
        GroceryItemDto dto = new GroceryItemDto();
        dto.setId(groceryItem.getId());
        dto.setName(groceryItem.getProduct().getName());
        dto.setAmount(groceryItem.getAmount());
        dto.setUnit(groceryItem.getUnit());
        dto.setPurchasedDate(groceryItem.getPurchasedDate());
        dto.setExpirationDate(groceryItem.getExpirationDate());
        return dto;
    }
}
