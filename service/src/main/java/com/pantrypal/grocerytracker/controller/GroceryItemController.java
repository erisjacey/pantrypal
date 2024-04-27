package com.pantrypal.grocerytracker.controller;

import com.pantrypal.grocerytracker.constants.Constants;
import com.pantrypal.grocerytracker.dto.BuyGroceryItemRequest;
import com.pantrypal.grocerytracker.model.GroceryItem;
import com.pantrypal.grocerytracker.service.GroceryItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(Constants.GROCERY_ITEM_API_BASE_PATH)
public class GroceryItemController {
    private final GroceryItemService groceryItemService;

    @Autowired
    public GroceryItemController(GroceryItemService groceryItemService) {
        this.groceryItemService = groceryItemService;
    }

    // Endpoint to get all grocery items
    @GetMapping(Constants.GROCERY_ITEM_API_GET_ALL)
    public List<GroceryItem> getAllGroceryItems() {
        return groceryItemService.getAllGroceryItems();
    }

    // Endpoint to get a single grocery item by ID
    @GetMapping(Constants.GROCERY_ITEM_API_GET_BY_ID)
    public ResponseEntity<GroceryItem> getGroceryItemById(@PathVariable Long id) {
        Optional<GroceryItem> optionalGroceryItem = groceryItemService.getGroceryItemById(id);
        return optionalGroceryItem.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Endpoint to create a new grocery item
    @PostMapping(Constants.GROCERY_ITEM_API_CREATE)
    public ResponseEntity<GroceryItem> createGroceryItem(@RequestBody BuyGroceryItemRequest request) {
        GroceryItem createdItem = groceryItemService.createGroceryItem(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdItem);
    }

    // Endpoint to update an existing grocery item
    @PutMapping(Constants.GROCERY_ITEM_API_UPDATE)
    public ResponseEntity<GroceryItem> updateGroceryItem(@PathVariable Long id, @RequestBody GroceryItem updatedItem) {
        Optional<GroceryItem> existingItem = groceryItemService.getGroceryItemById(id);
        return existingItem.map(item -> {
            updatedItem.setId(id); // Ensure the ID is set
            GroceryItem updated = groceryItemService.updateGroceryItem(updatedItem);
            return ResponseEntity.ok(updated);
        }).orElse(ResponseEntity.notFound().build());
    }

    // Endpoint to delete a grocery item
    @DeleteMapping(Constants.GROCERY_ITEM_API_DELETE)
    public ResponseEntity<Void> deleteGroceryItem(@PathVariable Long id) {
        Optional<GroceryItem> existingItem = groceryItemService.getGroceryItemById(id);
        return existingItem.map(item -> {
            groceryItemService.deleteGroceryItem(id);
            return ResponseEntity.noContent().<Void>build(); // Explicitly specify the generic type
        }).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping(Constants.ALIVE_ENDPOINT_PATH)
    public String alive() {
        return Constants.GROCERY_ITEM_ALIVE_MESSAGE;
    }
}
