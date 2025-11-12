package com.cafe.erp.modules.catalogue.item.entity;

import com.cafe.erp.common.enums.FoodType;
import com.cafe.erp.common.model.BaseEntity;
import com.cafe.erp.modules.catalogue.category.entity.Category;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "items")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Item extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Basic Info
    @Column(nullable = false)
    private String name;

    private String shortName;

    @Column(unique = true)
    private String handle;

    private String description;

    // Category mapping
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @Column(name = "pos_code")
    private String posCode;

    @Column(name = "food_type")
    @Enumerated(EnumType.STRING)
    private FoodType foodType;


    // Pricing
    private Double basePrice;
    private Double dineInPrice;
    private Double takeawayPrice;
    private Double deliveryPrice;
    private Double aggregatorPrice;

    private String markupType;   // NONE, FLAT, PERCENTAGE
    private Double markupValue;

    private Boolean isActive;
}
