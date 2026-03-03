package com.cafe.erp.modules.catalogue.discount.service.implementation;

import com.cafe.erp.common.exception.BadRequestException;
import com.cafe.erp.common.exception.ResourceNotFoundException;
import com.cafe.erp.common.utils.AuditUtils;
import com.cafe.erp.modules.catalogue.discount.dto.DiscountRequestDTO;
import com.cafe.erp.modules.catalogue.discount.dto.DiscountResponseDTO;
import com.cafe.erp.modules.catalogue.discount.entity.Discounts;
import com.cafe.erp.modules.catalogue.discount.entity.ItemDiscount;
import com.cafe.erp.modules.catalogue.discount.repository.DiscountRepository;
import com.cafe.erp.modules.catalogue.discount.repository.ItemDiscountRepository;
import com.cafe.erp.modules.catalogue.discount.service.DiscountService;
import com.cafe.erp.modules.catalogue.item.entity.Item;
import com.cafe.erp.modules.catalogue.item.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DiscountServiceImpl implements DiscountService {

    private final DiscountRepository discountRepo;
    private final ItemRepository itemRepo;
    private final ItemDiscountRepository mappingRepo;

    @Override
    public DiscountResponseDTO createDiscount(DiscountRequestDTO dto) {

        if (discountRepo.existsByName(dto.getName()))
            throw new BadRequestException("Discount name already exists");

        if (discountRepo.existsByHandle(dto.getHandle()))
            throw new BadRequestException("Discount handle already exists");

        Discounts discount = Discounts.builder()
                .name(dto.getName())
                .handle(dto.getHandle())
                .description(dto.getDescription())
                .type(dto.getType())
                .value(dto.getValue())
                .startDate(dto.getStartDate())
                .endDate(dto.getEndDate())
                .isAutoApply(dto.getIsAutoApply())
                .isActive(dto.getIsActive())
                .build();

        Discounts saved = discountRepo.save(discount);

        saveItemMappings(saved, dto.getItemIds());

        return toDTO(saved);
    }


    @Override
    public DiscountResponseDTO updateDiscount(Long id, DiscountRequestDTO dto) {

        Discounts discount = discountRepo.findByDiscountIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new ResourceNotFoundException("Discount not found"));

        discount.setName(dto.getName());
        discount.setHandle(dto.getHandle());
        discount.setDescription(dto.getDescription());
        discount.setType(dto.getType());
        discount.setValue(dto.getValue());
        discount.setStartDate(dto.getStartDate());
        discount.setEndDate(dto.getEndDate());
        discount.setIsAutoApply(dto.getIsAutoApply());
        discount.setIsActive(dto.getIsActive());
        discount.setUpdatedAt(LocalDateTime.now());
        discount.setUpdatedBy(AuditUtils.getCurrentUser());

        Discounts updated = discountRepo.save(discount);

        // Soft delete old mappings
        mappingRepo.findAllByDiscount_DiscountIdAndIsDeletedFalse(id)
                .forEach(m -> {
                    m.setIsDeleted(true);
                    m.setDeletedAt(LocalDateTime.now());
                    m.setDeletedBy(AuditUtils.getCurrentUser());
                    mappingRepo.save(m);
                });

        saveItemMappings(updated, dto.getItemIds());

        return toDTO(updated);
    }

    private void saveItemMappings(Discounts discount, List<Long> itemIds) {
        if (itemIds == null) return;

        for (Long itemId : itemIds) {

            Item item = itemRepo.findByIdAndIsDeletedFalse(itemId)
                    .orElseThrow(() -> new ResourceNotFoundException("Item not found: " + itemId));

            ItemDiscount map = ItemDiscount.builder()
                    .item(item)
                    .discount(discount)
                    .sortOrder(0)
                    .build();

            mappingRepo.save(map);
        }
    }


    @Override
    public List<DiscountResponseDTO> getAllDiscounts() {
        return discountRepo.findAllByIsDeletedFalse()
                .stream()
                .map(this::toDTO)
                .toList();
    }

    @Override
    public DiscountResponseDTO getDiscountById(Long id) {
        return discountRepo.findByDiscountIdAndIsDeletedFalse(id)
                .map(this::toDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Discount not found"));
    }

    @Override
    public void deleteDiscount(Long id) {

        Discounts discount = discountRepo.findByDiscountIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new ResourceNotFoundException("Discount not found"));

        discount.setIsDeleted(true);
        discount.setDeletedAt(LocalDateTime.now());
        discount.setDeletedBy(AuditUtils.getCurrentUser());

        discountRepo.save(discount);

        mappingRepo.findAllByDiscount_DiscountIdAndIsDeletedFalse(id)
                .forEach(m -> {
                    m.setIsDeleted(true);
                    m.setDeletedAt(LocalDateTime.now());
                    m.setDeletedBy(AuditUtils.getCurrentUser());
                    mappingRepo.save(m);
                });
    }


    private DiscountResponseDTO toDTO(Discounts d) {

        List<Long> itemIds = mappingRepo.findAllByDiscount_DiscountIdAndIsDeletedFalse(d.getDiscountId())
                .stream()
                .map(m -> m.getItem().getId())
                .toList();

        return DiscountResponseDTO.builder()
                .discountId(d.getDiscountId())
                .name(d.getName())
                .handle(d.getHandle())
                .description(d.getDescription())
                .type(d.getType())
                .value(d.getValue())
                .startDate(d.getStartDate())
                .endDate(d.getEndDate())
                .isAutoApply(d.getIsAutoApply())
                .isActive(d.getIsActive())
                .createdAt(d.getCreatedAt())
                .updatedAt(d.getUpdatedAt())
                .createdBy(d.getCreatedBy())
                .updatedBy(d.getUpdatedBy())
                .itemIds(itemIds)
                .build();
    }
}

