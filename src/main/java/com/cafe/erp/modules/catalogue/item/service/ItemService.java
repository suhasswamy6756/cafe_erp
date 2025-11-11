package com.cafe.erp.modules.catalogue.item.service;

import com.cafe.erp.modules.catalogue.item.dto.ItemRequestDTO;
import com.cafe.erp.modules.catalogue.item.dto.ItemResponseDTO;

import java.util.List;

public interface ItemService {
    List<ItemResponseDTO> getAllItems();

    ItemResponseDTO getItemById(Long id);

    ItemResponseDTO createItem(ItemRequestDTO itemRequestDTO);

    ItemResponseDTO updateItem(Long id, ItemRequestDTO itemRequestDTO);

    void deleteItem(Long id);

}
