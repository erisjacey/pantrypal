package com.pantrypal.grocerytracker.controller;

import com.pantrypal.grocerytracker.dto.ModifyAmountRequest;
import com.pantrypal.grocerytracker.dto.PantryItemDto;
import com.pantrypal.grocerytracker.service.PantryItemService;
import com.pantrypal.grocerytracker.util.TestModels;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class PantryItemControllerTest {

    private PantryItemService pantryItemService;
    private PantryItemController pantryItemController;

    @BeforeEach
    void setUp() {
        pantryItemService = mock(PantryItemService.class);
        pantryItemController = new PantryItemController(pantryItemService);
    }

    @Test
    @DisplayName("Test get all pantry items")
    void getAllPantryItems() {
        // Arrange
        List<PantryItemDto> mockPantryItems = TestModels.getListOfTwoPantryItemDtos();

        // Mock service behavior
        when(pantryItemService.getAllPantryItems()).thenReturn(mockPantryItems);

        // Act
        List<PantryItemDto> result = pantryItemController.getAllPantryItems();

        // Assert
        assertEquals(mockPantryItems.size(), result.size());
        assertEquals(mockPantryItems, result);
        verify(pantryItemService, times(1)).getAllPantryItems();
    }

    @Test
    @DisplayName("Test get pantry item by ID - item found")
    void getGroceryItemById_itemFound() {
        // Arrange
        long itemId = TestModels.ID_3;
        PantryItemDto mockPantryItemDto = TestModels.getMilkPantryItemDto();

        // Mock service behavior
        when(pantryItemService.getPantryItemById(itemId)).thenReturn(Optional.of(mockPantryItemDto));

        // Act
        ResponseEntity<PantryItemDto> result = pantryItemController.getGroceryItemById(itemId);

        // Assert
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertTrue(result.hasBody());
        assertEquals(mockPantryItemDto, result.getBody());
        verify(pantryItemService, times(1)).getPantryItemById(itemId);
    }

    @Test
    @DisplayName("Test get pantry item by ID - item not found")
    void getGroceryItemById_itemNotFound() {
        // Arrange
        long itemId = TestModels.ID_3;

        // Mock service behavior
        when(pantryItemService.getPantryItemById(itemId)).thenReturn(Optional.empty());

        // Act
        ResponseEntity<PantryItemDto> result = pantryItemController.getGroceryItemById(itemId);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
        assertNull(result.getBody());
        verify(pantryItemService, times(1)).getPantryItemById(itemId);
    }

    @Test
    @DisplayName("Test modify pantry item quantity")
    void modifyPantryItemQuantity() {
        // Arrange
        long itemId = TestModels.ID_3;
        ModifyAmountRequest request = TestModels.getModifyAmountRequest();

        PantryItemDto mockModifiedItem = TestModels.getMilkPantryItemDto();

        // Mock service behavior
        when(pantryItemService.modifyPantryItemQuantity(itemId, request)).thenReturn(mockModifiedItem);

        // Act
        ResponseEntity<PantryItemDto> result = pantryItemController.modifyPantryItem(itemId, request);

        // Assert
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertTrue(result.hasBody());
        assertEquals(mockModifiedItem, result.getBody());
        verify(pantryItemService, times(1)).modifyPantryItemQuantity(itemId, request);
    }
}
