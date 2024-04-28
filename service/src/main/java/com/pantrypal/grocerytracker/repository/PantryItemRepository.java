package com.pantrypal.grocerytracker.repository;

import com.pantrypal.grocerytracker.model.PantryItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PantryItemRepository extends JpaRepository<PantryItem, Long> {
    Optional<PantryItem> findByGroceryItem_Id(Long groceryItemId);

    void deleteByGroceryItem_Id(Long groceryItemId);
}
