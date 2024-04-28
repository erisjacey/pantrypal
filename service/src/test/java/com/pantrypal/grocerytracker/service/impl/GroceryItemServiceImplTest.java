package com.pantrypal.grocerytracker.service.impl;

import com.pantrypal.grocerytracker.dto.GroceryItemDto;
import com.pantrypal.grocerytracker.mapper.GroceryItemMapper;
import com.pantrypal.grocerytracker.model.GroceryItem;
import com.pantrypal.grocerytracker.repository.GroceryItemRepository;
import com.pantrypal.grocerytracker.service.GroceryItemService;
import com.pantrypal.grocerytracker.service.PantryItemService;
import com.pantrypal.grocerytracker.service.ProductService;
import com.pantrypal.grocerytracker.util.TestModels;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class GroceryItemServiceImplTest {

    private GroceryItemRepository groceryItemRepository;
    private GroceryItemMapper groceryItemMapper;
    private ProductService productService;
    private PantryItemService pantryItemService;
    private GroceryItemService groceryItemService;

    @BeforeEach
    void setUp() {
        groceryItemRepository = mock(GroceryItemRepository.class);
        groceryItemMapper = mock(GroceryItemMapper.class);
        productService = mock(ProductService.class);
        pantryItemService = mock(PantryItemService.class);
        groceryItemService = new GroceryItemServiceImpl(
                groceryItemRepository, groceryItemMapper, productService, pantryItemService
        );
    }

    @Test
    @DisplayName("Test get all grocery items")
    void getAllGroceryItems() {
        // Arrange
        List<GroceryItem> mockGroceryItems = TestModels.getListOfTwoGroceryItems();
        List<GroceryItemDto> mockGroceryItemDtos = TestModels.getListOfTwoGroceryItemDtos();

        // Mock repository behavior
        when(groceryItemRepository.findAll()).thenReturn(mockGroceryItems);

        // Mock mapper behavior
        when(groceryItemMapper.mapToDto(mockGroceryItems.get(0))).thenReturn(TestModels.getMilkGroceryItemDto());
        when(groceryItemMapper.mapToDto(mockGroceryItems.get(1))).thenReturn(TestModels.getButterGroceryItemDto());

        // Act
        List<GroceryItemDto> result = groceryItemService.getAllGroceryItems();

        // Assert
        assertEquals(mockGroceryItems.size(), result.size());
        assertEquals(mockGroceryItemDtos, result);
        verify(groceryItemMapper, times(2)).mapToDto(any());
    }

    @Test
    @DisplayName("Test get grocery item by ID")
    void getGroceryItemById() {
        // Arrange
        long itemId = 1L;
        GroceryItem mockGroceryItem = TestModels.getMilkGroceryItem();
        GroceryItemDto mockGroceryItemDto = TestModels.getMilkGroceryItemDto();

        // Mock repository behavior
        when(groceryItemRepository.findById(itemId)).thenReturn(Optional.of(mockGroceryItem));
        // Mock mapper behavior
        when(groceryItemMapper.mapToDto(mockGroceryItem)).thenReturn(mockGroceryItemDto);

        // Act
        Optional<GroceryItemDto> result = groceryItemService.getGroceryItemById(itemId);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(mockGroceryItemDto, result.get());
        verify(groceryItemMapper).mapToDto(mockGroceryItem);
    }

    @Test
    @DisplayName("Test create grocery item")
    void createGroceryItem() {
        // Arrange
        GroceryItemDto mockInputDto = TestModels.getMilkGroceryItemDto();
        GroceryItem mockSavedItem = TestModels.getMilkGroceryItem();

        // Mock product service behavior
        when(productService.findOrCreateProduct(anyString())).thenReturn(TestModels.getMilkProduct());

        // Mock repository behavior
        when(groceryItemRepository.save(mockSavedItem)).thenReturn(mockSavedItem);

        // Mock mapper behavior
        when(groceryItemMapper.mapToEntity(any(), any())).thenReturn(mockSavedItem);
        when(groceryItemMapper.mapToDto(mockSavedItem)).thenReturn(mockInputDto);

        // Act
        GroceryItemDto result = groceryItemService.createGroceryItem(mockInputDto);

        // Assert
        assertNotNull(result);
        assertEquals(mockInputDto, result);
        verify(groceryItemRepository).save(mockSavedItem);
        verify(pantryItemService).addGroceryItemToPantry(mockSavedItem);
    }

    @Test
    @DisplayName("Test update grocery item")
    void updateGroceryItem() {
        // Arrange
        GroceryItemDto mockInputDto = TestModels.getMilkGroceryItemDto();
        GroceryItem mockUpdatedItem = TestModels.getMilkGroceryItem();

        // Mock product service behavior
        when(productService.findOrCreateProduct(anyString())).thenReturn(TestModels.getMilkProduct());

        // Mock repository behavior
        when(groceryItemRepository.save(mockUpdatedItem)).thenReturn(mockUpdatedItem);

        // Mock mapper behavior
        when(groceryItemMapper.mapToEntityWithId(any(), any())).thenReturn(mockUpdatedItem);
        when(groceryItemMapper.mapToDto(mockUpdatedItem)).thenReturn(mockInputDto);

        // Act
        GroceryItemDto result = groceryItemService.updateGroceryItem(mockInputDto);

        // Assert
        assertNotNull(result);
        assertEquals(mockInputDto, result);
        verify(groceryItemRepository).save(mockUpdatedItem);
    }

    @Test
    @DisplayName("Test delete grocery item")
    void deleteGroceryItem() {
        // Arrange
        long itemId = 1L;

        // Act
        groceryItemService.deleteGroceryItem(itemId);

        // Assert
        verify(groceryItemRepository).deleteById(itemId);
    }
}
