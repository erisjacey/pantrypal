package com.pantrypal.grocerytracker.repository;

import com.pantrypal.grocerytracker.model.PantryItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PantryItemRepository extends JpaRepository<PantryItem, Long> {
}
