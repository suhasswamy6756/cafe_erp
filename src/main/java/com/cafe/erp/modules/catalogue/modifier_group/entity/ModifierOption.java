package com.cafe.erp.modules.catalogue.modifier_group.entity;

import com.cafe.erp.common.model.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "modifier_options")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ModifierOption extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "option_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "modifier_group_id",
            referencedColumnName = "modifier_group_id",
            nullable = false
    )
    private ModifierGroups modifierGroup;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "price_modifier")
    private Double priceModifier = 0.0;

    @Column(name = "sort_order")
    private Integer sortOrder = 0;

    @Column(name = "is_active")
    private Boolean isActive = true;
}
