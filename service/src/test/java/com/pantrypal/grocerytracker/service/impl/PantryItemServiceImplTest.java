package com.pantrypal.grocerytracker.service.impl;

import com.pantrypal.grocerytracker.constants.Constants;
import com.pantrypal.grocerytracker.dto.ModifyAmountRequest;
import com.pantrypal.grocerytracker.dto.PantryItemDto;
import com.pantrypal.grocerytracker.mapper.PantryItemMapper;
import com.pantrypal.grocerytracker.model.GroceryItem;
import com.pantrypal.grocerytracker.model.PantryItem;
import com.pantrypal.grocerytracker.repository.PantryItemRepository;
import com.pantrypal.grocerytracker.service.PantryItemService;
import com.pantrypal.grocerytracker.util.TestModels;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class PantryItemServiceImplTest {

    private PantryItemRepository pantryItemRepository;
    private PantryItemMapper pantryItemMapper;
    private PantryItemService pantryItemService;

    @BeforeEach
    void setUp() {
        pantryItemRepository = mock(PantryItemRepository.class);
        pantryItemMapper = mock(PantryItemMapper.class);
        pantryItemService = new PantryItemServiceImpl(pantryItemRepository, pantryItemMapper);
    }

    @Test
    @DisplayName("Test get all pantry items")
    void getAllPantryItems() {
        // Arrange
        List<PantryItem> mockPantryItems = TestModels.getListOfTwoPantryItems();
        List<PantryItemDto> mockPantryItemDtos = TestModels.getListOfTwoPantryItemDtos();

        // Mock repository behavior
        when(pantryItemRepository.findAll()).thenReturn(mockPantryItems);

        // Mock mapper behavior
        when(pantryItemMapper.mapToDto(mockPantryItems.get(0))).thenReturn(TestModels.getMilkPantryItemDto());
        when(pantryItemMapper.mapToDto(mockPantryItems.get(1))).thenReturn(TestModels.getButterPantryItemDto());

        // Act
        List<PantryItemDto> result = pantryItemService.getAllPantryItems();

        // Assert
        assertEquals(mockPantryItems.size(), result.size());
        assertEquals(mockPantryItemDtos, result);
        verify(pantryItemMapper, times(2)).mapToDto(any());
    }

    @Test
    @DisplayName("Test get pantry item by ID")
    void getPantryItemById() {
        // Arrange
        long itemId = TestModels.ID_3;
        PantryItem mockPantryItem = TestModels.getMilkPantryItem();
        PantryItemDto mockPantryItemDto = TestModels.getMilkPantryItemDto();

        // Mock repository behavior
        when(pantryItemRepository.findById(itemId)).thenReturn(Optional.of(mockPantryItem));

        // Mock mapper behavior
        when(pantryItemMapper.mapToDto(mockPantryItem)).thenReturn(mockPantryItemDto);

        // Act
        Optional<PantryItemDto> result = pantryItemService.getPantryItemById(itemId);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(mockPantryItemDto, result.get());
        verify(pantryItemMapper).mapToDto(mockPantryItem);
    }

    @Test
    @DisplayName("Test add grocery item to pantry")
    void addGroceryItemToPantry() {
        // Arrange
        GroceryItem mockGroceryItem = TestModels.getButterGroceryItem();
        PantryItem mockPantryItem = TestModels.getButterPantryItem();
        PantryItemDto mockPantryItemDto = TestModels.getButterPantryItemDto();

        // Mock repository behavior
        when(pantryItemRepository.save(mockPantryItem)).thenReturn(mockPantryItem);

        // Mock mapper behavior
        when(pantryItemMapper.mapToEntity(mockGroceryItem)).thenReturn(mockPantryItem);
        when(pantryItemMapper.mapToDto(mockPantryItem)).thenReturn(mockPantryItemDto);

        // Act
        PantryItemDto result = pantryItemService.addGroceryItemToPantry(mockGroceryItem);

        // Assert
        assertNotNull(result);
        assertEquals(mockPantryItemDto, result);
        verify(pantryItemRepository).save(mockPantryItem);
        verify(pantryItemMapper).mapToDto(mockPantryItem);
    }

    @Test
    @DisplayName("Test update grocery item in pantry - pantry item exists")
    void updateGroceryItemInPantry_pantryItemExists() {
        // Arrange
        long updatedGroceryItemId = TestModels.ID_2;
        long existingPantryItemId = TestModels.ID_3;
        GroceryItem mockUpdatedGroceryItem = TestModels.getButterGroceryItem();
        PantryItem mockUpdatedPantryItem = TestModels.getButterPantryItem();
        PantryItem mockExistingPantryItem = TestModels.getMilkPantryItem();
        PantryItemDto mockUpdatedPantryItemDto = TestModels.getButterPantryItemDto();

        // Mock repository behavior
        when(pantryItemRepository.findByGroceryItem_Id(updatedGroceryItemId))
                .thenReturn(Optional.of(mockExistingPantryItem));
        when(pantryItemRepository.save(mockUpdatedPantryItem)).thenReturn(mockUpdatedPantryItem);

        // Mock mapper behavior
        when(pantryItemMapper.mapToEntityWithId(eq(mockUpdatedGroceryItem), eq(existingPantryItemId)))
                .thenReturn(mockUpdatedPantryItem);
        when(pantryItemMapper.mapToDto(mockUpdatedPantryItem)).thenReturn(mockUpdatedPantryItemDto);

        // Act
        PantryItemDto result = pantryItemService.updateGroceryItemInPantry(mockUpdatedGroceryItem);

        // Assert
        assertNotNull(result);
        assertEquals(mockUpdatedPantryItemDto, result);
        verify(pantryItemRepository).save(mockUpdatedPantryItem);
        verify(pantryItemMapper).mapToDto(mockUpdatedPantryItem);
    }

    @Test
    @DisplayName("Test update grocery item in pantry - pantry item does not exist")
    void updateGroceryItemInPantry_pantryItemDoesNotExist() {
        // Arrange
        long updatedGroceryItemId = TestModels.ID_2;
        GroceryItem mockUpdatedGroceryItem = TestModels.getButterGroceryItem();

        // Act
        Throwable exception = assertThrows(EntityNotFoundException.class, () ->
                    pantryItemService.updateGroceryItemInPantry(mockUpdatedGroceryItem)
        );

        // Assert
        assertEquals(
                Constants.ERROR_MESSAGE_PANTRY_ITEM_NOT_FOUND_WITH_GROCERY_ITEM_ID + updatedGroceryItemId,
                exception.getMessage()
        );
        verify(pantryItemRepository, never()).save(any());
        verify(pantryItemMapper, never()).mapToEntityWithId(any(), any());
        verify(pantryItemMapper, never()).mapToDto(any());
    }

    @Test
    @DisplayName("Test delete grocery item in pantry")
    void deleteGroceryItemInPantry() {
        // Arrange
        long itemId = TestModels.ID_1;

        // Act
        pantryItemService.deleteGroceryItemInPantry(itemId);

        // Assert
        verify(pantryItemRepository).deleteByGroceryItem_Id(itemId);
    }

    @Test
    @DisplayName("Test modify pantry item quantity - happy path")
    void modifyPantryItemQuantity() {
        // Arrange
        long itemId = TestModels.ID_3;
        ModifyAmountRequest request = TestModels.getModifyAmountRequest();
        PantryItem mockExistingPantryItem = TestModels.getMilkPantryItem();
        PantryItem mockModifiedPantryItem = TestModels.getMilkPantryItem();
        PantryItemDto mockModifiedPantryItemDto = TestModels.getMilkPantryItemDto();
        mockModifiedPantryItem.setQuantityInStock(TestModels.AMOUNT_2_POINT_3);
        mockModifiedPantryItemDto.setCurrentAmount(TestModels.AMOUNT_2_POINT_3);

        // Mock repository behavior
        when(pantryItemRepository.findById(itemId)).thenReturn(Optional.of(mockExistingPantryItem));
        when(pantryItemRepository.save(mockExistingPantryItem)).thenReturn(mockModifiedPantryItem);

        // Mock mapper behavior
        when(pantryItemMapper.mapToDto(mockModifiedPantryItem)).thenReturn(mockModifiedPantryItemDto);

        // Act
        PantryItemDto result = pantryItemService.modifyPantryItemQuantity(itemId, request);

        // Assert
        assertNotNull(result);
        assertEquals(mockModifiedPantryItem.getQuantityInStock(), mockExistingPantryItem.getQuantityInStock());
        assertEquals(mockModifiedPantryItem.getId(), mockExistingPantryItem.getId());
        assertEquals(mockModifiedPantryItem.getGroceryItem(), mockExistingPantryItem.getGroceryItem());
        verify(pantryItemRepository).save(mockModifiedPantryItem);
        verify(pantryItemMapper).mapToDto(mockModifiedPantryItem);
    }

    @Test
    @DisplayName("Test modify pantry item quantity - pantry item does not exist")
    void modifyPantryItemQuantity_pantryItemDoesNotExist() {
        // Arrange
        long itemId = TestModels.ID_3;
        ModifyAmountRequest mockRequest = TestModels.getModifyAmountRequest();

        // Act
        Throwable exception = assertThrows(EntityNotFoundException.class, () ->
                pantryItemService.modifyPantryItemQuantity(itemId, mockRequest)
        );

        // Assert
        assertEquals(Constants.ERROR_MESSAGE_PANTRY_ITEM_NOT_FOUND_WITH_ID + itemId, exception.getMessage());
        verify(pantryItemRepository, never()).save(any());
        verify(pantryItemMapper, never()).mapToDto(any());
    }

    @Test
    @DisplayName("Test modify pantry item quantity - insufficient remaining quantity")
    void modifyPantryItemQuantity_insufficientQuantity() {
        // Arrange
        long itemId = TestModels.ID_3;
        ModifyAmountRequest mockRequest = TestModels.getModifyAmountRequest();
        PantryItem mockExistingPantryItem = TestModels.getMilkPantryItem();
        // Set quantityInStock to 0.1L to force insufficient quantity
        mockExistingPantryItem.setQuantityInStock(TestModels.AMOUNT_0_POINT_1);

        // Mock repository behavior
        when(pantryItemRepository.findById(itemId)).thenReturn(Optional.of(mockExistingPantryItem));

        // Act
        Throwable exception = assertThrows(IllegalArgumentException.class, () ->
                pantryItemService.modifyPantryItemQuantity(itemId, mockRequest)
        );

        // Assert
        assertEquals(Constants.ERROR_MESSAGE_INSUFFICIENT_QUANTITY, exception.getMessage());
        verify(pantryItemRepository, never()).save(any());
        verify(pantryItemMapper, never()).mapToDto(any());
    }
}
