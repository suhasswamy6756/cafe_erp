package com.cafe.erp.modules.catalogue.item.service.implementation;

import com.cafe.erp.common.exception.ResourceNotFoundException;
import com.cafe.erp.modules.catalogue.category.entity.Category;
import com.cafe.erp.modules.catalogue.category.repository.CategoryRepository;
import com.cafe.erp.modules.catalogue.item.dto.ItemRequestDTO;
import com.cafe.erp.modules.catalogue.item.dto.ItemResponseDTO;
import com.cafe.erp.modules.catalogue.item.entity.Item;
import com.cafe.erp.modules.catalogue.item.entity.ItemModifierGroup;
import com.cafe.erp.modules.catalogue.item.repository.ItemModifierGroupRepository;
import com.cafe.erp.modules.catalogue.item.repository.ItemRepository;
import com.cafe.erp.modules.catalogue.item.service.ItemService;
import com.cafe.erp.modules.catalogue.modifier_group.entity.ModifierGroups;
import com.cafe.erp.modules.catalogue.modifier_group.repository.ModifierGroupRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static com.cafe.erp.common.utils.AuditUtils.getCurrentUser;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepo;
    private final CategoryRepository categoryRepo;
    private final ModifierGroupRepository groupRepo;
    private final ItemModifierGroupRepository itemModRepo;

    @Transactional
    @Override
    public ItemResponseDTO createItem(ItemRequestDTO dto) {

        Category category = null;
        if (dto.getCategoryId() != null) {
            category = categoryRepo.findById(dto.getCategoryId()).orElseThrow(() -> new ResourceNotFoundException("Category not found"));
        }

        Item item = Item.builder().name(dto.getName()).shortName(dto.getShortName()).handle(dto.getHandle()).foodType(dto.getFoodType()).posCode(dto.getPosCode()).description(dto.getDescription()).category(category).basePrice(dto.getBasePrice()).dineInPrice(dto.getDineInPrice()).takeawayPrice(dto.getTakeawayPrice()).deliveryPrice(dto.getDeliveryPrice()).aggregatorPrice(dto.getAggregatorPrice()).markupType(dto.getMarkupType()).markupValue(dto.getMarkupValue()).isActive(dto.getIsActive()).build();

        Item saved = itemRepo.save(item);

        // Save modifier groups mapping
        if (dto.getModifierGroupIds() != null) {
            for (Long mgId : dto.getModifierGroupIds()) {
                ModifierGroups mg = groupRepo.findByIdAndIsDeletedFalse(mgId).orElseThrow(() -> new ResourceNotFoundException("Modifier group not found"));

                itemModRepo.save(ItemModifierGroup.builder().item(saved).modifierGroup(mg).sortOrder(0).build());
            }
        }

        return mapToResponse(saved);
    }

    @Override
    public List<ItemResponseDTO> getAllItems() {
        return itemRepo.findAllByIsDeletedFalse().stream().map(this::mapToResponse).toList();
    }

    @Override
    public ItemResponseDTO getItemById(Long id) {
        Item item = itemRepo.findByIdAndIsDeletedFalse(id).orElseThrow(() -> new ResourceNotFoundException("Item not found"));
        return mapToResponse(item);
    }

    @Override
    @Transactional
    public ItemResponseDTO updateItem(Long itemId, ItemRequestDTO request) {

        Item item = itemRepo.findById(itemId).orElseThrow(() -> new ResourceNotFoundException("Item not found"));

        // ✅ Update normal fields
        mapFields(item, request);
        itemRepo.save(item);

        String user = getCurrentUser();  // your helper
        LocalDateTime now = LocalDateTime.now();

        // ✅ Fetch all existing mappings once
        List<ItemModifierGroup> existingLinks = itemModRepo.findAllByItemId(itemId);

        // ✅ Step 1: Soft delete everything (mark deleted)
        for (ItemModifierGroup link : existingLinks) {
            link.setIsDeleted(true);
            link.setDeletedAt(now);
            link.setDeletedBy(user);
            itemModRepo.save(link);
        }

        // ✅ Step 2: For each incoming modifier ID → re-enable or insert new
        for (Long mgId : request.getModifierGroupIds()) {

            // If already exists → reactivate instead of inserting
            Optional<ItemModifierGroup> existing = itemModRepo.findByItemIdAndGroupId(itemId, mgId);

            if (existing.isPresent()) {

                ItemModifierGroup link = existing.get();

                // Reactivate
                link.setIsDeleted(false);
                link.setDeletedAt(null);
                link.setDeletedBy(null);

                link.setUpdatedAt(now);
                link.setUpdatedBy(user);

                itemModRepo.save(link);

            } else {

                // Insert new link
                ModifierGroups mg = groupRepo.findById(mgId).orElseThrow(() -> new ResourceNotFoundException("ModifierGroup not found"));

                ItemModifierGroup newLink = new ItemModifierGroup();
                newLink.setItem(item);
                newLink.setModifierGroup(mg);
                newLink.setSortOrder(0);
                newLink.setIsDeleted(false);

                newLink.setCreatedAt(now);
                newLink.setCreatedBy(user);

                itemModRepo.save(newLink);
            }
        }

        return mapToResponse(item);
    }

    @Transactional
    @Override
    public void deleteItem(Long id) {
        Item item = itemRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Item not found"));

        String currentUser = getCurrentUser();
        LocalDateTime now = LocalDateTime.now();

        // ✅ Soft delete all modifier group mappings for this item
        List<ItemModifierGroup> mappings = itemModRepo.findAllByItemId(id);
        mappings.forEach(m -> {
            m.setIsDeleted(true);
            m.setDeletedAt(now);
            m.setDeletedBy(currentUser);
            itemModRepo.save(m);
        });

        // ✅ Soft delete item
        item.setIsDeleted(true);
        item.setDeletedAt(now);
        item.setDeletedBy(currentUser);
        itemRepo.save(item);
    }


    private ItemResponseDTO mapToResponse(Item item) {

        List<Long> modifierGroupIds = itemModRepo.findAllByItemAndIsDeletedFalse(item).stream().map(m -> m.getModifierGroup().getId()).toList();

        return ItemResponseDTO.builder().id(item.getId()).name(item.getName()).shortName(item.getShortName()).foodType(item.getFoodType()).posCode(item.getPosCode()).handle(item.getHandle()).description(item.getDescription()).categoryId(item.getCategory() != null ? item.getCategory().getId() : null).basePrice(item.getBasePrice()).dineInPrice(item.getDineInPrice()).takeawayPrice(item.getTakeawayPrice()).deliveryPrice(item.getDeliveryPrice()).aggregatorPrice(item.getAggregatorPrice()).markupType(item.getMarkupType()).markupValue(item.getMarkupValue()).isActive(item.getIsActive()).modifierGroupIds(modifierGroupIds).createdBy(item.getCreatedBy()).updatedBy(item.getUpdatedBy()).createdAt(item.getCreatedAt()).updatedAt(item.getUpdatedAt()).build();
    }

    private void mapFields(Item item, ItemRequestDTO request) {

        item.setName(request.getName());
        item.setShortName(request.getShortName());
        item.setHandle(request.getHandle());
        item.setDescription(request.getDescription());

        // Category lookup should happen outside
        Category category = categoryRepo.findById(request.getCategoryId()).orElseThrow(() -> new ResourceNotFoundException("Category not found"));
        item.setCategory(category);

        item.setBasePrice(request.getBasePrice());
        item.setDineInPrice(request.getDineInPrice());
        item.setTakeawayPrice(request.getTakeawayPrice());
        item.setDeliveryPrice(request.getDeliveryPrice());
        item.setAggregatorPrice(request.getAggregatorPrice());

        item.setPosCode(request.getPosCode());
        item.setFoodType(request.getFoodType());

        item.setMarkupType(request.getMarkupType());
        item.setMarkupValue(request.getMarkupValue());

        item.setIsActive(request.getIsActive());

        item.setUpdatedAt(LocalDateTime.now());
        item.setUpdatedBy(getCurrentUser());
    }

}
