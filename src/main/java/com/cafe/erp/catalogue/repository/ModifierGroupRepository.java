package com.cafe.erp.catalogue.repository;

import com.cafe.erp.catalogue.model.ModifierGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ModifierGroupRepository extends JpaRepository<ModifierGroup, Long> {
}
