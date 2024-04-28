package com.pantrypal.grocerytracker.service.impl;

import com.pantrypal.grocerytracker.constants.Constants;
import com.pantrypal.grocerytracker.dto.ModifyAmountRequest;
import com.pantrypal.grocerytracker.dto.PantryItemDto;
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
    public List<PantryItemDto> getAllPantryItems() {
        List<PantryItem> pantryItems = pantryItemRepository.findAll();

        // Map each pantry item to DTO and return
        return pantryItems.stream()
                .map(pantryItemMapper::mapToDto)
                .toList();
    }

    @Override
    public Optional<PantryItemDto> getPantryItemById(Long id) {
        Optional<PantryItem> optionalPantryItem = pantryItemRepository.findById(id);
        return optionalPantryItem.map(pantryItemMapper::mapToDto);
    }

    @Override
    public PantryItemDto addGroceryItemToPantry(GroceryItem groceryItem) {
        // Map grocery item to entity
        PantryItem pantryItem = pantryItemMapper.mapToEntity(groceryItem);

        // Save and return pantry item
        PantryItem savedPantryItem = pantryItemRepository.save(pantryItem);
        return pantryItemMapper.mapToDto(savedPantryItem);
    }

    @Override
    public PantryItemDto modifyPantryItemQuantity(Long id, ModifyAmountRequest request) {
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
        PantryItem savedExistingItem = pantryItemRepository.save(existingItem);
        return pantryItemMapper.mapToDto(savedExistingItem);
    }
}
