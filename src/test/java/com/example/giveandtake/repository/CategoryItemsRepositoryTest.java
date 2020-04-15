package com.example.giveandtake.repository;

import com.example.giveandtake.DTO.CategoryDTO;
import com.example.giveandtake.DTO.CategoryItemDTO;
import com.example.giveandtake.model.entity.Category;
import com.example.giveandtake.model.entity.CategoryItem;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CategoryItemsRepositoryTest {
    @Autowired
    CategoryItemsRepository itemsRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Test
    public void createItems() {
        Category category = (Category) categoryRepository.findById(1L).get();
        CategoryItemDTO itemDTO = CategoryItemDTO.builder()
                .itemName("외식")
                .category(category)
                .build();

        itemsRepository.save(itemDTO.toEntity());
    }

}