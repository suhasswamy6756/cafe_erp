package com.cafe.erp.modules.inventory.purchase.dto;


import lombok.Data;
import java.time.LocalDate;
import java.util.List;

@Data
public class PurchaseOrderCreateRequest {
    private Long supplierId;
    private LocalDate expectedDelivery;
    private String notes;
    private List<PurchaseOrderItemRequest> items;
}
