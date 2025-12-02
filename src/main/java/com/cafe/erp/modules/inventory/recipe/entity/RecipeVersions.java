package com.cafe.erp.modules.inventory.recipe.entity;

import com.cafe.erp.common.model.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RecipeVersions extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "version_id")
    private Long versionId;

    @Column(name = "recipe_id")
    private Long recipeId;

    @Column(name = "version_number")
    private Long versionNumber;

    @Column(name = "status")
    private String status;

    @Column(name = "notes")
    private String note;

    @Column(name = "wastage_percent")
    private BigDecimal wastagePercent;

    @Column(name = "is_default")
    private Boolean isDefault;
}
