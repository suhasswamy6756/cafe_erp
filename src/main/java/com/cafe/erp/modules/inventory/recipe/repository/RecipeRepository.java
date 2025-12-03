package com.cafe.erp.modules.inventory.recipe.repository;

import com.cafe.erp.modules.inventory.recipe.entity.Recipes;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface RecipeRepository extends JpaRepository<Recipes, Long> {
    List<Recipes> findByIsDeletedFalse();
    Optional<Recipes> findByRecipeIdAndIsDeletedFalse(Long id);
    boolean existsByRecipeNameIgnoreCase(String name);

}






