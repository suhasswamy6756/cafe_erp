package com.cafe.erp.catalogue.service.impl;

import com.cafe.erp.catalogue.dto.ModifierDTO;
import com.cafe.erp.catalogue.dto.ModifierGroupDTO;
import com.cafe.erp.catalogue.model.Modifier;
import com.cafe.erp.catalogue.model.ModifierGroup;
import com.cafe.erp.catalogue.repository.ModifierGroupRepository;
import com.cafe.erp.catalogue.service.ModifierGroupService;
import com.cafe.erp.common.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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

    public ModifierGroupDTO getModifierGroupWithModifiers(Long id) {
        ModifierGroup group = modifierGroupRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Modifier Group not found"));

        List<ModifierDTO> modifierDTOs = group.getModifiers().stream()
                .filter(Modifier::getActive) // ✅ only active modifiers
                .map(mod -> ModifierDTO.builder()
                        .id(mod.getId())
                        .modifierGroupId(mod.getId())
                        .title(mod.getTitle())
                        .shortName(mod.getShortName())
                        .foodType(mod.getFoodType())
                        .defaultSalePrice(mod.getDefaultSalePrice())
                        .sortOrder(mod.getSortOrder())
                        .isDefault(mod.getIsDefault())
                        .active(mod.getActive())
                        .build())
                .collect(Collectors.toList());

        return ModifierGroupDTO.builder()
                .id(group.getId())
                .title(group.getTitle())
                .shortName(group.getShortName())
                .handle(group.getHandle())
                .groupType(group.getGroupType())
                .description(group.getDescription())
                .sortOrder(group.getSortOrder())
                .active(group.getActive())
                .modifiers(modifierDTOs)
                .build();
    }
}
