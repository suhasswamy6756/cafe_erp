package com.cafe.erp.modules.catalogue.modifier_group.entity;

import com.cafe.erp.common.model.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(
        name = "modifier_groups",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"handle"})
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ModifierGroups extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "modifier_group_id")
    private Long id;

    @Column(name = "type")
    private String type;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "short_name")
    private String shortName;

    @Column(name = "handle", unique = true)
    private String handle;

    @Column(name = "sort_order")
    private Integer sortOrder = 0;

    @Column(name = "description")
    private String description;

    @Column(name = "is_active")
    private Boolean isActive = true;

    // One Modifier Group → Many Modifier Options
    @OneToMany(mappedBy = "modifierGroup", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<ModifierOption> options;
}
