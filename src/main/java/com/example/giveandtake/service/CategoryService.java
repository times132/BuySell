package com.example.giveandtake.service;

import com.example.giveandtake.model.entity.Category;
import com.example.giveandtake.model.entity.CategoryItem;
import com.example.giveandtake.repository.CategoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Scanner;

@Service
@AllArgsConstructor
public class CategoryService {

    private CategoryRepository categoryRepository;

    public List<Category> getCategory() {
        return categoryRepository.findAll();
    }

    public List<CategoryItem> getCategoryItems(Long id) {
        Category category = categoryRepository.findById(id).get();
        List<CategoryItem> items = category.getItems();
        return items;
    }
}
