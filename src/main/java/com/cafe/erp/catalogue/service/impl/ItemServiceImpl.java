package com.cafe.erp.catalogue.service.impl;

import com.cafe.erp.catalogue.dto.ItemRequestDTO;
import com.cafe.erp.catalogue.model.*;
import com.cafe.erp.catalogue.repository.CategoryRepository;
import com.cafe.erp.catalogue.repository.ItemRepository;
import com.cafe.erp.catalogue.repository.ModifierGroupRepository;
import com.cafe.erp.catalogue.repository.TaxRepository;
import com.cafe.erp.catalogue.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;
    private final TaxRepository taxRepository;
    private final ModifierGroupRepository modifierGroupRepository;
    private final CategoryRepository categoryRepository; // ✅ add this to fetch category

    @Override
    public Item createItem(ItemRequestDTO itemRequest) {
        // ✅ Fetch the category using ID
        Category category = categoryRepository.findById(itemRequest.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found with id: " + itemRequest.getCategoryId()));

        // ✅ Build the item
        Item item = new Item();
        item.setCategory(category);
        item.setTitle(itemRequest.getTitle());
        item.setShortName(itemRequest.getShortName());
        item.setHandle(itemRequest.getHandle());
        item.setDescription(itemRequest.getDescription());
        item.setDefaultPrice(itemRequest.getDefaultPrice());
        item.setFoodType(itemRequest.getFoodType());
        item.setItemType(itemRequest.getItemType());
        item.setActive(itemRequest.getActive());
        item.setIsDeleted(false);

        // ✅ Save item
        return itemRepository.save(item);
    }

    @Override
    public Item updateItem(Long id, Item item) {
        return itemRepository.findById(id)
                .map(existingItem -> {
                    existingItem.setTitle(item.getTitle());
                    existingItem.setDescription(item.getDescription());
                    existingItem.setDefaultPrice(item.getDefaultPrice());
                    existingItem.setCategory(item.getCategory());
                    existingItem.setFoodType(item.getFoodType());
                    existingItem.setItemType(item.getItemType());
                    existingItem.setActive(item.getActive());
                    return itemRepository.save(existingItem);
                })
                .orElseThrow(() -> new RuntimeException("Item not found with id: " + id));
    }

    @Override
    public void deleteItem(Long id) {
        itemRepository.deleteById(id);
    }

    @Override
    public Item getItemById(Long id) {
        return itemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Item not found with id: " + id));
    }

    @Override
    public List<Item> getAllItems() {
        return itemRepository.findAll();
    }

    @Override
    public Item mapTaxesToItem(Long itemId, List<Long> taxIds) {
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new RuntimeException("Item not found with id: " + itemId));

//        item.getTaxMappings().clear(); // <-- Possible NullPointerException if taxMappings == null

        List<ItemTaxMapping> taxMappings = taxIds.stream()
                .map(taxId -> {
                    Tax tax = taxRepository.findById(taxId)
                            .orElseThrow(() -> new RuntimeException("Tax not found: " + taxId));
                    return new ItemTaxMapping(item, tax, true);
                })
                .toList();

        item.setTaxMappings(taxMappings);
        return itemRepository.save(item);
    }


    @Override
    public Item mapModifierGroupsToItem(Long itemId, List<Long> modifierGroupIds) {
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new RuntimeException("Item not found with id: " + itemId));

        item.getModifierGroupMappings().clear();

        List<ItemModifierGroupMapping> mappings = modifierGroupIds.stream()
                .map(groupId -> {
                    ModifierGroup group = modifierGroupRepository.findById(groupId)
                            .orElseThrow(() -> new RuntimeException("Modifier group not found: " + groupId));
                    return new ItemModifierGroupMapping(item, group, true);
                })
                .toList();

        item.setModifierGroupMappings(mappings);
        return itemRepository.save(item);
    }

}
