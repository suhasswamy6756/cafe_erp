package com.cafe.erp.catalogue.controller;

import com.cafe.erp.catalogue.dto.ItemRequestDTO;
import com.cafe.erp.catalogue.model.Item;
import com.cafe.erp.catalogue.service.ItemService;
import com.cafe.erp.common.model.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/items")
public class ItemController {

    @Autowired
    private ItemService itemService;

    @PostMapping
    public ResponseEntity<ApiResponse<Item>> createItem(@RequestBody ItemRequestDTO  item) {
        return ResponseEntity.ok(ApiResponse.success("Item created successfully", itemService.createItem(item), 201));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<Item>>> getAllItems() {
        return ResponseEntity.ok(ApiResponse.success("Items fetched successfully", itemService.getAllItems(), 200));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Item>> getItemById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success("Item fetched successfully", itemService.getItemById(id), 200));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Item>> updateItem(@PathVariable Long id, @RequestBody Item item) {
        return ResponseEntity.ok(ApiResponse.success("Item updated successfully", itemService.updateItem(id, item), 200));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Item>> deleteItem(@PathVariable Long id) {
        itemService.deleteItem(id);
        return ResponseEntity.ok(ApiResponse.success("Item deleted successfully", null, 200));
    }

    @PostMapping("/{itemId}/modifier-groups")
    public ResponseEntity<ApiResponse<Item>> addModifierGroupsToItem(
            @PathVariable Long itemId,
            @RequestBody List<Long> modifierGroupIds) {
        return ResponseEntity.ok(ApiResponse.success(
                "Modifier groups mapped successfully",
                itemService.mapModifierGroupsToItem(itemId, modifierGroupIds),
                200
        ));
    }

    @PostMapping("/{itemId}/taxes")
    public ResponseEntity<ApiResponse<Item>> addTaxesToItem(
            @PathVariable Long itemId,
            @RequestBody List<Long> taxIds) {
        return ResponseEntity.ok(ApiResponse.success(
                "Taxes mapped successfully",
                itemService.mapTaxesToItem(itemId, taxIds),
                200
        ));
    }

}
