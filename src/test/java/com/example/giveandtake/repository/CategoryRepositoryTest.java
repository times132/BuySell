package com.example.giveandtake.repository;

import com.example.giveandtake.DTO.CategoryDTO;
import com.example.giveandtake.model.entity.Category;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CategoryRepositoryTest {

    //상위
    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    public void createCategory() {
        CategoryDTO categoryDTO = CategoryDTO.builder()
                .id(4L)
                .name("생활")
                .build();

        categoryRepository.save(categoryDTO.toEntity());
    }

}