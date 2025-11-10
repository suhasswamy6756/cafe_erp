package com.cafe.erp.modules.catalogue.category.service.implementation;

import com.cafe.erp.common.exception.ResourceNotFoundException;
import com.cafe.erp.modules.catalogue.category.dto.CategoryRequestDTO;
import com.cafe.erp.modules.catalogue.category.dto.CategoryResponseDTO;
import com.cafe.erp.modules.catalogue.category.entity.Category;
import com.cafe.erp.modules.catalogue.category.repository.CategoryRepository;
import com.cafe.erp.modules.catalogue.category.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    public CategoryResponseDTO createCategory(CategoryRequestDTO dto) {

        Category category = Category.builder()
                .name(dto.getName())
                .shortName(dto.getShortName())
                .handle(dto.getHandle())
                .description(dto.getDescription())
                .parentCategoryId(dto.getParentCategoryId())
                .kotGroup(dto.getKotGroup())
                .timingGroupId(dto.getTimingGroupId())
                .sortOrder(dto.getSortOrder())
                .active(dto.isActive())
                .build();

        // ✅ Save to DB
        Category saved = categoryRepository.save(category);

        // ✅ Convert To Response DTO
        return CategoryResponseDTO.builder()
                .id(saved.getId())
                .name(saved.getName())
                .shortName(saved.getShortName())
                .handle(saved.getHandle())
                .description(saved.getDescription())
                .parentCategoryId(saved.getParentCategoryId())
                .kotGroup(saved.getKotGroup())
                .timingGroupId(saved.getTimingGroupId())
                .sortOrder(saved.getSortOrder())
                .active(saved.isActive())
                .createdBy(saved.getCreatedBy())
                .updatedBy(saved.getUpdatedBy())
                .createdAt(saved.getCreatedAt())
                .updatedAt(saved.getUpdatedAt())
                .build();
    }

    @Override
    public List<CategoryResponseDTO> getAllCategories() {
        return categoryRepository.findAllByIsDeletedFalse()
                .stream()
                .map(cat -> CategoryResponseDTO.builder()
                        .id(cat.getId())
                        .name(cat.getName())
                        .shortName(cat.getShortName())
                        .handle(cat.getHandle())
                        .description(cat.getDescription())
                        .parentCategoryId(cat.getParentCategoryId())
                        .kotGroup(cat.getKotGroup())
                        .timingGroupId(cat.getTimingGroupId())
                        .sortOrder(cat.getSortOrder())
                        .active(cat.isActive())
                        .createdBy(cat.getCreatedBy())
                        .updatedBy(cat.getUpdatedBy())
                        .createdAt(cat.getCreatedAt())
                        .updatedAt(cat.getUpdatedAt())
                        .build())
                .toList();
    }

    @Override
    public CategoryResponseDTO getCategoryById(Long id) {
        Category category = categoryRepository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));

        return CategoryResponseDTO.builder()
                .id(category.getId())
                .name(category.getName())
                .shortName(category.getShortName())
                .handle(category.getHandle())
                .description(category.getDescription())
                .parentCategoryId(category.getParentCategoryId())
                .kotGroup(category.getKotGroup())
                .timingGroupId(category.getTimingGroupId())
                .sortOrder(category.getSortOrder())
                .active(category.isActive())
                .createdBy(category.getCreatedBy())
                .updatedBy(category.getUpdatedBy())
                .createdAt(category.getCreatedAt())
                .updatedAt(category.getUpdatedAt())
                .build();
    }

    @Override
    public CategoryResponseDTO updateCategory(Long id, CategoryRequestDTO dto) {
        Category category = categoryRepository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));

        category.setName(dto.getName());
        category.setShortName(dto.getShortName());
        category.setHandle(dto.getHandle());
        category.setDescription(dto.getDescription());
        category.setParentCategoryId(dto.getParentCategoryId());
        category.setKotGroup(dto.getKotGroup());
        category.setTimingGroupId(dto.getTimingGroupId());
        category.setSortOrder(dto.getSortOrder());
        category.setActive(dto.isActive());

        Category saved = categoryRepository.save(category);

        return getCategoryById(saved.getId());
    }

    @Override
    public void deleteCategory(Long id) {
        Category category = categoryRepository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));

        category.setIsDeleted(true);
        category.setDeletedAt(LocalDateTime.now());
        category.setDeletedBy( SecurityContextHolder.getContext().getAuthentication().getName()); // we'll explain this below

        categoryRepository.save(category);
    }

}

