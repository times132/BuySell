package com.buysell.repository;

import com.buysell.domain.DTO.CategoryItemDTO;
import com.buysell.domain.entity.Category;
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