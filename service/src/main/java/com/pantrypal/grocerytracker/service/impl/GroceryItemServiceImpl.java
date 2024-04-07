package com.pantrypal.grocerytracker.service.impl;

import com.pantrypal.grocerytracker.constants.Constants;
import com.pantrypal.grocerytracker.dto.ModifyAmountRequest;
import com.pantrypal.grocerytracker.model.GroceryItem;
import com.pantrypal.grocerytracker.repository.GroceryItemRepository;
import com.pantrypal.grocerytracker.service.GroceryItemService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GroceryItemServiceImpl implements GroceryItemService {
    private final GroceryItemRepository groceryItemRepository;

    @Autowired
    public GroceryItemServiceImpl(GroceryItemRepository groceryItemRepository) {
        this.groceryItemRepository = groceryItemRepository;
    }

    @Override
    public List<GroceryItem> getAllGroceryItems() {
        return groceryItemRepository.findAll();
    }

    @Override
    public Optional<GroceryItem> getGroceryItemById(Long id) {
        return groceryItemRepository.findById(id);
    }

    @Override
    public GroceryItem createGroceryItem(GroceryItem groceryItem) {
        return groceryItemRepository.save(groceryItem);
    }

    @Override
    public GroceryItem updateGroceryItem(GroceryItem updatedItem) {
        return groceryItemRepository.save(updatedItem);
    }

    @Override
    public GroceryItem modifyGroceryItemQuantity(Long id, ModifyAmountRequest request) {
        Optional<GroceryItem> existingItemOptional = groceryItemRepository.findById(id);
        if (existingItemOptional.isEmpty()) {
            throw new EntityNotFoundException(Constants.ERROR_MESSAGE_GROCERY_ITEM_NOT_FOUND_WITH_ID + id);
        }

        GroceryItem existingItem = existingItemOptional.get();
        double currentAmount = existingItem.getAmount();
        double modifiedAmountInBaseUnit = request.getUnit().convertToBaseUnit(request.getAmount());

        if (!existingItem.getUnit().getClass().equals(request.getUnit().getClass())) {
            // If the existing item's unit is not the same type as the request unit,
            // convert the existing item's amount to the base unit first
            currentAmount = existingItem.getUnit().convertToBaseUnit(currentAmount);
        }

        if (currentAmount < modifiedAmountInBaseUnit) {
            throw new IllegalArgumentException(Constants.ERROR_MESSAGE_INSUFFICIENT_QUANTITY);
        }

        double updatedAmountInBaseUnit = currentAmount - modifiedAmountInBaseUnit;
        existingItem.setAmount(existingItem.getUnit().convertFromBaseUnit(updatedAmountInBaseUnit));
        return groceryItemRepository.save(existingItem);
    }

    @Override
    public void deleteGroceryItem(Long id) {
        groceryItemRepository.deleteById(id);
    }
}
