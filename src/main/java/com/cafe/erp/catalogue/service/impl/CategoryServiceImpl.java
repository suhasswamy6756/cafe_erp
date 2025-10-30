package com.cafe.erp.catalogue.service.impl;

import com.cafe.erp.catalogue.model.Category;
import com.cafe.erp.catalogue.repository.CategoryRepository;
import com.cafe.erp.catalogue.service.CategoryService;
import com.cafe.erp.common.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    public Category createCategory(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll()
                .stream()
                .filter(c -> !Boolean.TRUE.equals(c.getIsDeleted()))
                .toList();
    }

    @Override
    public Category getCategoryById(Long id) {
        return categoryRepository.findById(id)
                .filter(c -> !Boolean.TRUE.equals(c.getIsDeleted()))
                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));
    }

    @Override
    public Category updateCategory(Long id, Category category) {
        Category existing = getCategoryById(id);
        existing.setName(category.getName());
        existing.setDescription(category.getDescription());
        existing.setActive(category.getActive());
        return categoryRepository.save(existing);
    }

    @Override
    public void softDeleteCategory(Long id, String deletedBy) {
        Category existing = getCategoryById(id);
        existing.setDeletedAt(LocalDateTime.now());
        existing.setDeletedBy(deletedBy);
        existing.setIsDeleted(true);
        categoryRepository.save(existing);
    }
}
