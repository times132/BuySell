package com.example.giveandtake.repository;

import com.example.giveandtake.model.entity.CategoryItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryItemsRepository extends JpaRepository<CategoryItem, Long> {
    CategoryItem findByItemName(String itemName);
}
