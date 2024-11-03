package com.pantrypal.grocerytracker.repository;

import com.pantrypal.grocerytracker.model.PantryItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PantryItemRepository extends JpaRepository<PantryItem, Long> {
    List<PantryItem> findByUserId(Long userId);
    Optional<PantryItem> findByIdAndUserId(Long id, Long userId);
    Optional<PantryItem> findByGroceryItemIdAndUserId(Long groceryItemId, Long userId);
    void deleteByGroceryItemIdAndUserId(Long groceryItemId, Long userId);
}
