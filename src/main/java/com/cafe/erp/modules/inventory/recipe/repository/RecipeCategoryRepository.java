package com.cafe.erp.modules.inventory.recipe.repository;

import com.cafe.erp.modules.inventory.recipe.entity.RecipeCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecipeCategoryRepository extends JpaRepository<RecipeCategory, Long> {
    boolean existsByRecipeCategoryNameIgnoreCase(String name);
    List<RecipeCategory> findByIsDeletedFalse();
    RecipeCategory findByRecipeCategoryIdAndIsDeletedFalse(Long id);
}

