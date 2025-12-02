package com.cafe.erp.modules.inventory.recipe.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Table(name = "recipe_items")
@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RecipeItems {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="item_id")
    private Long itemId;

    @Column(name="version_id")
    private Long versionId;

    @Column(name="material_id")
    private Long materialId;

    @Column(name="quantity")
    private BigDecimal quantity;

    @Column(name="uom_code")
    private String uomCode;

    @Column(name = "cost_per_unit")
    private String costPerUnit;

    @Column(name = "total_cost")
    private String totalCost;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "updated_by")
    private String updatedBy;
}
