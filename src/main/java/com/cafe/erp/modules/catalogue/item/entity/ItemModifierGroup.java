package com.cafe.erp.modules.catalogue.item.entity;

import com.cafe.erp.common.model.BaseEntity;
import com.cafe.erp.modules.catalogue.modifier_group.entity.ModifierGroups;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "item_modifier_groups")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ItemModifierGroup extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Item mapping
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id", nullable = false)
    private Item item;

    // Modifier group mapping
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "modifier_group_id")
    private ModifierGroups modifierGroup;

    private Integer sortOrder = 0;
}
