package com.pantrypal.grocerytracker.service;

import com.pantrypal.grocerytracker.dto.ModifyAmountRequest;
import com.pantrypal.grocerytracker.dto.PantryItemDto;
import com.pantrypal.grocerytracker.model.GroceryItem;

import java.util.List;
import java.util.Optional;

/**
 * Service interface for managing pantry items.
 * Provides methods for retrieving, adding, updating, and deleting pantry items,
 * as well as modifying the quantity of existing pantry items.
 */
public interface PantryItemService {
    /**
     * Retrieves all pantry items.
     *
     * @return A list of {@link PantryItemDto} representing all pantry items.
     */
    List<PantryItemDto> getAllPantryItems();

    /**
     * Retrieves a pantry item by its ID.
     *
     * @param id The ID of the pantry item to retrieve.
     * @return An {@link Optional} containing the {@link PantryItemDto} if found, or empty if not found.
     */
    Optional<PantryItemDto> getPantryItemById(Long id);

    /**
     * Adds a grocery item to the pantry.
     * Used as a transitive step in {@link GroceryItemService#createGroceryItem}.
     *
     * @param groceryItem The grocery item to add to the pantry.
     * @return The {@link PantryItemDto} representing the added pantry item.
     */
    PantryItemDto addGroceryItemToPantry(GroceryItem groceryItem);

    /**
     * Updates a grocery item in the pantry.
     * Used as a transitive step in {@link GroceryItemService#updateGroceryItem}.
     *
     * @param updatedItem The updated grocery item to be stored in the pantry.
     * @return The {@link PantryItemDto} representing the updated pantry item.
     */
    PantryItemDto updateGroceryItemInPantry(GroceryItem updatedItem);

    /**
     * Deletes a grocery item from the pantry.
     * Used as a transitive step in {@link GroceryItemService#deleteGroceryItem}.
     *
     * @param groceryItemId The ID of the grocery item to delete from the pantry.
     */
    void deleteGroceryItemInPantry(Long groceryItemId);

    /**
     * Modifies the quantity of an existing pantry item.
     * The {@link ModifyAmountRequest} contains the unit of measurement for that amount,
     * and the magnitude of the amount itself.
     *
     * @param id      The ID of the pantry item to modify.
     * @param request The {@link ModifyAmountRequest} containing the details of the modification.
     * @return The {@link PantryItemDto} representing the modified pantry item.
     */
    PantryItemDto modifyPantryItemQuantity(Long id, ModifyAmountRequest request);
}
