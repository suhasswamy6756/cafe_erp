package com.cafe.erp.modules.catalogue.taxes.service.implementation;

import com.cafe.erp.common.exception.BadRequestException;
import com.cafe.erp.common.exception.ResourceNotFoundException;
import com.cafe.erp.common.utils.AuditUtils;
import com.cafe.erp.modules.catalogue.item.entity.Item;
import com.cafe.erp.modules.catalogue.item.repository.ItemRepository;
import com.cafe.erp.modules.catalogue.taxes.dto.TaxRequestDTO;
import com.cafe.erp.modules.catalogue.taxes.dto.TaxResponseDTO;
import com.cafe.erp.modules.catalogue.taxes.entity.ItemTax;
import com.cafe.erp.modules.catalogue.taxes.entity.Tax;
import com.cafe.erp.modules.catalogue.taxes.respository.ItemTaxRepository;
import com.cafe.erp.modules.catalogue.taxes.respository.TaxRepository;
import com.cafe.erp.modules.catalogue.taxes.service.TaxService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TaxServiceImpl implements TaxService {

    private final TaxRepository taxRepo;
    private final ItemRepository itemRepo;
    private final ItemTaxRepository itemTaxRepo;

    @Override
    public TaxResponseDTO createTax(TaxRequestDTO dto) {

        if (taxRepo.existsByName(dto.getName())) {
            throw new BadRequestException("Tax name already exists");
        }

        if (taxRepo.existsByHandle(dto.getHandle())) {
            throw new BadRequestException("Tax handle already exists");
        }

        Tax tax = Tax.builder()
                .name(dto.getName())
                .handle(dto.getHandle())
                .description(dto.getDescription())
                .type(dto.getType())
                .value(dto.getValue())
                .isActive(dto.getIsActive())
                .build();

        Tax saved = taxRepo.save(tax);

        // ✅ Add item associations
        if (dto.getItemIds() != null) {
            for (Long itemId : dto.getItemIds()) {

                Item item = itemRepo.findByIdAndIsDeletedFalse(itemId)
                        .orElseThrow(() -> new ResourceNotFoundException("Item not found: " + itemId));

                ItemTax mapping = ItemTax.builder()
                        .item(item)
                        .tax(saved)
                        .sortOrder(0)
                        .build();

                itemTaxRepo.save(mapping);
            }
        }

        return toDTO(saved);
    }


    @Override
    public TaxResponseDTO updateTax(Long id, TaxRequestDTO dto) {

        Tax tax = taxRepo.findByTaxIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tax not found"));

        tax.setName(dto.getName());
        tax.setHandle(dto.getHandle());
        tax.setDescription(dto.getDescription());
        tax.setType(dto.getType());
        tax.setValue(dto.getValue());
        tax.setIsActive(dto.getIsActive());
        tax.setUpdatedAt(LocalDateTime.now());
        tax.setUpdatedBy(AuditUtils.getCurrentUser());

        Tax updated = taxRepo.save(tax);

        // ✅ Soft-delete old mappings
        itemTaxRepo.findAllByTax_TaxIdAndIsDeletedFalse(id).forEach(map -> {
            map.setIsDeleted(true);
            map.setDeletedAt(LocalDateTime.now());
            map.setDeletedBy(AuditUtils.getCurrentUser());
            itemTaxRepo.save(map);
        });

        // ✅ Insert updated mappings
        if (dto.getItemIds() != null) {
            for (Long itemId : dto.getItemIds()) {

                Item item = itemRepo.findByIdAndIsDeletedFalse(itemId)
                        .orElseThrow(() -> new ResourceNotFoundException("Item not found: " + itemId));

                ItemTax mapping = ItemTax.builder()
                        .item(item)
                        .tax(updated)
                        .sortOrder(0)
                        .build();

                itemTaxRepo.save(mapping);
            }
        }

        return toDTO(updated);
    }


    @Override
    public List<TaxResponseDTO> getAllTaxes() {
        return taxRepo.findAllByIsDeletedFalse()
                .stream()
                .map(this::toDTO)
                .toList();
    }


    @Override
    public TaxResponseDTO getTaxById(Long id) {
        Tax tax = taxRepo.findByTaxIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tax not found"));
        return toDTO(tax);
    }


    @Override
    public void deleteTax(Long id) {

        Tax tax = taxRepo.findByTaxIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tax not found"));

        tax.setIsDeleted(true);
        tax.setDeletedAt(LocalDateTime.now());
        tax.setDeletedBy(AuditUtils.getCurrentUser());

        taxRepo.save(tax);

        // ✅ Soft-delete all item associations
        itemTaxRepo.findAllByTax_TaxIdAndIsDeletedFalse(id).forEach(map -> {
            map.setIsDeleted(true);
            map.setDeletedAt(LocalDateTime.now());
            map.setDeletedBy(AuditUtils.getCurrentUser());
            itemTaxRepo.save(map);
        });
    }


    private TaxResponseDTO toDTO(Tax tax) {

        List<Long> itemIds = itemTaxRepo.findAllByTax_TaxIdAndIsDeletedFalse(tax.getTaxId())
                .stream()
                .map(mapping -> mapping.getItem().getId())
                .toList();

        return TaxResponseDTO.builder()
                .taxId(tax.getTaxId())
                .name(tax.getName())
                .handle(tax.getHandle())
                .description(tax.getDescription())
                .type(tax.getType())
                .value(tax.getValue())
                .isActive(tax.getIsActive())
                .createdAt(tax.getCreatedAt())
                .updatedAt(tax.getUpdatedAt())
                .createdBy(tax.getCreatedBy())
                .updatedBy(tax.getUpdatedBy())
                .itemIds(itemIds)
                .build();
    }
}
