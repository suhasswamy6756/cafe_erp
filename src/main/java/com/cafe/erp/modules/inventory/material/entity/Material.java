package com.cafe.erp.modules.inventory.material.entity;


import com.cafe.erp.common.enums.MaterialType;
import com.cafe.erp.common.model.BaseEntity;
import com.cafe.erp.modules.inventory.categories.entity.InventoryCategory;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "materials")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Material extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long materialId;

    @Column(nullable = false, unique = true)
    private String name;

    private String sku;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private InventoryCategory category;

    @Column(name = "uom_code", nullable = false)
    private String uomCode;

    @Enumerated(EnumType.STRING)
    @Column(name = "material_type", nullable = false)
    private MaterialType materialType;

    @Column(name = "unit_cost")
    private BigDecimal unitCost;

    @Column(name = "reorder_level")
    private Double reorderLevel;

    private Boolean isActive;
}

