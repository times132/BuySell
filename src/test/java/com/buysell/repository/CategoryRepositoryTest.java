package com.buysell.repository;

import com.buysell.domain.DTO.CategoryDTO;
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