package com.buysell.repository;

import com.buysell.domain.entity.CategoryItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryItemsRepository extends JpaRepository<CategoryItem, Long> {

    CategoryItem findByItemName(String itemName);
}
