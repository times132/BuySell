package com.example.giveandtake.repository;

import com.example.giveandtake.model.entity.Category;
import com.example.giveandtake.model.entity.CategoryItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

}
