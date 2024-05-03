package com.pantrypal.grocerytracker.controller;

import com.pantrypal.grocerytracker.constants.Constants;
import com.pantrypal.grocerytracker.dto.GroceryItemDto;
import com.pantrypal.grocerytracker.service.GroceryItemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(Constants.GROCERY_ITEM_API_BASE_PATH)
@Tag(name = "Grocery Item Controller", description = "Endpoints for managing grocery items")
public class GroceryItemController {
    private final GroceryItemService groceryItemService;

    @Autowired
    public GroceryItemController(GroceryItemService groceryItemService) {
        this.groceryItemService = groceryItemService;
    }

    @Operation(summary = "Get all grocery items")
    @GetMapping(Constants.GROCERY_ITEM_API_GET_ALL)
    public List<GroceryItemDto> getAllGroceryItems() {
        return groceryItemService.getAllGroceryItems();
    }

    @Operation(summary = "Get a grocery item by ID")
    @GetMapping(Constants.GROCERY_ITEM_API_GET_BY_ID)
    public ResponseEntity<GroceryItemDto> getGroceryItemById(
            @PathVariable @Parameter(description = "ID of the grocery item") Long id
    ) {
        Optional<GroceryItemDto> optionalGroceryItem = groceryItemService.getGroceryItemById(id);
        return optionalGroceryItem
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Create a new grocery item")
    @PostMapping(Constants.GROCERY_ITEM_API_CREATE)
    public ResponseEntity<GroceryItemDto> createGroceryItem(
            @RequestBody(description = "Details of the grocery item to be created") GroceryItemDto groceryItem
    ) {
        GroceryItemDto createdItem = groceryItemService.createGroceryItem(groceryItem);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdItem);
    }

    @Operation(summary = "Update an existing grocery item")
    @PutMapping(Constants.GROCERY_ITEM_API_UPDATE)
    public ResponseEntity<GroceryItemDto> updateGroceryItem(
            @PathVariable @Parameter(description = "ID of the grocery item to be updated") Long id,
            @RequestBody(description = "Updated details of the grocery item") GroceryItemDto updatedItem
    ) {
        Optional<GroceryItemDto> existingItem = groceryItemService.getGroceryItemById(id);
        return existingItem.map(item -> {
            updatedItem.setId(id); // Ensure the ID is set
            GroceryItemDto updated = groceryItemService.updateGroceryItem(updatedItem);
            return ResponseEntity.ok(updated);
        }).orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Delete a grocery item")
    @DeleteMapping(Constants.GROCERY_ITEM_API_DELETE)
    public ResponseEntity<Void> deleteGroceryItem(
            @PathVariable @Parameter(description = "ID of the grocery item to be deleted") Long id
    ) {
        Optional<GroceryItemDto> existingItem = groceryItemService.getGroceryItemById(id);
        return existingItem.map(item -> {
            groceryItemService.deleteGroceryItem(id);
            return ResponseEntity.noContent().<Void>build(); // Explicitly specify the generic type
        }).orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Check if the Grocery Item API is alive")
    @GetMapping(Constants.ALIVE_ENDPOINT_PATH)
    public String alive() {
        return Constants.GROCERY_ITEM_ALIVE_MESSAGE;
    }
}
