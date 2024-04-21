package com.pantrypal.grocerytracker.controller;

import com.pantrypal.grocerytracker.constants.Constants;
import com.pantrypal.grocerytracker.dto.ModifyAmountRequest;
import com.pantrypal.grocerytracker.model.PantryItem;
import com.pantrypal.grocerytracker.service.PantryItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(Constants.PANTRY_ITEM_API_BASE_PATH)
public class PantryItemController {
    private final PantryItemService pantryItemService;

    @Autowired
    public PantryItemController(PantryItemService pantryItemService) {
        this.pantryItemService = pantryItemService;
    }

    // Endpoint to get all pantry items
    @GetMapping(Constants.PANTRY_ITEM_API_GET_ALL)
    public List<PantryItem> getAllPantryItems() {
        return pantryItemService.getAllPantryItems();
    }

    // Endpoint to get a single pantry item by ID
    @GetMapping(Constants.PANTRY_ITEM_API_GET_BY_ID)
    public ResponseEntity<PantryItem> getGroceryItemById(@PathVariable Long id) {
        Optional<PantryItem> optionalPantryItem = pantryItemService.getPantryItemById(id);
        return optionalPantryItem.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Endpoint to create a new pantry item
    @PostMapping(Constants.PANTRY_ITEM_API_CREATE)
    public ResponseEntity<PantryItem> createPantryItem(@RequestBody PantryItem pantryItem) {
        PantryItem createdItem = pantryItemService.createPantryItem(pantryItem);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdItem);
    }

    // Endpoint to update an existing pantry item
    @PutMapping(Constants.PANTRY_ITEM_API_UPDATE)
    public ResponseEntity<PantryItem> updatePantryItem(@PathVariable Long id, @RequestBody PantryItem updatedItem) {
        Optional<PantryItem> existingItem = pantryItemService.getPantryItemById(id);
        return existingItem.map(item -> {
            updatedItem.setId(id); // Ensure the ID is set
            PantryItem updated = pantryItemService.updatePantryItem(updatedItem);
            return ResponseEntity.ok(updated);
        }).orElse(ResponseEntity.notFound().build());
    }

    // Endpoint to modify the quantity of an existing pantry item
    @PatchMapping(Constants.PANTRY_ITEM_API_MODIFY)
    public ResponseEntity<PantryItem> modifyPantryItem(@PathVariable Long id, @RequestBody ModifyAmountRequest request) {
        PantryItem modifiedItem = pantryItemService.modifyPantryItemQuantity(id, request);
        return ResponseEntity.ok(modifiedItem);
    }

    @GetMapping(Constants.ALIVE_ENDPOINT_PATH)
    public String alive() {
        return Constants.PANTRY_ITEM_ALIVE_MESSAGE;
    }
}
