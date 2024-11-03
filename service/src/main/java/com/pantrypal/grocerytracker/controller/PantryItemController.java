package com.pantrypal.grocerytracker.controller;

import com.pantrypal.grocerytracker.constants.Constants;
import com.pantrypal.grocerytracker.dto.ModifyAmountRequest;
import com.pantrypal.grocerytracker.dto.PantryItemDto;
import com.pantrypal.grocerytracker.service.PantryItemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
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
@Tag(name = "Pantry Item Controller", description = "Endpoints for managing pantry items")
public class PantryItemController {
    private final PantryItemService pantryItemService;

    @Autowired
    public PantryItemController(PantryItemService pantryItemService) {
        this.pantryItemService = pantryItemService;
    }

    @Operation(summary = "Get all pantry items")
    @GetMapping(Constants.PANTRY_ITEM_API_GET_ALL)
    public List<PantryItemDto> getAllPantryItems() {
        return pantryItemService.getAllPantryItems();
    }

    @Operation(summary = "Get a single pantry item by ID")
    @GetMapping(Constants.PANTRY_ITEM_API_GET_BY_ID)
    public ResponseEntity<PantryItemDto> getPantryItemById(
            @PathVariable @Parameter(description = "ID of the pantry item") Long id
    ) {
        Optional<PantryItemDto> optionalPantryItem = pantryItemService.getPantryItemById(id);
        return optionalPantryItem.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Modify the quantity of an existing pantry item")
    @PatchMapping(Constants.PANTRY_ITEM_API_MODIFY)
    public ResponseEntity<PantryItemDto> modifyPantryItem(
            @PathVariable @Parameter(description = "ID of the pantry item to be modified") Long id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Request body containing the new quantity"
            ) @Valid @RequestBody ModifyAmountRequest request
    ) {
        PantryItemDto modifiedItem = pantryItemService.modifyPantryItemQuantity(id, request);
        return ResponseEntity.ok(modifiedItem);
    }

    @Operation(summary = "Check if the Pantry Item API is alive")
    @GetMapping(Constants.ALIVE_ENDPOINT_PATH)
    public String alive() {
        return Constants.PANTRY_ITEM_ALIVE_MESSAGE;
    }
}
