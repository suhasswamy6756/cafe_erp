package com.cafe.erp.modules.inventory.recipe.entity;


import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Table(name = "recipe_cost_audit")
@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RecipeCostAudit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "audit_id")
    private Long auditId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "version_id", nullable = false)
    private RecipeVersions versions;

    @Column(name = "total_cost")
    private BigDecimal totalCost;

    @Column(name = "cost_per_output_unit")
    private BigDecimal costPerOutputUnit;

    @Column(name = "calculated_at")
    private LocalDateTime calculatedAt;

}
