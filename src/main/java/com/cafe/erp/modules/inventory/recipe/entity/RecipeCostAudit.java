package com.cafe.erp.modules.inventory.recipe.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Table(name = "recipe_cost_audit")
@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RecipeCostAudit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "audit_id")
    private Long auditId;

    @Column(name = "version_id")
    private Long versionId;

    @Column(name = "total_cost")
    private Long recipeId;

    @Column(name = "cost_per_output_unit")
    private BigDecimal costPerOutputUnit;

    @Column(name = "calculated_at")
    private LocalDateTime calculatedAt;


}
