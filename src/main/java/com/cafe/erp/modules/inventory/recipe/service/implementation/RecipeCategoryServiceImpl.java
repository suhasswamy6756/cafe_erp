package com.cafe.erp.modules.inventory.recipe.service.implementation;

import com.cafe.erp.modules.inventory.recipe.dto.RecipeCategoryRequest;
import com.cafe.erp.modules.inventory.recipe.dto.RecipeCategoryResponse;
import com.cafe.erp.modules.inventory.recipe.entity.RecipeCategory;
import com.cafe.erp.modules.inventory.recipe.repository.RecipeCategoryRepository;
import com.cafe.erp.modules.inventory.recipe.service.RecipeCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

import static com.cafe.erp.common.utils.AuditUtils.getCurrentUser;

@Service
@RequiredArgsConstructor
public class RecipeCategoryServiceImpl implements RecipeCategoryService {

    private final RecipeCategoryRepository repo;

    @Override
    public RecipeCategoryResponse createCategory(RecipeCategoryRequest req) {

        if (repo.existsByRecipeCategoryNameIgnoreCase(req.getName())) {
            throw new RuntimeException("Category already exists");
        }

        var category = repo.save(RecipeCategory.builder()
                .recipeCategoryName(req.getName())
                .recipeCategoryDescription(req.getDescription())
                .build());

        return toDTO(category);
    }

    @Override
    public List<RecipeCategoryResponse> getAllCategories() {
        return repo.findByIsDeletedFalse().stream().map(this::toDTO).toList();
    }

    @Override
    public RecipeCategoryResponse getCategory(Long id) {
        return toDTO(repo.findByRecipeCategoryIdAndIsDeletedFalse(id));
    }

    @Override
    public RecipeCategoryResponse updateCategory(Long id, RecipeCategoryRequest req) {
        var category = repo.findById(id).orElseThrow(() -> new RuntimeException("Not found"));
        category.setRecipeCategoryName(req.getName());
        category.setRecipeCategoryDescription(req.getDescription());
        return toDTO(repo.save(category));
    }

    @Override
    public void deleteCategory(Long id) {
        var category = repo.findById(id).orElseThrow();
        category.setIsDeleted(true);
        category.setDeletedAt(LocalDateTime.now());
        category.setDeletedBy(getCurrentUser());
        repo.save(category);
    }

    private RecipeCategoryResponse toDTO(RecipeCategory c) {
        var dto = new RecipeCategoryResponse();
        dto.setRecipeCategoryId(c.getRecipeCategoryId());
        dto.setRecipeCategoryName(c.getRecipeCategoryName());
        dto.setRecipeCategoryDescription(c.getRecipeCategoryDescription());
        dto.setCreatedAt(c.getCreatedAt());
        dto.setCreatedBy(c.getCreatedBy());
        dto.setUpdatedAt(c.getUpdatedAt());
        dto.setUpdatedBy(c.getUpdatedBy());
        return dto;
    }
}

