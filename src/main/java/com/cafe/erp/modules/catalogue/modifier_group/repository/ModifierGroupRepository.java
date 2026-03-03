package com.cafe.erp.modules.catalogue.modifier_group.repository;

import com.cafe.erp.modules.catalogue.modifier_group.entity.ModifierGroups;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ModifierGroupRepository extends JpaRepository<ModifierGroups, Long> {
    List<ModifierGroups> findAllByIsDeletedFalse();

    Optional<ModifierGroups> findByIdAndIsDeletedFalse(Long id);

    boolean existsByHandle(String handle);

    boolean existsByTitle(String title);
}
