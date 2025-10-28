package com.cafe.erp.catalogue.model;

import com.cafe.erp.common.model.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "modifiers")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Modifier extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Relationship with ModifierGroup
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "modifier_group_id", referencedColumnName = "id")
    private ModifierGroup modifierGroup;

    @Column(name = "title", nullable = false, length = 150)
    private String title;

    @Column(name = "short_name", length = 100)
    private String shortName;

    @Column(name = "food_type", length = 50)
    private String foodType; // Veg, NonVeg, Egg

    @Column(name = "default_sale_price", precision = 10, scale = 2)
    private Double defaultSalePrice = 0.0;

    @Column(name = "sort_order", nullable = false)
    private Integer sortOrder = 0;

    @Column(name = "is_default", nullable = false)
    private Boolean isDefault = false;

    @Builder.Default
    @Column(name = "is_deleted", nullable = false)
    private Boolean isDeleted = false;

    @Column(name = "active", nullable = false)
    private Boolean active = true;
}
