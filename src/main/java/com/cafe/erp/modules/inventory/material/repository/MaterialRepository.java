package com.cafe.erp.modules.inventory.material.repository;


import com.cafe.erp.modules.inventory.material.entity.Material;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MaterialRepository extends JpaRepository<Material, Long> {
    List<Material> findByIsDeletedFalse();
}

