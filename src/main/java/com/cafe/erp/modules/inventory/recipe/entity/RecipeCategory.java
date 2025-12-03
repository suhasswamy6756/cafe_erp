package com.cafe.erp.modules.inventory.recipe.entity;


import com.cafe.erp.common.model.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Table(name = "recipe_categories")
@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RecipeCategory extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "recipe_category_id")
    private Long recipeCategoryId;

    @Column(name = "name")
    private String recipeCategoryName;

    @Column(name = "description")
    private String recipeCategoryDescription;
}
