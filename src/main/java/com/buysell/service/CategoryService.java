package com.buysell.service;

import com.buysell.repository.CategoryItemsRepository;
import com.buysell.repository.CategoryRepository;
import com.buysell.domain.entity.Category;
import com.buysell.domain.entity.CategoryItem;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CategoryService {

    private CategoryRepository categoryRepository;
    private CategoryItemsRepository categoryItemsRepository;

    public List<Category> getCategory() {
        return categoryRepository.findAll();
    }

    public List<CategoryItem> getItems(){
        return categoryItemsRepository.findAll();
    }
    //대분류 id로 소분류 리스트 가져오기
    public List<CategoryItem> getCategoryItems(Long id) {
        Category category = categoryRepository.findById(id).get();
        List<CategoryItem> items = category.getItems();
        return items;
    }
    //소분류 name 으로 대분류 category 가져오기
    public Category getCateItems(String btype) {
        CategoryItem items = categoryItemsRepository.findByItemName(btype);

        Category category = items.getCategory();
        return category;
    }

}
