package com.cafe.erp.modules.catalogue.modifier_group.repository;

import com.cafe.erp.modules.catalogue.modifier_group.entity.ModifierGroups;
import com.cafe.erp.modules.catalogue.modifier_group.entity.ModifierOption;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ModifierOptionsRepository extends JpaRepository<ModifierOption, Long> {
    // Fetch options for a group
    List<ModifierOption> findAllByModifierGroupAndIsDeletedFalse(ModifierGroups group);

    // Fetch options by group id (for batch operations)
    List<ModifierOption> findAllByModifierGroup_IdAndIsDeletedFalse(Long groupId);
}
