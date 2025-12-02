package com.cafe.erp.modules.inventory.recipe.entity;


import com.cafe.erp.common.model.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name = "recipe_categories")
@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RecipeCategory extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long recipeCategoryId;

    @Column(name = "name")
    private String recipeCategoryName;

    @Column(name = "description")
    private String recipeCategoryDescription;
}
