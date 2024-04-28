package com.pantrypal.grocerytracker.mapper;

import com.pantrypal.grocerytracker.dto.GroceryItemDto;
import com.pantrypal.grocerytracker.model.GroceryItem;
import com.pantrypal.grocerytracker.model.Product;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class GroceryItemMapper {
    public GroceryItem mapToEntity(GroceryItemDto dto, Product product) {
        GroceryItem groceryItem = new GroceryItem();
        groceryItem.setProduct(product);
        groceryItem.setAmount(dto.getAmount());
        groceryItem.setUnit(dto.getUnit());
        groceryItem.setPurchasedDate(dto.getPurchasedDate() != null ? dto.getPurchasedDate() : LocalDate.now());
        groceryItem.setExpirationDate(dto.getExpirationDate());
        return groceryItem;
    }

    public GroceryItem mapToEntityWithId(GroceryItemDto dto, Product product) {
        GroceryItem groceryItem = mapToEntity(dto, product);
        groceryItem.setId(dto.getId());
        return groceryItem;
    }

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
