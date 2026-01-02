package com.cafe.erp.modules.inventory.recipe.repository;

import com.cafe.erp.modules.inventory.recipe.entity.RecipeItems;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RecipeItemRepository extends JpaRepository<RecipeItems, Long> {
    List<RecipeItems> findByVersion_VersionId(Long versionId);
    List<RecipeItems> findByMaterial_MaterialId(Long versionId);
}
