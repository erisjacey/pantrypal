package com.pantrypal.grocerytracker.controller;

import com.pantrypal.grocerytracker.constants.Constants;
import com.pantrypal.grocerytracker.dto.ModifyAmountRequest;
import com.pantrypal.grocerytracker.dto.PantryItemDto;
import com.pantrypal.grocerytracker.service.PantryItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
    public List<PantryItemDto> getAllPantryItems() {
        return pantryItemService.getAllPantryItems();
    }

    // Endpoint to get a single pantry item by ID
    @GetMapping(Constants.PANTRY_ITEM_API_GET_BY_ID)
    public ResponseEntity<PantryItemDto> getGroceryItemById(@PathVariable Long id) {
        Optional<PantryItemDto> optionalPantryItem = pantryItemService.getPantryItemById(id);
        return optionalPantryItem.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Endpoint to modify the quantity of an existing pantry item
    @PatchMapping(Constants.PANTRY_ITEM_API_MODIFY)
    public ResponseEntity<PantryItemDto> modifyPantryItem(@PathVariable Long id, @RequestBody ModifyAmountRequest request) {
        PantryItemDto modifiedItem = pantryItemService.modifyPantryItemQuantity(id, request);
        return ResponseEntity.ok(modifiedItem);
    }

    @GetMapping(Constants.ALIVE_ENDPOINT_PATH)
    public String alive() {
        return Constants.PANTRY_ITEM_ALIVE_MESSAGE;
    }
}
