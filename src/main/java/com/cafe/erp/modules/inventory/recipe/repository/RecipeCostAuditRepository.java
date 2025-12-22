package com.cafe.erp.modules.inventory.recipe.repository;

import com.cafe.erp.modules.inventory.recipe.entity.RecipeCostAudit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RecipeCostAuditRepository extends JpaRepository<RecipeCostAudit, Long> {
    Optional<RecipeCostAudit> findTopByVersions_VersionIdOrderByCalculatedAtDesc(Long versionId);

    List<RecipeCostAudit> findByVersions_VersionIdOrderByCalculatedAtDesc(Long versionId);
}


