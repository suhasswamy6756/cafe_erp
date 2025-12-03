package com.cafe.erp.modules.inventory.recipe.entity;


import com.cafe.erp.modules.inventory.material.entity.Material;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Table(name = "recipe_items")
@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RecipeItems {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="item_id")
    private Long itemId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "version_id", nullable = false)
    private RecipeVersions version;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "material_id", nullable = false)
    private Material material;

    @Column(name="quantity")
    private BigDecimal quantity;

    @Column(name="uom_code")
    private String uomCode;

    @Column(name = "cost_per_unit")
    private BigDecimal costPerUnit;

    @Column(name = "total_cost")
    private BigDecimal totalCost;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "updated_by")
    private String updatedBy;
}
