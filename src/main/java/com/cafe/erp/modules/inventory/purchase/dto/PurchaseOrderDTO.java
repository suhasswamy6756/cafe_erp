package com.cafe.erp.modules.inventory.purchase.dto;


import com.cafe.erp.common.enums.PurchaseStatus;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class PurchaseOrderDTO {

    private Long poId;
    private Long supplierId;
    private String supplierName;
    private LocalDate orderDate;
    private LocalDate expectedDelivery;
    private String notes;
    private PurchaseStatus status;
    private List<PurchaseOrderItemDTO> items;
}

