package com.pantrypal.grocerytracker.service.impl;

import com.pantrypal.grocerytracker.dto.GroceryItemDto;
import com.pantrypal.grocerytracker.mapper.GroceryItemMapper;
import com.pantrypal.grocerytracker.model.GroceryItem;
import com.pantrypal.grocerytracker.model.User;
import com.pantrypal.grocerytracker.repository.GroceryItemRepository;
import com.pantrypal.grocerytracker.service.AuthService;
import com.pantrypal.grocerytracker.service.GroceryItemService;
import com.pantrypal.grocerytracker.service.PantryItemService;
import com.pantrypal.grocerytracker.service.ProductService;
import com.pantrypal.grocerytracker.service.UserService;
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
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class GroceryItemServiceImplTest {

    private GroceryItemRepository groceryItemRepository;
    private GroceryItemMapper groceryItemMapper;
    private ProductService productService;
    private PantryItemService pantryItemService;
    private AuthService authService;
    private UserService userService;
    private GroceryItemService groceryItemService;

    @BeforeEach
    void setUp() {
        groceryItemRepository = mock(GroceryItemRepository.class);
        groceryItemMapper = mock(GroceryItemMapper.class);
        productService = mock(ProductService.class);
        pantryItemService = mock(PantryItemService.class);
        authService = mock(AuthService.class);
        userService = mock(UserService.class);
        groceryItemService = new GroceryItemServiceImpl(
                groceryItemRepository, groceryItemMapper, productService, pantryItemService, authService, userService
        );
    }

    @Test
    @DisplayName("Test get all grocery items")
    void getAllGroceryItems() {
        // Arrange
        Long userId = TestModels.ID_1;
        List<GroceryItem> mockGroceryItems = TestModels.getListOfTwoGroceryItems();
        List<GroceryItemDto> mockGroceryItemDtos = TestModels.getListOfTwoGroceryItemDtos();

        // Mock auth service behavior
        when(authService.getCurrentUserId()).thenReturn(userId);

        // Mock repository behavior
        when(groceryItemRepository.findByUserId(userId)).thenReturn(mockGroceryItems);

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
        long itemId = TestModels.ID_1;
        long userId = TestModels.ID_1;
        GroceryItem mockGroceryItem = TestModels.getMilkGroceryItem();
        GroceryItemDto mockGroceryItemDto = TestModels.getMilkGroceryItemDto();

        // Mock auth service behavior
        when(authService.getCurrentUserId()).thenReturn(userId);

        // Mock repository behavior
        when(groceryItemRepository.findByIdAndUserId(itemId, userId)).thenReturn(Optional.of(mockGroceryItem));

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
        User user = TestModels.getUser();
        long userId = user.getId();
        GroceryItem mockGroceryItem = TestModels.getMilkGroceryItem();
        GroceryItemDto mockGroceryItemDto = TestModels.getMilkGroceryItemDto();

        // Mock product service behavior
        when(productService.findOrCreateProduct(anyString())).thenReturn(TestModels.getMilkProduct());

        // Mock auth service behavior
        when(authService.getCurrentUserId()).thenReturn(userId);

        // Mock user service behavior
        when(userService.getUserById(userId)).thenReturn(Optional.of(user));

        // Mock repository behavior
        when(groceryItemRepository.save(mockGroceryItem)).thenReturn(mockGroceryItem);

        // Mock mapper behavior
        when(groceryItemMapper.mapToEntity(eq(mockGroceryItemDto), eq(user), any())).thenReturn(mockGroceryItem);
        when(groceryItemMapper.mapToDto(mockGroceryItem)).thenReturn(mockGroceryItemDto);

        // Act
        GroceryItemDto result = groceryItemService.createGroceryItem(mockGroceryItemDto);

        // Assert
        assertNotNull(result);
        assertEquals(mockGroceryItemDto, result);
        verify(groceryItemRepository).save(mockGroceryItem);
        verify(pantryItemService).addGroceryItemToPantry(mockGroceryItem);
    }

    @Test
    @DisplayName("Test update grocery item")
    void updateGroceryItem() {
        // Arrange
        User user = TestModels.getUser();
        long userId = user.getId();
        GroceryItem mockGroceryItem = TestModels.getMilkGroceryItem();
        GroceryItemDto mockGroceryItemDto = TestModels.getMilkGroceryItemDto();

        // Mock product service behavior
        when(productService.findOrCreateProduct(anyString())).thenReturn(TestModels.getMilkProduct());

        // Mock auth service behavior
        when(authService.getCurrentUserId()).thenReturn(userId);

        // Mock user service behavior
        when(userService.getUserById(userId)).thenReturn(Optional.of(user));

        // Mock repository behavior
        when(groceryItemRepository.save(mockGroceryItem)).thenReturn(mockGroceryItem);

        // Mock mapper behavior
        when(groceryItemMapper.mapToEntityWithId(eq(mockGroceryItemDto), eq(user), any())).thenReturn(mockGroceryItem);
        when(groceryItemMapper.mapToDto(mockGroceryItem)).thenReturn(mockGroceryItemDto);

        // Act
        GroceryItemDto result = groceryItemService.updateGroceryItem(mockGroceryItemDto);

        // Assert
        assertNotNull(result);
        assertEquals(mockGroceryItemDto, result);
        verify(groceryItemRepository).save(mockGroceryItem);
        verify(pantryItemService).updateGroceryItemInPantry(mockGroceryItem, userId);
    }

    @Test
    @DisplayName("Test delete grocery item")
    void deleteGroceryItem() {
        // Arrange
        long itemId = TestModels.ID_1;
        long userId = TestModels.ID_1;

        // Mock auth service behavior
        when(authService.getCurrentUserId()).thenReturn(userId);

        // Act
        groceryItemService.deleteGroceryItem(itemId);

        // Assert
        verify(pantryItemService).deleteGroceryItemInPantry(itemId, userId);
        verify(groceryItemRepository).deleteByIdAndUserId(itemId, userId);
    }
}
