package com.pantrypal.grocerytracker.service.impl;

import com.pantrypal.grocerytracker.constants.Constants;
import com.pantrypal.grocerytracker.dto.ModifyAmountRequest;
import com.pantrypal.grocerytracker.model.GroceryItem;
import com.pantrypal.grocerytracker.repository.GroceryItemRepository;
import com.pantrypal.grocerytracker.service.GroceryItemService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GroceryItemServiceImpl implements GroceryItemService {
    private final GroceryItemRepository groceryItemRepository;

    @Autowired
    public GroceryItemServiceImpl(GroceryItemRepository groceryItemRepository) {
        this.groceryItemRepository = groceryItemRepository;
    }

    @Override
    public List<GroceryItem> getAllGroceryItems() {
        return groceryItemRepository.findAll();
    }

    @Override
    public Optional<GroceryItem> getGroceryItemById(Long id) {
        return groceryItemRepository.findById(id);
    }

    @Override
    public GroceryItem createGroceryItem(GroceryItem groceryItem) {
        return groceryItemRepository.save(groceryItem);
    }

    @Override
    public GroceryItem updateGroceryItem(GroceryItem updatedItem) {
        return groceryItemRepository.save(updatedItem);
    }

    @Override
    public void deleteGroceryItem(Long id) {
        groceryItemRepository.deleteById(id);
    }
}
