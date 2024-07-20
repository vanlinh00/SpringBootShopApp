package com.example.springboot_shop_app.services;

import com.example.springboot_shop_app.dto.CategoryDTO;
import com.example.springboot_shop_app.models.Category;

import java.util.List;

public interface ICategoryService {

    Category createCategory(CategoryDTO category);
    Category getCategoryById(long id);
    List<Category> getAllCategories();
    Category updateCategory(long categoryId,CategoryDTO category);
    void deleteCateGory(long id);

}
