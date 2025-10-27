package com.cafe.erp.catalogue.service;

import com.cafe.erp.catalogue.model.Category;
import java.util.List;

public interface CategoryService {
    Category createCategory(Category category);
    List<Category> getAllCategories();
    Category getCategoryById(Long id);
    Category updateCategory(Long id, Category category);
    void softDeleteCategory(Long id, String deletedBy);
}
