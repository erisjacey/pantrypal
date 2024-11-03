package com.pantrypal.grocerytracker.repository;

import com.pantrypal.grocerytracker.model.GroceryItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface GroceryItemRepository extends JpaRepository<GroceryItem, Long> {
    List<GroceryItem> findByUserId(Long userId);
    Optional<GroceryItem> findByIdAndUserId(Long id, Long userId);
    void deleteByIdAndUserId(Long id, Long userId);
}
