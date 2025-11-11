package com.cafe.erp.modules.catalogue.category.service;

import com.cafe.erp.modules.catalogue.category.dto.CategoryRequestDTO;
import com.cafe.erp.modules.catalogue.category.dto.CategoryResponseDTO;

import java.util.List;

public interface CategoryService {

    CategoryResponseDTO createCategory(CategoryRequestDTO categoryRequestDTO);

    List<CategoryResponseDTO> getAllCategories();

    CategoryResponseDTO getCategoryById(Long id);

    CategoryResponseDTO updateCategory(Long id, CategoryRequestDTO categoryRequestDTO);

    void deleteCategory(Long id);

}
