package com.pantrypal.grocerytracker.repository;

import com.pantrypal.grocerytracker.model.GroceryItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroceryItemRepository extends JpaRepository<GroceryItem, Long> {
}
