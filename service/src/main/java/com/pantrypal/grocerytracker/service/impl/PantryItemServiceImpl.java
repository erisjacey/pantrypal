package com.pantrypal.grocerytracker.service.impl;

import com.pantrypal.grocerytracker.constants.Constants;
import com.pantrypal.grocerytracker.dto.ModifyAmountRequest;
import com.pantrypal.grocerytracker.dto.PantryItemDto;
import com.pantrypal.grocerytracker.mapper.PantryItemMapper;
import com.pantrypal.grocerytracker.model.GroceryItem;
import com.pantrypal.grocerytracker.model.PantryItem;
import com.pantrypal.grocerytracker.model.unit.Unit;
import com.pantrypal.grocerytracker.repository.PantryItemRepository;
import com.pantrypal.grocerytracker.service.AuthService;
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
    private final AuthService authService;

    @Autowired
    public PantryItemServiceImpl(
            PantryItemRepository pantryItemRepository,
            PantryItemMapper pantryItemMapper,
            AuthService authService
    ) {
        this.pantryItemRepository = pantryItemRepository;
        this.pantryItemMapper = pantryItemMapper;
        this.authService = authService;
    }

    @Override
    public List<PantryItemDto> getAllPantryItems() {
        Long userId = authService.getCurrentUserId();
        List<PantryItem> pantryItems = pantryItemRepository.findByUserId(userId);

        // Map each pantry item to DTO and return
        return pantryItems.stream()
                .map(pantryItemMapper::mapToDto)
                .toList();
    }

    @Override
    public Optional<PantryItemDto> getPantryItemById(Long id) {
        Long userId = authService.getCurrentUserId();
        Optional<PantryItem> optionalPantryItem = pantryItemRepository.findByIdAndUserId(id, userId);
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
    public PantryItemDto updateGroceryItemInPantry(GroceryItem updatedItem, Long userId) {
        // Find existing pantry item
        PantryItem existingPantryItem = findPantryItemByGroceryItemIdAndUserId(updatedItem.getId(), userId);

        // Map grocery item to entity (including pantry item ID)
        PantryItem updatedPantryItem = pantryItemMapper.mapToEntityWithId(updatedItem, existingPantryItem.getId());

        // Save and return pantry item
        PantryItem savedUpdatedPantryItem = pantryItemRepository.save(updatedPantryItem);
        return pantryItemMapper.mapToDto(savedUpdatedPantryItem);
    }

    @Override
    public void deleteGroceryItemInPantry(Long groceryItemId, Long userId) {
        pantryItemRepository.deleteByGroceryItemIdAndUserId(groceryItemId, userId);
    }

    @Override
    public PantryItemDto modifyPantryItemQuantity(Long id, ModifyAmountRequest request) {
        // Get current user ID
        Long userId = authService.getCurrentUserId();

        // Find existing pantry item
        PantryItem existingItem = findPantryItemByIdAndUserId(id, userId);

        double currentAmount = existingItem.getQuantityInStock();
        double modifyAmountInBaseUnit = request.getUnit().convertToBaseUnit(request.getAmount());
        Unit existingItemUnit = existingItem.getGroceryItem().getUnit();

        if (!existingItemUnit.equals(request.getUnit())) {
            // If the existing item's unit is not the same type as the request unit,
            // convert the existing item's amount to the base unit first
            currentAmount = existingItemUnit.convertToBaseUnit(currentAmount);
        }

        if (currentAmount < modifyAmountInBaseUnit) {
            throw new IllegalArgumentException(Constants.ERROR_MESSAGE_INSUFFICIENT_QUANTITY);
        }

        double updatedAmountInBaseUnit = currentAmount - modifyAmountInBaseUnit;
        existingItem.setQuantityInStock(existingItemUnit.convertFromBaseUnit(updatedAmountInBaseUnit));
        PantryItem savedExistingItem = pantryItemRepository.save(existingItem);
        return pantryItemMapper.mapToDto(savedExistingItem);
    }


    /**
     * Finds a pantry item by the ID of its associated grocery item, and the current user ID.
     *
     * @param groceryItemId The ID of the grocery item associated with the pantry item to find.
     * @param userId        The ID of the current user.
     * @return The pantry item associated with the specified grocery item ID and user ID.
     * @throws EntityNotFoundException If no pantry item is found associated with the given grocery item ID and user ID.
     */
    private PantryItem findPantryItemByGroceryItemIdAndUserId(Long groceryItemId, Long userId) {
        Optional<PantryItem> existingPantryItemOptional = pantryItemRepository.findByGroceryItemIdAndUserId(
                groceryItemId, userId);
        return existingPantryItemOptional.orElseThrow(
                () -> new EntityNotFoundException(
                        Constants.ERROR_MESSAGE_PANTRY_ITEM_NOT_FOUND_WITH_GROCERY_ITEM_ID + groceryItemId
                )
        );
    }

    /**
     * Finds a pantry item by its ID, and the current user ID.
     *
     * @param id     The ID of the pantry item to find.
     * @param userId The ID of the current user.
     * @return The pantry item with the specified ID and user ID.
     * @throws EntityNotFoundException If no pantry item is found with the given ID and user ID.
     */
    private PantryItem findPantryItemByIdAndUserId(Long id, Long userId) {
        Optional<PantryItem> existingItemOptional = pantryItemRepository.findByIdAndUserId(id, userId);
        return existingItemOptional.orElseThrow(
                () -> new EntityNotFoundException(
                        Constants.ERROR_MESSAGE_PANTRY_ITEM_NOT_FOUND_WITH_ID + id
                )
        );
    }
}
