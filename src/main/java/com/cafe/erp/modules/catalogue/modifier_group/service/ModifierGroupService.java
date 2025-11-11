package com.cafe.erp.modules.catalogue.modifier_group.service;

import com.cafe.erp.modules.catalogue.modifier_group.dto.ModifierGroupRequestDTO;
import com.cafe.erp.modules.catalogue.modifier_group.dto.ModifierGroupResponseDTO;

import java.util.List;

public interface ModifierGroupService {

    ModifierGroupResponseDTO createModifierGroup(ModifierGroupRequestDTO dto);

    ModifierGroupResponseDTO getModifierGroupById(Long id);

    List<ModifierGroupResponseDTO> getAllModifierGroups();

    ModifierGroupResponseDTO updateModifierGroup(Long id, ModifierGroupRequestDTO dto);

    void deleteModifierGroup(Long id);
}
