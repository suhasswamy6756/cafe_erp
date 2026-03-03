package com.cafe.erp.modules.catalogue.charges.service;


import com.cafe.erp.common.exception.BadRequestException;
import com.cafe.erp.common.exception.ResourceNotFoundException;
import com.cafe.erp.common.utils.AuditUtils;
import com.cafe.erp.modules.catalogue.charges.dto.ChargeRequestDTO;
import com.cafe.erp.modules.catalogue.charges.dto.ChargeResponseDTO;
import com.cafe.erp.modules.catalogue.charges.entity.Charges;
import com.cafe.erp.modules.catalogue.charges.entity.ItemCharge;
import com.cafe.erp.modules.catalogue.charges.repository.ChargeRepository;
import com.cafe.erp.modules.catalogue.charges.repository.ItemChargeRepository;
import com.cafe.erp.modules.catalogue.item.entity.Item;
import com.cafe.erp.modules.catalogue.item.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChargeServiceImpl implements ChargeService {

    private final ChargeRepository chargeRepo;
    private final ItemRepository itemRepo;
    private final ItemChargeRepository itemChargeRepo;

    @Override
    @Transactional
    public ChargeResponseDTO createCharge(ChargeRequestDTO dto) {

        if (chargeRepo.existsByName(dto.getName())) {
            throw new BadRequestException("Charges name already exists");
        }

        if (chargeRepo.existsByHandle(dto.getHandle())) {
            throw new BadRequestException("Charges handle already exists");
        }

        Charges charge = Charges.builder()
                .name(dto.getName())
                .handle(dto.getHandle())
                .description(dto.getDescription())
                .type(dto.getType())
                .value(dto.getValue())
                .isTaxable(dto.getIsTaxable())
                .isActive(dto.getIsActive())
                .build();

        Charges saved = chargeRepo.save(charge);

        // Insert mapping
        if (dto.getItemIds() != null) {
            for (Long itemId : dto.getItemIds()) {

                Item item = itemRepo.findByIdAndIsDeletedFalse(itemId)
                        .orElseThrow(() -> new ResourceNotFoundException("Item not found: " + itemId));

                ItemCharge mapping = ItemCharge.builder()
                        .item(item)
                        .charge(saved)
                        .sortOrder(0)
                        .build();

                itemChargeRepo.save(mapping);
            }
        }

        return toDTO(saved);
    }

    @Override
    @Transactional
    public ChargeResponseDTO updateCharge(Long id, ChargeRequestDTO dto) {

        Charges charge = chargeRepo.findByChargeIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new ResourceNotFoundException("Charges not found"));

        charge.setName(dto.getName());
        charge.setHandle(dto.getHandle());
        charge.setDescription(dto.getDescription());
        charge.setType(dto.getType());
        charge.setValue(dto.getValue());
        charge.setIsTaxable(dto.getIsTaxable());
        charge.setIsActive(dto.getIsActive());
        charge.setUpdatedAt(LocalDateTime.now());
        charge.setUpdatedBy(AuditUtils.getCurrentUser());

        Charges updated = chargeRepo.save(charge);

        // Soft delete old mappings
        itemChargeRepo.findAllByCharge_ChargeIdAndIsDeletedFalse(id)
                .forEach(map -> {
                    map.setIsDeleted(true);
                    map.setDeletedAt(LocalDateTime.now());
                    map.setDeletedBy(AuditUtils.getCurrentUser());
                    itemChargeRepo.save(map);
                });

        // Insert fresh mappings
        if (dto.getItemIds() != null) {
            for (Long itemId : dto.getItemIds()) {

                Item item = itemRepo.findByIdAndIsDeletedFalse(itemId)
                        .orElseThrow(() -> new ResourceNotFoundException("Item not found: " + itemId));

                ItemCharge map = ItemCharge.builder()
                        .item(item)
                        .charge(updated)
                        .sortOrder(0)
                        .build();

                itemChargeRepo.save(map);
            }
        }

        return toDTO(updated);
    }

    @Override
    public List<ChargeResponseDTO> getAllCharges() {
        return chargeRepo.findAllByIsDeletedFalse()
                .stream()
                .map(this::toDTO)
                .toList();
    }

    @Override
    public ChargeResponseDTO getChargeById(Long id) {
        return chargeRepo.findByChargeIdAndIsDeletedFalse(id)
                .map(this::toDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Charges not found"));
    }

    @Override
    @Transactional
    public void deleteCharge(Long id) {

        Charges charge = chargeRepo.findByChargeIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new ResourceNotFoundException("Charges not found"));

        charge.setIsDeleted(true);
        charge.setDeletedAt(LocalDateTime.now());
        charge.setDeletedBy(AuditUtils.getCurrentUser());
        chargeRepo.save(charge);

        itemChargeRepo.findAllByCharge_ChargeIdAndIsDeletedFalse(id)
                .forEach(map -> {
                    map.setIsDeleted(true);
                    map.setDeletedAt(LocalDateTime.now());
                    map.setDeletedBy(AuditUtils.getCurrentUser());
                    itemChargeRepo.save(map);
                });
    }


    private ChargeResponseDTO toDTO(Charges charge) {

        List<Long> itemIds = itemChargeRepo
                .findAllByCharge_ChargeIdAndIsDeletedFalse(charge.getChargeId())
                .stream()
                .map(mapping -> mapping.getItem().getId())
                .toList();

        return ChargeResponseDTO.builder()
                .chargeId(charge.getChargeId())
                .name(charge.getName())
                .handle(charge.getHandle())
                .description(charge.getDescription())
                .type(charge.getType())
                .value(charge.getValue())
                .isTaxable(charge.getIsTaxable())
                .isActive(charge.getIsActive())
                .itemIds(itemIds)
                .createdAt(charge.getCreatedAt())
                .updatedAt(charge.getUpdatedAt())
                .createdBy(charge.getCreatedBy())
                .updatedBy(charge.getUpdatedBy())
                .build();
    }
}

