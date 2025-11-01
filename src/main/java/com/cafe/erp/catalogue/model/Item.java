package com.cafe.erp.catalogue.model;

import com.cafe.erp.common.model.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "items")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
//@Builder
public class Item extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @Column(name = "title", nullable = false, length = 150)
    private String title;

    @Column(name = "short_name", length = 100)
    private String shortName;

    @Column(name = "handle", unique = true, length = 100)
    private String handle;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "default_price", precision = 10, scale = 2)
    private BigDecimal defaultPrice;

    // 🍽️ Food type: Veg, NonVeg, Egg
    @Column(name = "food_type", length = 50)
    private String foodType;

    // 🏷️ Item type: POS or RAW
    @Column(name = "item_type", length = 50, nullable = false)
    private String itemType; // e.g. "POS", "RAW"

    @Column(name = "active", nullable = false)
    private Boolean active = true;

    @Column(name = "is_deleted", nullable = false)
    private Boolean isDeleted = false;

    // 🔗 Relations
    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ItemTaxMapping> taxMappings;

    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ItemModifierGroupMapping> modifierGroupMappings;
}
