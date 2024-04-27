package com.pantrypal.grocerytracker.service.impl;

import com.pantrypal.grocerytracker.constants.Constants;
import com.pantrypal.grocerytracker.dto.ModifyAmountRequest;
import com.pantrypal.grocerytracker.mapper.PantryItemMapper;
import com.pantrypal.grocerytracker.model.GroceryItem;
import com.pantrypal.grocerytracker.model.PantryItem;
import com.pantrypal.grocerytracker.model.unit.Unit;
import com.pantrypal.grocerytracker.repository.PantryItemRepository;
import com.pantrypal.grocerytracker.service.PantryItemService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PantryItemServiceImpl implements PantryItemService {
    private final PantryItemRepository pantryItemRepository;
    private final PantryItemMapper pantryItemMapper;

    @Autowired
    public PantryItemServiceImpl(
            PantryItemRepository pantryItemRepository,
            PantryItemMapper pantryItemMapper
    ) {
        this.pantryItemRepository = pantryItemRepository;
        this.pantryItemMapper = pantryItemMapper;
    }

    @Override
    public List<PantryItem> getAllPantryItems() {
        return pantryItemRepository.findAll();
    }

    @Override
    public Optional<PantryItem> getPantryItemById(Long id) {
        return pantryItemRepository.findById(id);
    }

    @Override
    public PantryItem createPantryItem(PantryItem pantryItem) {
        return pantryItemRepository.save(pantryItem);
    }

    @Override
    public PantryItem addGroceryItemToPantry(GroceryItem groceryItem) {
        // Map grocery item to entity
        PantryItem pantryItem = pantryItemMapper.mapToEntity(groceryItem);

        // Save and return pantry item
        return pantryItemRepository.save(pantryItem);
    }

    @Override
    public PantryItem updatePantryItem(PantryItem updatedItem) {
        return pantryItemRepository.save(updatedItem);
    }

    @Override
    public PantryItem modifyPantryItemQuantity(Long id, ModifyAmountRequest request) {
        Optional<PantryItem> existingItemOptional = pantryItemRepository.findById(id);
        if (existingItemOptional.isEmpty()) {
            throw new EntityNotFoundException(Constants.ERROR_MESSAGE_PANTRY_ITEM_NOT_FOUND_WITH_ID + id);
        }

        PantryItem existingItem = existingItemOptional.get();
        double currentAmount = existingItem.getQuantityInStock();
        double modifiedAmountInBaseUnit = request.getUnit().convertToBaseUnit(request.getAmount());
        Unit existingItemUnit = existingItem.getGroceryItem().getUnit();

        if (!existingItemUnit.getClass().equals(request.getUnit().getClass())) {
            // If the existing item's unit is not the same type as the request unit,
            // convert the existing item's amount to the base unit first
            currentAmount = existingItemUnit.convertToBaseUnit(currentAmount);
        }

        if (currentAmount < modifiedAmountInBaseUnit) {
            throw new IllegalArgumentException(Constants.ERROR_MESSAGE_INSUFFICIENT_QUANTITY);
        }

        double updatedAmountInBaseUnit = currentAmount - modifiedAmountInBaseUnit;
        existingItem.setQuantityInStock(existingItemUnit.convertFromBaseUnit(updatedAmountInBaseUnit));
        return pantryItemRepository.save(existingItem);
    }
}
