package com.pantrypal.grocerytracker.service;

import com.pantrypal.grocerytracker.dto.GroceryItemDto;

import java.util.List;
import java.util.Optional;

/**
 * Service interface for managing grocery items.
 * Provides methods to retrieve, create, update, and delete grocery items.
 */
public interface GroceryItemService {
    /**
     * Retrieves all grocery items.
     *
     * @return A list of {@link GroceryItemDto} representing all grocery items.
     */
    List<GroceryItemDto> getAllGroceryItems();

    /**
     * Retrieves a grocery item by its ID.
     *
     * @param id The ID of the grocery item to retrieve.
     * @return An {@link Optional} containing the {@link GroceryItemDto} if found,
     *         or an empty {@link Optional} if no grocery item with the given ID exists.
     */
    Optional<GroceryItemDto> getGroceryItemById(Long id);

    /**
     * Creates a new grocery item.
     * If the associated product doesn't exist, it creates one.
     * Also, transitively creates an associated pantry item whose quantity in stock matches
     * the amount of the grocery item.
     *
     * @param groceryItem The {@link GroceryItemDto} representing the new grocery item to create.
     * @return The created {@link GroceryItemDto}.
     */
    GroceryItemDto createGroceryItem(GroceryItemDto groceryItem);

    /**
     * Updates an existing grocery item.
     * Also updates the associated pantry item.
     *
     * @param updatedItem The {@link GroceryItemDto} representing the updated grocery item.
     * @return The updated {@link GroceryItemDto}.
     */
    GroceryItemDto updateGroceryItem(GroceryItemDto updatedItem);

    /**
     * Deletes a grocery item by its ID.
     * Also deletes the associated pantry item.
     *
     * @param id The ID of the grocery item to delete.
     */
    void deleteGroceryItem(Long id);
}
