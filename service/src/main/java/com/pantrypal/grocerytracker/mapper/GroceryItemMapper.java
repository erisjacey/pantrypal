package com.pantrypal.grocerytracker.mapper;

import com.pantrypal.grocerytracker.dto.BuyGroceryItemRequest;
import com.pantrypal.grocerytracker.model.GroceryItem;
import com.pantrypal.grocerytracker.model.Product;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class GroceryItemMapper {
    public GroceryItem mapToEntity(BuyGroceryItemRequest dto, Product product) {
        GroceryItem groceryItem = new GroceryItem();
        groceryItem.setProduct(product);
        groceryItem.setAmount(dto.getAmount());
        groceryItem.setUnit(dto.getUnit());
        groceryItem.setPurchasedDate(dto.getPurchasedDate() != null ? dto.getPurchasedDate() : LocalDate.now());
        groceryItem.setExpirationDate(dto.getExpirationDate());
        return groceryItem;
    }
}
