package com.cafe.erp.catalogue.service.impl;

import com.cafe.erp.catalogue.model.ModifierGroup;
import com.cafe.erp.catalogue.repository.ModifierGroupRepository;
import com.cafe.erp.catalogue.service.ModifierGroupService;
import com.cafe.erp.common.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ModifierGroupServiceImpl implements ModifierGroupService {

    private final ModifierGroupRepository modifierGroupRepository;

    @Override
    public ModifierGroup createModifierGroup(ModifierGroup modifierGroup) {
        return modifierGroupRepository.save(modifierGroup);
    }

    @Override
    public List<ModifierGroup> getAllModifierGroups() {
        return modifierGroupRepository.findAll();
    }

    @Override
    public ModifierGroup getModifierGroupById(Long id) {
        return modifierGroupRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ModifierGroup not found"));
    }

    @Override
    public ModifierGroup updateModifierGroup(Long id, ModifierGroup modifierGroup) {
        return modifierGroupRepository.findById(id)
                .map(modifierGroup1 -> {
                    modifierGroup1.setTitle(modifierGroup.getTitle());
                    modifierGroup1.setDescription(modifierGroup.getDescription());
                    return modifierGroupRepository.save(modifierGroup1);
                })
                .orElseThrow(() -> new ResourceNotFoundException("ModifierGroup not found"));
    }

    @Override
    public void deleteModifierGroup(Long id) {
        modifierGroupRepository.deleteById(id);
    }


}
