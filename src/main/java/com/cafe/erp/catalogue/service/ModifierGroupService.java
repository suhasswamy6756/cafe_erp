package com.cafe.erp.catalogue.service;

import com.cafe.erp.catalogue.model.ModifierGroup;

import java.util.List;

public interface ModifierGroupService {

    ModifierGroup createModifierGroup(ModifierGroup modifierGroup);
    List<ModifierGroup> getAllModifierGroups();
    ModifierGroup getModifierGroupById(Long id);
    ModifierGroup updateModifierGroup(Long id, ModifierGroup modifierGroup);
    void deleteModifierGroup(Long id);
}
