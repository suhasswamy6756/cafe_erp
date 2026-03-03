package com.cafe.erp.modules.inventory.material.repository;


import com.cafe.erp.modules.inventory.material.entity.Material;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MaterialRepository extends JpaRepository<Material, Long> {
    @Query("""
       SELECT m
       FROM Material m
       JOIN FETCH m.category
       WHERE m.isDeleted = false
       """)
    List<Material> findAllActiveWithCategory();
    Optional<Material> findByMaterialIdAndIsDeletedFalse(Long materialId);
}

