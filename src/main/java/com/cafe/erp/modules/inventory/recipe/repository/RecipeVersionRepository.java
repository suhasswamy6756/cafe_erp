package com.cafe.erp.modules.inventory.recipe.repository;

import com.cafe.erp.modules.inventory.recipe.entity.RecipeVersions;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RecipeVersionRepository extends JpaRepository<RecipeVersions, Long> {
    List<RecipeVersions> findByRecipe_RecipeIdAndIsDeletedFalse(Long recipeId);
    Optional<RecipeVersions> findByRecipe_RecipeIdAndIsDefaultTrueAndIsDeletedFalse(Long recipeId);
    List<RecipeVersions> findByRecipe_RecipeIdOrderByVersionNumberAsc(Long recipeId);
}
