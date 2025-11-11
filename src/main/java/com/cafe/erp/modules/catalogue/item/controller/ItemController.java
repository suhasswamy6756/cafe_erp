package com.cafe.erp.modules.catalogue.item.controller;

import com.cafe.erp.common.model.ApiResponse;
import com.cafe.erp.modules.catalogue.item.dto.ItemRequestDTO;
import com.cafe.erp.modules.catalogue.item.dto.ItemResponseDTO;
import com.cafe.erp.modules.catalogue.item.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/items")
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    @PostMapping
    public ResponseEntity<ApiResponse<ItemResponseDTO>> createItem(@RequestBody ItemRequestDTO dto) {
        return ResponseEntity.ok(
                ApiResponse.success("Item created successfully", itemService.createItem(dto), 201)
        );
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<ItemResponseDTO>>> getAllItems() {
        return ResponseEntity.ok(
                ApiResponse.success("Items fetched successfully", itemService.getAllItems(), 200)
        );
    }

    @GetMapping("/{id}")
    public ItemResponseDTO getItemById(@PathVariable Long id) {
        return itemService.getItemById(id);
    }

    @PutMapping("/{id}")
    public ItemResponseDTO updateItem(@PathVariable Long id, @RequestBody ItemRequestDTO dto) {
        return itemService.updateItem(id, dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteItem(@PathVariable Long id) {
        itemService.deleteItem(id);
        return ResponseEntity.ok(
                ApiResponse.success("Item deleted successfully", null, 200)
        );
    }
}
