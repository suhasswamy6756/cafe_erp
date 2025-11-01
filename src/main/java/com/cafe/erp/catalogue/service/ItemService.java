package com.cafe.erp.catalogue.service;

import com.cafe.erp.catalogue.dto.ItemRequestDTO;
import com.cafe.erp.catalogue.model.Item;

import java.util.List;

public interface ItemService {

    Item createItem(ItemRequestDTO item);

    Item updateItem(Long id, Item item);

    void deleteItem(Long id);

    Item getItemById(Long id);

    List<Item> getAllItems();

    Item mapTaxesToItem(Long itemId, List<Long> taxIds);

    Item mapModifierGroupsToItem(Long itemId, List<Long> modifierGroupIds);
}
