package com.cafe.erp.catalogue.model;

import com.cafe.erp.common.model.BaseEntity;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "modifier_groups")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
//@Builder
public class ModifierGroup extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", nullable = false, length = 150)
    private String title;

    @Column(name = "short_name", length = 100)
    private String shortName;

    @Column(name = "handle", length = 100, unique = true)
    private String handle;

    @Column(name = "group_type", length = 50)
    private String groupType; // Variant or Addon

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "sort_order", nullable = false)
    private Integer sortOrder = 0;

//    @Builder.Default
    @Column(name = "is_deleted", nullable = false)
    private Boolean isDeleted = false;

    @Column(name = "active", nullable = false)
    private Boolean active = true;

    @OneToMany(mappedBy = "modifierGroup", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<Modifier> modifiers;
}
