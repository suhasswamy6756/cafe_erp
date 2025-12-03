package com.cafe.erp.modules.inventory.recipe.entity;

import com.cafe.erp.common.model.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "recipes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Recipes extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "recipe_id")
    private Long recipeId;

    @Column(name = "recipe_name")
    private String recipeName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recipe_category_id", nullable = false)
    private RecipeCategory recipeCategory;

    @Column(name = "output_unit")
    private String outputUnit;

    @Column(name = "output_quantity")
    private BigDecimal outputQuantity;


}
