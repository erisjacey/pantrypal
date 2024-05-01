package com.pantrypal.grocerytracker.controller;

import com.pantrypal.grocerytracker.dto.GroceryItemDto;
import com.pantrypal.grocerytracker.service.GroceryItemService;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class GroceryItemControllerTest {

    private GroceryItemService groceryItemService;
    private GroceryItemController groceryItemController;

    @BeforeEach
    void setUp() {
        groceryItemService = mock(GroceryItemService.class);
        groceryItemController = new GroceryItemController(groceryItemService);
    }

    @Test
    @DisplayName("Test get all grocery items")
    void getAllGroceryItems() {
        // Arrange
        List<GroceryItemDto> mockGroceryItems = TestModels.getListOfTwoGroceryItemDtos();

        // Mock service behavior
        when(groceryItemService.getAllGroceryItems()).thenReturn(mockGroceryItems);

        // Act
        List<GroceryItemDto> result = groceryItemController.getAllGroceryItems();

        // Assert
        assertEquals(mockGroceryItems.size(), result.size());
        assertEquals(mockGroceryItems, result);
        verify(groceryItemService, times(1)).getAllGroceryItems();
    }

    @Test
    @DisplayName("Test get grocery item by ID - item found")
    void getGroceryItemById_itemFound() {
        // Arrange
        long itemId = TestModels.ID_1;
        GroceryItemDto mockGroceryItemDto = TestModels.getMilkGroceryItemDto();

        // Mock service behavior
        when(groceryItemService.getGroceryItemById(itemId)).thenReturn(Optional.of(mockGroceryItemDto));

        // Act
        ResponseEntity<GroceryItemDto> result = groceryItemController.getGroceryItemById(itemId);

        // Assert
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertTrue(result.hasBody());
        assertEquals(mockGroceryItemDto, result.getBody());
        verify(groceryItemService, times(1)).getGroceryItemById(itemId);
    }

    @Test
    @DisplayName("Test get grocery item by ID - item not found")
    void getGroceryItemById_itemNotFound() {
        // Arrange
        long itemId = TestModels.ID_1;

        // Mock service behavior
        when(groceryItemService.getGroceryItemById(itemId)).thenReturn(Optional.empty());

        // Act
        ResponseEntity<GroceryItemDto> result = groceryItemController.getGroceryItemById(itemId);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
        assertNull(result.getBody());
        verify(groceryItemService, times(1)).getGroceryItemById(itemId);
    }

    @Test
    @DisplayName("Test create grocery item - success")
    void createGroceryItem_success() {
        // Arrange
        GroceryItemDto mockInputDto = TestModels.getMilkGroceryItemDto();
        GroceryItemDto mockSavedItem = TestModels.getMilkGroceryItemDto();

        // Mock service behavior
        when(groceryItemService.createGroceryItem(mockInputDto)).thenReturn(mockSavedItem);

        // Act
        ResponseEntity<GroceryItemDto> result = groceryItemController.createGroceryItem(mockInputDto);

        // Assert
        assertEquals(HttpStatus.CREATED, result.getStatusCode());
        assertTrue(result.hasBody());
        assertEquals(mockSavedItem, result.getBody());
        verify(groceryItemService, times(1)).createGroceryItem(mockInputDto);
    }

    @Test
    @DisplayName("Test update grocery item - item found")
    void updateGroceryItem_itemFound() {
        // Arrange
        long itemId = TestModels.ID_1;
        GroceryItemDto mockUpdatedItem = TestModels.getMilkGroceryItemDto();

        // Mock service behavior
        when(groceryItemService.getGroceryItemById(itemId)).thenReturn(Optional.of(mockUpdatedItem));
        when(groceryItemService.updateGroceryItem(mockUpdatedItem)).thenReturn(mockUpdatedItem);

        // Act
        ResponseEntity<GroceryItemDto> result = groceryItemController.updateGroceryItem(itemId, mockUpdatedItem);

        // Assert
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertTrue(result.hasBody());
        assertEquals(mockUpdatedItem, result.getBody());
        verify(groceryItemService, times(1)).updateGroceryItem(mockUpdatedItem);
    }

    @Test
    @DisplayName("Test update grocery item - item not found")
    void updateGroceryItem_itemNotFound() {
        // Arrange
        long itemId = TestModels.ID_1;

        // Mock service behavior
        when(groceryItemService.getGroceryItemById(itemId)).thenReturn(Optional.empty());

        // Act
        ResponseEntity<GroceryItemDto> result = groceryItemController.updateGroceryItem(
                itemId, TestModels.getMilkGroceryItemDto());

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
        assertNull(result.getBody());
        verify(groceryItemService, never()).updateGroceryItem(any());
    }

    @Test
    @DisplayName("Test delete grocery item - item found")
    void deleteGroceryItem_itemFound() {
        // Arrange
        long itemId = TestModels.ID_1;
        GroceryItemDto mockGroceryItemDto = TestModels.getMilkGroceryItemDto();

        // Mock service behavior
        when(groceryItemService.getGroceryItemById(itemId)).thenReturn(Optional.of(mockGroceryItemDto));

        // Act
        ResponseEntity<Void> result = groceryItemController.deleteGroceryItem(itemId);

        // Assert
        assertEquals(HttpStatus.NO_CONTENT, result.getStatusCode());
        verify(groceryItemService, times(1)).deleteGroceryItem(itemId);
    }

    @Test
    @DisplayName("Test delete grocery item - not found")
    void deleteGroceryItem_itemNotFound() {
        // Arrange
        long itemId = TestModels.ID_1;

        // Mock service behavior
        when(groceryItemService.getGroceryItemById(itemId)).thenReturn(Optional.empty());

        // Act
        ResponseEntity<Void> result = groceryItemController.deleteGroceryItem(itemId);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
        verify(groceryItemService, never()).deleteGroceryItem(anyLong());
    }
}
