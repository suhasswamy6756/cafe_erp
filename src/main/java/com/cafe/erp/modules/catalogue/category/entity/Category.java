package com.cafe.erp.modules.catalogue.category.entity;

import com.cafe.erp.common.model.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "categories")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Category extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "short_name")   // ✅ FIXED (nullable allowed)
    private String shortName;

    @Column(name = "handle")
    private String handle;

    @Column(name = "description")
    private String description;

    @Column(name = "parent_category_id")
    private Long parentCategoryId;

    @Column(name = "kot_group")
    private String kotGroup;

    @Column(name = "timing_group_id")
    private Long timingGroupId;

    @Column(name = "sort_order")
    private int sortOrder;

    @Column(name = "active")
    private boolean active;
}
