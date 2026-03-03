package com.cafe.erp.modules.catalogue.modifier_group.service.implementation;

import com.cafe.erp.common.exception.ResourceNotFoundException;
import com.cafe.erp.common.utils.AuditUtils;
import com.cafe.erp.modules.catalogue.modifier_group.dto.ModifierGroupRequestDTO;
import com.cafe.erp.modules.catalogue.modifier_group.dto.ModifierGroupResponseDTO;
import com.cafe.erp.modules.catalogue.modifier_group.dto.ModifierOptionRequestDTO;
import com.cafe.erp.modules.catalogue.modifier_group.dto.ModifierOptionResponseDTO;
import com.cafe.erp.modules.catalogue.modifier_group.entity.ModifierGroups;
import com.cafe.erp.modules.catalogue.modifier_group.entity.ModifierOption;
import com.cafe.erp.modules.catalogue.modifier_group.repository.ModifierGroupRepository;
import com.cafe.erp.modules.catalogue.modifier_group.repository.ModifierOptionsRepository;
import com.cafe.erp.modules.catalogue.modifier_group.service.ModifierGroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ModifierGroupServiceImpl implements ModifierGroupService {

    private final ModifierGroupRepository groupRepo;
    private final ModifierOptionsRepository optionRepo;

    // -----------------------------------------------------------------------
    // CREATE
    // -----------------------------------------------------------------------
    @Override
    public ModifierGroupResponseDTO createModifierGroup(ModifierGroupRequestDTO dto) {



        ModifierGroups group = ModifierGroups.builder()
                .type(dto.getType())
                .title(dto.getTitle())
                .shortName(dto.getShortName())
                .handle(dto.getHandle())
                .description(dto.getDescription())
                .sortOrder(dto.getSortOrder())
                .isActive(dto.getIsActive())
                .build();

        if(groupRepo.existsByHandle(group.getHandle())){
            throw new ResourceNotFoundException("Modifier group already exists with handle: " + group.getHandle());
        }

        ModifierGroups savedGroup = groupRepo.save(group);

        // Save options (if given)
        if (dto.getOptions() != null) {
            for (ModifierOptionRequestDTO optDto : dto.getOptions()) {
                ModifierOption option = ModifierOption.builder()
                        .modifierGroup(savedGroup)
                        .name(optDto.getName())
                        .priceModifier(optDto.getPriceModifier())
                        .sortOrder(optDto.getSortOrder())
                        .isActive(optDto.getIsActive())
                        .build();
                optionRepo.save(option);
            }
        }

        return toResponse(savedGroup);
    }

    // -----------------------------------------------------------------------
    // GET BY ID
    // -----------------------------------------------------------------------
    @Override
    public ModifierGroupResponseDTO getModifierGroupById(Long id) {
        ModifierGroups group = groupRepo.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new ResourceNotFoundException("Modifier group not found with id: " + id));

        return toResponse(group);
    }

    // -----------------------------------------------------------------------
    // GET ALL
    // -----------------------------------------------------------------------
    @Override
    public List<ModifierGroupResponseDTO> getAllModifierGroups() {
        return groupRepo.findAllByIsDeletedFalse()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    // -----------------------------------------------------------------------
    // UPDATE
    // -----------------------------------------------------------------------
    @Override
    public ModifierGroupResponseDTO updateModifierGroup(Long id, ModifierGroupRequestDTO dto) {
        ModifierGroups group = groupRepo.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new ResourceNotFoundException("Modifier group not found with id: " + id));

        // Update group fields
        group.setType(dto.getType());
        group.setTitle(dto.getTitle());
        group.setShortName(dto.getShortName());
        group.setHandle(dto.getHandle());
        group.setDescription(dto.getDescription());
        group.setSortOrder(dto.getSortOrder());
        group.setIsActive(dto.getIsActive());
        group.setUpdatedAt(LocalDateTime.now());
        group.setUpdatedBy(AuditUtils.getCurrentUser());

        ModifierGroups updatedGroup = groupRepo.save(group);

        // Replace options fully
        List<ModifierOption> existingOptions = optionRepo.findAllByModifierGroupAndIsDeletedFalse(updatedGroup);
        existingOptions.forEach(opt -> {
            opt.setIsDeleted(true);
            optionRepo.save(opt);
        });

        if (dto.getOptions() != null) {
            for (ModifierOptionRequestDTO optDto : dto.getOptions()) {
                ModifierOption newOption = ModifierOption.builder()
                        .modifierGroup(updatedGroup)
                        .name(optDto.getName())
                        .priceModifier(optDto.getPriceModifier())
                        .sortOrder(optDto.getSortOrder())
                        .isActive(optDto.getIsActive())
                        .build();
                optionRepo.save(newOption);
            }
        }

        return toResponse(updatedGroup);
    }

    // -----------------------------------------------------------------------
    // DELETE (SOFT DELETE)
    // -----------------------------------------------------------------------
    @Override
    public void deleteModifierGroup(Long id) {

        ModifierGroups group = groupRepo.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new ResourceNotFoundException("Modifier group not found with id: " + id));

        group.setDeletedAt(LocalDateTime.now());
        group.setDeletedBy(AuditUtils.getCurrentUser());
        group.setIsDeleted(true);
        groupRepo.save(group);

        List<ModifierOption> options = optionRepo.findAllByModifierGroup_IdAndIsDeletedFalse(id);
        for (ModifierOption opt : options) {
            opt.setIsDeleted(true);
            optionRepo.save(opt);
        }
    }

    // -----------------------------------------------------------------------
    // MAPPER: Entity → Response DTO
    // -----------------------------------------------------------------------
    private ModifierGroupResponseDTO toResponse(ModifierGroups group) {

        List<ModifierOption> options = optionRepo.findAllByModifierGroup_IdAndIsDeletedFalse(group.getId());

        List<ModifierOptionResponseDTO> optionDTOs = options.stream()
                .map(opt -> ModifierOptionResponseDTO.builder()
                        .id(opt.getId())
                        .name(opt.getName())
                        .priceModifier(opt.getPriceModifier())
                        .sortOrder(opt.getSortOrder())
                        .isActive(opt.getIsActive())
                        .createdAt(opt.getCreatedAt())
                        .updatedAt(opt.getUpdatedAt())
                        .createdBy(opt.getCreatedBy())
                        .updatedBy(opt.getUpdatedBy())
                        .build())
                .toList();

        return ModifierGroupResponseDTO.builder()
                .id(group.getId())
                .type(group.getType())
                .title(group.getTitle())
                .shortName(group.getShortName())
                .handle(group.getHandle())
                .description(group.getDescription())
                .sortOrder(group.getSortOrder())
                .isActive(group.getIsActive())
                .options(optionDTOs)
                .createdAt(group.getCreatedAt())
                .updatedAt(group.getUpdatedAt())
                .createdBy(group.getCreatedBy())
                .updatedBy(group.getUpdatedBy())
                .build();
    }
}
